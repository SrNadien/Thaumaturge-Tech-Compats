package nadiendev.ThaumaturgeTechCompats.nodepower;

import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class RiftJarBlockEntity extends BlockEntity {
    private static final String POWER_KEY = "power";

    private float power;
    private int timer;

    public RiftJarBlockEntity(BlockPos pos, BlockState state) {
        super(NodePowerRegistry.RIFT_JAR_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RiftJarBlockEntity jar) {
        if (!(level instanceof ServerLevel server) || !CompatToggles.enabled(CompatToggles.NODE_POWER)) {
            return;
        }
        jar.timer++;
        if (jar.timer < NodePowerConfig.riftJarCaptureInterval()) {
            return;
        }
        jar.timer = 0;
        if (jar.power >= jar.capacity()) {
            return;
        }
        double radius = NodePowerConfig.riftJarCaptureRadius();
        AABB area = new AABB(pos).inflate(radius);
        EntityFluxRift nearest = null;
        double best = radius * radius;
        for (EntityFluxRift rift : server.getEntitiesOfClass(EntityFluxRift.class, area)) {
            if (rift.isRemoved() || rift.isCollapsing() || rift.currentSize() <= NodePowerConfig.riftMinSize()) {
                continue;
            }
            double distance = rift.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
            if (distance <= best) {
                nearest = rift;
                best = distance;
            }
        }
        if (nearest == null) {
            return;
        }
        nearest.resize(nearest.currentSize() - 1);
        jar.power = Math.min(jar.capacity(), jar.power + NodePowerConfig.riftJarPowerPerSize());
        jar.update();
    }

    public float power() {
        return power;
    }

    public float capacity() {
        return NodePowerConfig.riftJarCapacity();
    }

    public void consume(float amount) {
        power = Math.max(0.0F, power - amount);
        update();
    }

    public int fillLevel() {
        if (power <= 0.0F) {
            return 0;
        }
        return Math.max(1, Math.min(RiftJarBlock.MAX_FILL, (int) Math.ceil(power * RiftJarBlock.MAX_FILL / capacity())));
    }

    private void update() {
        setChanged();
        if (level == null) {
            return;
        }
        BlockState state = getBlockState();
        int fill = fillLevel();
        if (state.hasProperty(RiftJarBlock.FILL) && state.getValue(RiftJarBlock.FILL) != fill) {
            level.setBlock(worldPosition, state.setValue(RiftJarBlock.FILL, fill), 3);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        CompoundTag tag = new CompoundTag();
        tag.putFloat(POWER_KEY, power);
        output.store("data", CompoundTag.CODEC, tag);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        CompoundTag tag = input.read("data", CompoundTag.CODEC).orElseGet(CompoundTag::new);
        power = tag.getFloatOr(POWER_KEY, 0.0F);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level != null && !level.isClientSide() && power() > 0.0F) {
            AuraHelper.addFlux(level, pos, power() * NodePowerConfig.riftJarReleaseRatio());
        }
    }
}
