package nadiendev.ThaumaturgeTechCompats.flux.link;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxStorage;
import nadiendev.ThaumaturgeTechCompats.flux.SimpleFluxStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.ARGB;

public class TransmissionLinkBlockEntity extends BlockEntity {
    public enum Status {
        UNLINKED,
        ACTIVE,
        PARTNER_UNLOADED,
        OUT_OF_RANGE,
        DISABLED
    }

    private static final String PARTNER_KEY = "partner";
    private static final float PARTICLE_WHITE_MIX = 0.4F;

    private final SimpleFluxStorage buffer = new SimpleFluxStorage(this::capacity);
    private BlockPos partner;
    private TransmissionLinkBlockEntity partnerCache;
    private int transferTimer;
    private int beamTimer;
    private long flow;

    public TransmissionLinkBlockEntity(BlockPos pos, BlockState state) {
        super(LinkRegistry.LINK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TransmissionLinkBlockEntity link) {
        if (!(level instanceof ServerLevel server) || !CompatToggles.enabled(link.tier().toggle())) {
            return;
        }
        link.transferTimer++;
        int interval = LinkConfig.transferInterval();
        if (link.transferTimer >= interval) {
            link.transferTimer = 0;
            link.transfer(server, interval);
        }
        link.beam(server);
    }

    public FluxTier tier() {
        return getBlockState().getBlock() instanceof TransmissionLinkBlock block ? block.tier() : FluxTier.THAUMIUM;
    }

    public IFluxStorage storage() {
        return buffer;
    }

    public BlockPos partner() {
        return partner;
    }

    public long capacity() {
        return FluxTransfer.scale(FluxConfig.linkRate(tier()), LinkConfig.bufferTicks());
    }

    public int range(TransmissionLinkBlockEntity other) {
        return Math.min(FluxConfig.linkRange(tier()), FluxConfig.linkRange(other.tier()));
    }

    public boolean inRange(TransmissionLinkBlockEntity other) {
        double range = range(other);
        return worldPosition.distSqr(other.worldPosition) <= range * range;
    }

    public Status status(ServerLevel level) {
        if (partner == null) {
            return Status.UNLINKED;
        }
        if (!CompatToggles.enabled(tier().toggle())) {
            return Status.DISABLED;
        }
        TransmissionLinkBlockEntity other = resolve(level);
        if (partner == null) {
            return Status.UNLINKED;
        }
        if (other == null) {
            return Status.PARTNER_UNLOADED;
        }
        return inRange(other) ? Status.ACTIVE : Status.OUT_OF_RANGE;
    }

    public TransmissionLinkBlockEntity partnerEntity(ServerLevel level) {
        return resolve(level);
    }

    public static void pair(TransmissionLinkBlockEntity first, TransmissionLinkBlockEntity second) {
        first.breakLink();
        second.breakLink();
        first.partner = second.worldPosition;
        second.partner = first.worldPosition;
        first.partnerCache = second;
        second.partnerCache = first;
        first.setChanged();
        second.setChanged();
    }

    public void breakLink() {
        if (partner == null) {
            return;
        }
        BlockPos old = partner;
        TransmissionLinkBlockEntity other = partnerCache != null && !partnerCache.isRemoved() ? partnerCache : null;
        if (other == null && level != null && level.hasChunkAt(old) && level.getBlockEntity(old) instanceof TransmissionLinkBlockEntity found) {
            other = found;
        }
        partner = null;
        partnerCache = null;
        setChanged();
        if (other != null && worldPosition.equals(other.partner)) {
            other.partner = null;
            other.partnerCache = null;
            other.setChanged();
        }
    }

    private TransmissionLinkBlockEntity resolve(ServerLevel level) {
        if (partner == null) {
            return null;
        }
        if (partnerCache != null && !partnerCache.isRemoved() && worldPosition.equals(partnerCache.partner)) {
            return partnerCache;
        }
        partnerCache = null;
        if (!level.hasChunkAt(partner)) {
            return null;
        }
        if (level.getBlockEntity(partner) instanceof TransmissionLinkBlockEntity other && worldPosition.equals(other.partner)) {
            partnerCache = other;
            return other;
        }
        partner = null;
        setChanged();
        return null;
    }

    private void transfer(ServerLevel level, int interval) {
        FluxTier tier = tier();
        long moved = FluxTransfer.exchange(level, worldPosition, buffer, FluxTransfer.scale(FluxConfig.linkRate(tier), interval), true, true);
        TransmissionLinkBlockEntity other = resolve(level);
        if (other != null && worldPosition.asLong() < other.worldPosition.asLong() && inRange(other)) {
            long rate = Math.min(FluxConfig.linkRate(tier), FluxConfig.linkRate(other.tier()));
            long paired = FluxTransfer.balance(buffer, other.buffer, FluxTransfer.scale(rate, interval));
            if (paired > 0L) {
                flow += paired;
                moved += paired;
                other.setChanged();
            }
        }
        if (moved > 0L) {
            setChanged();
        }
    }

    private void beam(ServerLevel level) {
        if (partner == null || !LinkConfig.beamEnabled()) {
            return;
        }
        TransmissionLinkBlockEntity other = resolve(level);
        if (other == null || worldPosition.asLong() > other.worldPosition.asLong()) {
            return;
        }
        beamTimer++;
        if (beamTimer < LinkConfig.beamInterval()) {
            return;
        }
        beamTimer = 0;
        long window = flow;
        flow = 0L;
        if ((window <= 0L && !LinkConfig.beamIdle()) || !inRange(other)) {
            return;
        }
        Vec3 from = Vec3.atCenterOf(worldPosition);
        Vec3 to = Vec3.atCenterOf(other.worldPosition);
        int points = Math.max(2, Math.min(LinkConfig.beamMaxPoints(), (int) Math.ceil(from.distanceTo(to) * LinkConfig.beamPointsPerBlock())));
        DustParticleOptions options = new DustParticleOptions(particleColor(tier()), 1.0F);
        double phase = level.getRandom().nextDouble();
        for (int i = 0; i < points; i++) {
            double t = (i + phase) / points;
            level.sendParticles(options,
                    from.x + (to.x - from.x) * t,
                    from.y + (to.y - from.y) * t,
                    from.z + (to.z - from.z) * t,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static int particleColor(FluxTier tier) {
        int color = tier.color();
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        return ARGB.colorFromFloat(1.0F, red + (1.0F - red) * PARTICLE_WHITE_MIX, green + (1.0F - green) * PARTICLE_WHITE_MIX, blue + (1.0F - blue) * PARTICLE_WHITE_MIX);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        CompoundTag tag = new CompoundTag();
        buffer.save(tag);
        if (partner != null) {
            tag.putLong(PARTNER_KEY, partner.asLong());
        }
        output.store("data", CompoundTag.CODEC, tag);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        CompoundTag tag = input.read("data", CompoundTag.CODEC).orElseGet(CompoundTag::new);
        buffer.load(tag);
        partner = tag.contains(PARTNER_KEY) ? BlockPos.of(tag.getLongOr(PARTNER_KEY, 0L)) : null;
        partnerCache = null;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level != null && !level.isClientSide()) {
            breakLink();
        }
    }
}
