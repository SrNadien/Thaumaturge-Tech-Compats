package nadiendev.ThaumaturgeTechCompats.flux.link;

import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxStorage;
import nadiendev.ThaumaturgeTechCompats.flux.SimpleFluxStorage;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePowerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RiftExtractorBlockEntity extends BlockEntity {
    private static final String TIMER_KEY = "timer";
    private static final int PARTICLE_POINTS = 6;

    private final SimpleFluxStorage storage = new SimpleFluxStorage(LinkConfig::extractorCapacity);
    private final IFluxStorage output = new OutputView(storage);
    private int timer;

    public RiftExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(LinkRegistry.EXTRACTOR_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RiftExtractorBlockEntity extractor) {
        if (!(level instanceof ServerLevel server) || !CompatToggles.enabled(FluxTier.THAUMIUM.toggle())) {
            return;
        }
        extractor.timer++;
        if (extractor.timer >= FluxConfig.riftExtractInterval()) {
            extractor.timer = 0;
            extractor.extract(server);
        }
        if (extractor.storage.stored() > 0L) {
            long moved = FluxTransfer.exchange(server, pos, extractor.storage, LinkConfig.extractorPushRate(), false, false);
            if (moved > 0L) {
                extractor.setChanged();
            }
        }
    }

    public IFluxStorage output() {
        return output;
    }

    public IFluxStorage storage() {
        return storage;
    }

    public EntityFluxRift nearestRift(ServerLevel level) {
        double radius = FluxConfig.riftExtractRadius();
        double best = radius * radius;
        EntityFluxRift nearest = null;
        for (EntityFluxRift rift : level.getEntitiesOfClass(EntityFluxRift.class, new AABB(worldPosition).inflate(radius))) {
            if (rift.isRemoved() || rift.isCollapsing() || rift.currentSize() <= NodePowerConfig.riftMinSize()) {
                continue;
            }
            double distance = rift.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D);
            if (distance <= best) {
                nearest = rift;
                best = distance;
            }
        }
        return nearest;
    }

    private void extract(ServerLevel level) {
        long perSize = FluxConfig.riftFluxPerSize();
        if (storage.capacity() - storage.stored() < perSize) {
            return;
        }
        EntityFluxRift rift = nearestRift(level);
        if (rift == null) {
            return;
        }
        Vec3 from = rift.position();
        Vec3 to = Vec3.atCenterOf(worldPosition);
        rift.resize(rift.currentSize() - 1);
        storage.insert(perSize, false);
        setChanged();
        if (LinkConfig.extractorParticles()) {
            for (int i = 0; i < PARTICLE_POINTS; i++) {
                double t = (i + level.getRandom().nextDouble()) / PARTICLE_POINTS;
                level.sendParticles(ParticleTypes.PORTAL,
                        from.x + (to.x - from.x) * t,
                        from.y + (to.y - from.y) * t,
                        from.z + (to.z - from.z) * t,
                        2, 0.1D, 0.1D, 0.1D, 0.0D);
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        CompoundTag tag = new CompoundTag();
        storage.save(tag);
        tag.putInt(TIMER_KEY, timer);
        output.store("data", CompoundTag.CODEC, tag);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        CompoundTag tag = input.read("data", CompoundTag.CODEC).orElseGet(CompoundTag::new);
        storage.load(tag);
        timer = tag.getIntOr(TIMER_KEY, 0);
    }

    private record OutputView(IFluxStorage inner) implements IFluxStorage {
        @Override
        public long insert(long amount, boolean simulate) {
            return 0L;
        }

        @Override
        public long extract(long amount, boolean simulate) {
            return inner.extract(amount, simulate);
        }

        @Override
        public long stored() {
            return inner.stored();
        }

        @Override
        public long capacity() {
            return inner.capacity();
        }

        @Override
        public boolean canInsert() {
            return false;
        }
    }
}
