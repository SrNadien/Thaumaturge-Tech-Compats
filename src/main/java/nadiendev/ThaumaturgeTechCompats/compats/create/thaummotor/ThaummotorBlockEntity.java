package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePower;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePowerConfig;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSource;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class ThaummotorBlockEntity extends GeneratingKineticBlockEntity {
    private static final String ACTIVE_KEY = "active";
    private static final String SOURCE_KEY = "source";
    private static final String CAPACITY_KEY = "capacity";
    private static final String SPEED_KEY = "generated_speed";
    private static final String INDENT = "    ";

    private int timer;
    private boolean active;
    private NodeSourceKind source;
    private float capacityPerRpm;
    private float generatedSpeed;
    private ScrollValueBehaviour speedSetting;

    public ThaummotorBlockEntity(BlockPos pos, BlockState state) {
        super(ThaummotorRegistry.THAUMMOTOR_ENTITY.get(), pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        speedSetting = new KineticScrollValueBehaviour(CreateLang.translateDirect("kinetics.creative_motor.rotation_speed"), this, new ThaummotorValueBox());
        speedSetting.between(-ThaummotorConfig.maxSpeed(), ThaummotorConfig.maxSpeed());
        speedSetting.value = ThaummotorConfig.speed();
        speedSetting.withCallback(value -> updateGeneratedRotation());
        behaviours.add(speedSetting);
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) {
            updateGeneratedRotation();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) {
            return;
        }
        timer--;
        if (timer > 0) {
            return;
        }
        timer = ThaummotorConfig.checkInterval();
        refresh();
    }

    @Override
    public float getGeneratedSpeed() {
        return active && speedSetting != null ? convertToDirection(speedSetting.getValue(), getBlockState().getValue(ThaummotorBlock.FACING)) : 0.0F;
    }

    @Override
    public float calculateAddedStressCapacity() {
        float capacity = active ? capacityPerRpm : 0.0F;
        lastCapacityProvided = capacity;
        return capacity;
    }

    @Override
    public float calculateStressApplied() {
        lastStressApplied = 0.0F;
        return 0.0F;
    }

    public boolean active() {
        return active;
    }

    public NodeSourceKind source() {
        return source;
    }

    private void refresh() {
        NodeSourceKind found = null;
        if (CreateToggles.enabled(ThaummotorIntegration.TOGGLE)) {
            found = drawPower();
        }
        boolean nowActive = found != null;
        float speed = nowActive && speedSetting != null ? speedSetting.getValue() : 0.0F;
        float capacity = nowActive ? ThaummotorConfig.capacity(found) / ThaummotorConfig.speed() : 0.0F;
        boolean changed = nowActive != active || found != source
                || Float.compare(capacity, capacityPerRpm) != 0 || Float.compare(speed, generatedSpeed) != 0;
        active = nowActive;
        source = found;
        capacityPerRpm = capacity;
        generatedSpeed = speed;
        if (changed) {
            setChanged();
            updateGeneratedRotation();
        }
    }

    private NodeSourceKind drawPower() {
        int units = ThaummotorConfig.drainUnits();
        for (NodeSourceKind kind : NodePowerConfig.order()) {
            Optional<NodeSource> found = NodePower.find(level, worldPosition, ThaummotorConfig.radius(), EnumSet.of(kind));
            if (found.isEmpty()) {
                continue;
            }
            NodeSource candidate = found.get();
            if (candidate.drain(units, true) >= units && candidate.drain(units, false) >= units) {
                return candidate.kind();
            }
        }
        return null;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        tooltip.add(Component.literal(INDENT).append(Component.translatable(ThaummotorTranslations.TOOLTIP_TITLE).withStyle(ChatFormatting.GRAY)));
        if (active && source != null) {
            tooltip.add(Component.literal(INDENT + INDENT)
                    .append(Component.translatable(ThaummotorTranslations.TOOLTIP_SOURCE,
                                    Component.translatable(ThaummotorTranslations.sourceKey(source)).withStyle(ChatFormatting.LIGHT_PURPLE))
                            .withStyle(ChatFormatting.DARK_GRAY)));
        } else {
            tooltip.add(Component.literal(INDENT + INDENT)
                    .append(Component.translatable(ThaummotorTranslations.TOOLTIP_NO_SOURCE).withStyle(ChatFormatting.RED)));
        }
        return true;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean(ACTIVE_KEY, active);
        tag.putString(SOURCE_KEY, source == null ? "" : source.id());
        tag.putFloat(CAPACITY_KEY, capacityPerRpm);
        tag.putFloat(SPEED_KEY, generatedSpeed);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        active = tag.getBoolean(ACTIVE_KEY);
        source = NodeSourceKind.byId(tag.getString(SOURCE_KEY));
        capacityPerRpm = tag.getFloat(CAPACITY_KEY);
        generatedSpeed = tag.getFloat(SPEED_KEY);
    }
}
