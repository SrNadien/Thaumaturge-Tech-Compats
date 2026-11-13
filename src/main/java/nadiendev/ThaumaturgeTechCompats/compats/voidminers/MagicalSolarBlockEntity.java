package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import nadiendev.voidminersremastered.common.energy.SolarEnergyStorage;
import nadiendev.voidminersremastered.config.SolarConfigLoader;
import nadiendev.voidminersremastered.world.block.entity.HaltReason;
import nadiendev.voidminersremastered.world.block.entity.SolarControllerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MagicalSolarBlockEntity extends SolarControllerBE {
    private static final int STRUCTURE_INTERVAL = 20;
    private static final Field HALT_FIELD = MagicalUpgradeTotals.field(SolarControllerBE.class, "haltReason");
    private static final Field MODIFIER_MAP = MagicalUpgradeTotals.modifierField(SolarControllerBE.class);

    private final MagicalPower power = new MagicalPower();
    private HaltReason halt = HaltReason.NONE;
    private MagicalUpgradeTotals totals = MagicalUpgradeTotals.NONE;
    private float efficiency = 1.0F;
    private boolean producing;
    private int structureTimer;
    private long lastTick = Long.MIN_VALUE;

    public MagicalSolarBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public VoidMinersMagicalTier tier() {
        return getBlockState().getBlock() instanceof MagicalSolarBlock block ? block.tier() : VoidMinersMagicalTier.THAUMIUM;
    }

    @Override
    public void setupEnergyStorage() {
        getEnergyStorage().setLongCapacity(VoidMinersMagicalConfig.solarCapacity(tier()));
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, ResourceLocation structure, String name) {
        if (level.isClientSide) {
            return;
        }
        long time = level.getGameTime();
        if (time == lastTick) {
            return;
        }
        lastTick = time;
        if (getStructure() == null) {
            setup(structure, name);
        }
        boolean wasProducing = producing;
        HaltReason before = halt;
        boolean checked = structureTimer <= 0;
        if (checked) {
            checkStructure(level, pos);
            refreshModifiers();
            structureTimer = STRUCTURE_INTERVAL;
            setChanged();
        } else {
            structureTimer--;
        }
        if (foundStructure && showStructure) {
            updateShowStructure();
        }
        if (!foundStructure) {
            producing = false;
            halt = HaltReason.STRUCTURE_NOT_FOUND;
        } else {
            produce(level, pos);
        }
        canSeeSky = producing;
        if (checked || wasProducing != producing || before != halt) {
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    private void produce(Level level, BlockPos pos) {
        VoidMinersMagicalTier tier = tier();
        SolarEnergyStorage storage = getEnergyStorage();
        pushEnergy(level, pos, storage);
        if (storage.getLongEnergyStored() >= storage.getLongMaxEnergyStored()) {
            producing = false;
            halt = HaltReason.POWER_FULL;
            return;
        }
        boolean active = power.active(level, pos, VoidMinersMagicalConfig.solarRadius(tier),
                VoidMinersMagicalConfig.solarSources(), auraUnits(), VoidMinersMagicalConfig.solarInterval());
        producing = active;
        if (!active) {
            halt = HaltReason.NOT_ENOUGH_POWER;
            return;
        }
        storage.addLongEnergy(generation());
        halt = HaltReason.NONE;
    }

    private long generation() {
        return (long) (VoidMinersMagicalConfig.solarGeneration(tier()) * efficiency * totals.generation());
    }

    private void refreshModifiers() {
        if (!foundStructure) {
            totals = MagicalUpgradeTotals.NONE;
            efficiency = 1.0F;
            return;
        }
        Map<?, ?> modifiers = MagicalUpgradeTotals.modifiers(MODIFIER_MAP, this);
        totals = MagicalUpgradeTotals.scan(modifiers);
        float product = 1.0F;
        for (Map.Entry<?, ?> entry : modifiers.entrySet()) {
            if (!MagicalUpgradeTotals.isMagical(entry.getKey()) && entry.getValue() instanceof SolarConfigLoader.ModifierConfig config) {
                product *= config.efficiency();
            }
        }
        efficiency = product;
    }

    private void pushEnergy(Level level, BlockPos pos, SolarEnergyStorage storage) {
        Direction side = getExportSide();
        Direction[] sides = side == null ? Direction.values() : new Direction[]{side};
        for (Direction direction : sides) {
            if (storage.getLongEnergyStored() <= 0) {
                return;
            }
            IEnergyStorage target = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos.relative(direction), direction.getOpposite());
            if (target == null) {
                continue;
            }
            int offered = (int) Math.min(storage.getLongEnergyStored(), Integer.MAX_VALUE);
            int accepted = target.receiveEnergy(offered, false);
            if (accepted > 0) {
                storage.removeLongEnergy(accepted);
            }
        }
    }

    public MagicalPower.Failure failure() {
        return power.failure();
    }

    public MagicalUpgradeTotals upgradeTotals() {
        return totals;
    }

    public int auraUnits() {
        return Math.max(1, Math.round(VoidMinersMagicalConfig.solarUnits(tier()) * totals.cost()));
    }

    public float generationFactor() {
        return efficiency * totals.generation();
    }

    public NodeSourceKind activeSource() {
        return power.paid(level) ? power.source() : null;
    }

    public long generationPerTick() {
        return producing ? generation() : 0L;
    }

    @Override
    public long getRFPerTick(float efficiency) {
        return producing ? generation() : 0L;
    }

    @Override
    public float getSolarEfficiency() {
        return 1.0F;
    }

    @Override
    public HaltReason getHaltReason() {
        return halt;
    }

    @Override
    public int getBeamColor() {
        return VoidMinersMagicalConfig.argb(tier());
    }

    @Override
    public List<Component> getInteractionTooltip() {
        syncHalt(halt == HaltReason.NOT_ENOUGH_POWER ? HaltReason.NONE : halt);
        List<Component> original = super.getInteractionTooltip();
        syncHalt(halt);
        List<Component> lines = new ArrayList<>();
        VoidMinersMagicalTier tier = tier();
        SolarEnergyStorage storage = getEnergyStorage();
        for (int i = 0; i < original.size(); i++) {
            Component line = original.get(i);
            if (i == 0) {
                lines.add(MagicalTooltip.header(tier, true));
                continue;
            }
            switch (MagicalTooltip.key(line)) {
                case MagicalTooltip.STATUS -> {
                    if (halt == HaltReason.NOT_ENOUGH_POWER) {
                        lines.add(MagicalTooltip.stopped());
                        lines.add(MagicalTooltip.reason(power.failure()));
                    } else {
                        lines.add(line);
                    }
                }
                case MagicalTooltip.ENERGY -> {
                    lines.add(MagicalTooltip.energy(storage.getLongEnergyStored(), storage.getLongMaxEnergyStored()));
                    lines.add(MagicalTooltip.aura(activeSource()));
                    lines.add(MagicalTooltip.cost(auraUnits(), VoidMinersMagicalConfig.solarInterval(), totals.cost()));
                }
                case MagicalTooltip.EFFICIENCY -> {
                    lines.add(MagicalTooltip.efficiency(generationFactor()));
                    lines.add(MagicalTooltip.upgrades(totals, true));
                }
                case MagicalTooltip.LIMITED_BY_RAIN, MagicalTooltip.LIMITED_BY_THUNDER, MagicalTooltip.LIMITED_BY_TIME -> {
                }
                default -> lines.add(line);
            }
        }
        return lines;
    }

    private void syncHalt(HaltReason reason) {
        if (HALT_FIELD == null) {
            return;
        }
        try {
            HALT_FIELD.set(this, reason);
        } catch (IllegalAccessException | RuntimeException exception) {
            return;
        }
    }
}
