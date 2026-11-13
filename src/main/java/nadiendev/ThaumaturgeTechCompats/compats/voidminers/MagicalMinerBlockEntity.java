package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import nadiendev.voidminersremastered.common.energy.MinerEnergyStorage;
import nadiendev.voidminersremastered.world.block.entity.MinerControllerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MagicalMinerBlockEntity extends MinerControllerBE {
    private static final Field MODIFIER_MAP = MagicalUpgradeTotals.modifierField(MinerControllerBE.class);

    private final MinerEnergyStorage sealedStorage = new MinerEnergyStorage(0);
    private final MagicalPower power = new MagicalPower();
    private MagicalUpgradeTotals totals = MagicalUpgradeTotals.NONE;
    private boolean ticking;

    public MagicalMinerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public VoidMinersMagicalTier tier() {
        return getBlockState().getBlock() instanceof MagicalMinerBlock block ? block.tier() : VoidMinersMagicalTier.THAUMIUM;
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, Identifier structure, String name) {
        MinerEnergyStorage internal = super.getEnergyStorage();
        if (internal != null) {
            internal.setEnergy(0);
        }
        ticking = true;
        try {
            super.tick(level, pos, state, structure, name);
        } finally {
            ticking = false;
        }
    }

    public NodeSourceKind activeSource() {
        return power.paid(level) ? power.source() : null;
    }

    public MagicalPower.Failure failure() {
        return power.failure();
    }

    public MagicalUpgradeTotals upgradeTotals() {
        return totals;
    }

    public int auraUnits() {
        return Math.max(1, Math.round(VoidMinersMagicalConfig.minerUnits(tier()) * totals.cost()));
    }

    public float speedFactor() {
        return getSpeedModifierMultiplier() * VoidMinersMagicalConfig.minerSpeed(tier()) * totals.speed();
    }

    @Override
    public void checkStructure(Level level, BlockPos pos) {
        super.checkStructure(level, pos);
        totals = foundStructure ? MagicalUpgradeTotals.scan(MagicalUpgradeTotals.modifiers(MODIFIER_MAP, this)) : MagicalUpgradeTotals.NONE;
    }

    @Override
    public int getRFPerTick() {
        if (!ticking || level == null) {
            return 0;
        }
        VoidMinersMagicalTier tier = tier();
        boolean active = power.active(level, getBlockPos(), VoidMinersMagicalConfig.minerRadius(tier),
                VoidMinersMagicalConfig.minerSources(), auraUnits(), VoidMinersMagicalConfig.minerInterval());
        return active ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public int getMaxProgress() {
        return Math.max(1, (int) (VoidMinersMagicalConfig.minerDuration(tier()) / speedFactor()));
    }

    @Override
    public float getItemModifierMultiplier() {
        return super.getItemModifierMultiplier() * VoidMinersMagicalConfig.minerProduction(tier()) * totals.production();
    }

    @Override
    public int getMinerTier() {
        return VoidMinersMagicalConfig.minerTier(tier());
    }

    @Override
    public int getBeamColor() {
        return VoidMinersMagicalConfig.argb(tier());
    }

    @Override
    public MinerEnergyStorage getEnergyStorage() {
        return sealedStorage;
    }

    @Override
    public List<Component> getInteractionTooltip() {
        List<Component> original = super.getInteractionTooltip();
        List<Component> lines = new ArrayList<>();
        VoidMinersMagicalTier tier = tier();
        for (int i = 0; i < original.size(); i++) {
            Component line = original.get(i);
            if (i == 0) {
                lines.add(MagicalTooltip.header(tier, false));
                continue;
            }
            switch (MagicalTooltip.key(line)) {
                case MagicalTooltip.ENERGY -> lines.add(MagicalTooltip.aura(activeSource()));
                case MagicalTooltip.CONSUMPTION -> lines.add(MagicalTooltip.cost(auraUnits(), VoidMinersMagicalConfig.minerInterval(), totals.cost()));
                case MagicalTooltip.DURATION -> lines.add(MagicalTooltip.duration(getMaxProgress(), speedFactor()));
                case MagicalTooltip.ITEM_BOOST -> lines.add(MagicalTooltip.boost(getItemModifierMultiplier()));
                case MagicalTooltip.NOT_ENOUGH_POWER, MagicalTooltip.NOT_ENOUGH_POWER_BUFFER, MagicalTooltip.NOT_ENOUGH_POWER_NEXT ->
                        lines.add(MagicalTooltip.reason(power.failure()));
                default -> {
                    lines.add(line);
                    if (MagicalTooltip.isUpgradeLine(line)) {
                        lines.add(MagicalTooltip.upgrades(totals, false));
                    }
                }
            }
        }
        return lines;
    }
}
