package nadiendev.ThaumaturgeTechCompats.nodepower;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FluxJarBlockEntity extends BlockEntity {
    private static final String FLUX_KEY = "flux";

    private float flux;
    private int timer;

    public FluxJarBlockEntity(BlockPos pos, BlockState state) {
        super(NodePowerRegistry.FLUX_JAR_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FluxJarBlockEntity jar) {
        if (!(level instanceof ServerLevel) || !CompatToggles.enabled(CompatToggles.NODE_POWER)) {
            return;
        }
        jar.timer++;
        if (jar.timer < NodePowerConfig.fluxJarFillInterval()) {
            return;
        }
        jar.timer = 0;
        float room = jar.capacity() - jar.flux;
        if (room <= 0.0F) {
            return;
        }
        float taken = AuraHelper.drainFlux(level, pos, Math.min(NodePowerConfig.fluxJarFillAmount(jar.tier()), room), false);
        if (taken > 0.0F) {
            jar.flux = Math.min(jar.capacity(), jar.flux + taken);
            jar.update();
        }
    }

    public float flux() {
        return flux;
    }

    public FluxJarTier tier() {
        return getBlockState().getBlock() instanceof FluxJarBlock block ? block.tier() : FluxJarTier.BASE;
    }

    public float capacity() {
        return NodePowerConfig.fluxJarCapacity(tier());
    }

    public float fillRatio() {
        float capacity = capacity();
        return capacity <= 0.0F ? 0.0F : Math.min(1.0F, flux / capacity);
    }

    public void consume(float amount) {
        flux = Math.max(0.0F, flux - amount);
        update();
    }

    private void update() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        CompoundTag tag = new CompoundTag();
        tag.putFloat(FLUX_KEY, flux);
        output.store("data", CompoundTag.CODEC, tag);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        CompoundTag tag = input.read("data", CompoundTag.CODEC).orElseGet(CompoundTag::new);
        flux = tag.getFloatOr(FLUX_KEY, 0.0F);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level != null && !level.isClientSide() && flux() > 0.0F) {
            AuraHelper.addFlux(level, pos, flux());
        }
    }
}
