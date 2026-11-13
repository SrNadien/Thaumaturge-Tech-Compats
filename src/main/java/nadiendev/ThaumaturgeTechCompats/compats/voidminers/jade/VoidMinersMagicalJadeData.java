package nadiendev.ThaumaturgeTechCompats.compats.voidminers.jade;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalMinerBlockEntity;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalPower;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalSolarBlockEntity;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalUpgradeTotals;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalConfig;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import nadiendev.voidminersremastered.world.block.entity.HaltReason;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public enum VoidMinersMagicalJadeData implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final ResourceLocation UID = ThaumaturgeTechCompats.id("voidminers_aura");
    public static final String ROOT = "ThaumaturgeTechCompatsAura";
    public static final String SOURCE = "Source";
    public static final String FAILURE = "Failure";
    public static final String HALT = "Halt";
    public static final String PROGRESS = "Progress";
    public static final String MAX_PROGRESS = "MaxProgress";
    public static final String UNITS = "Units";
    public static final String INTERVAL = "Interval";
    public static final String SPEED = "Speed";
    public static final String PRODUCTION = "Production";
    public static final String COST = "Cost";
    public static final String GENERATION_FACTOR = "GenerationFactor";
    public static final String GENERATION = "Generation";
    public static final String SOLAR = "Solar";

    private static final int AFTER_VOID_MINERS = 1000;
    private static final String ENERGY = "Energy";
    private static final String MAX_ENERGY = "MaxEnergy";
    private static final String VM_HALT = "HaltReason";

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public int getDefaultPriority() {
        return AFTER_VOID_MINERS;
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        BlockEntity entity = accessor.getBlockEntity();
        CompoundTag tag = new CompoundTag();
        if (entity instanceof MagicalMinerBlockEntity miner) {
            clear(data);
            data.remove(ENERGY);
            data.remove(MAX_ENERGY);
            put(tag, miner.activeSource(), miner.failure(), miner.getHaltReason(), miner.upgradeTotals());
            tag.putInt(PROGRESS, miner.getProgress());
            tag.putInt(MAX_PROGRESS, miner.getMaxProgress());
            tag.putInt(UNITS, miner.auraUnits());
            tag.putInt(INTERVAL, VoidMinersMagicalConfig.minerInterval());
        } else if (entity instanceof MagicalSolarBlockEntity solar) {
            clear(data);
            put(tag, solar.activeSource(), solar.failure(), solar.getHaltReason(), solar.upgradeTotals());
            tag.putBoolean(SOLAR, true);
            tag.putLong(GENERATION, solar.generationPerTick());
            tag.putFloat(GENERATION_FACTOR, solar.generationFactor());
            tag.putInt(UNITS, solar.auraUnits());
            tag.putInt(INTERVAL, VoidMinersMagicalConfig.solarInterval());
        } else {
            return;
        }
        data.put(ROOT, tag);
    }

    private static void clear(CompoundTag data) {
        data.remove(VM_HALT);
        data.remove(PROGRESS);
        data.remove(MAX_PROGRESS);
    }

    private static void put(CompoundTag tag, NodeSourceKind source, MagicalPower.Failure failure, HaltReason halt, MagicalUpgradeTotals totals) {
        tag.putString(SOURCE, source == null ? "" : source.id());
        tag.putString(FAILURE, failure.id());
        tag.putInt(HALT, HaltReason.getIntFromHaltReason(halt));
        tag.putFloat(SPEED, totals.speed());
        tag.putFloat(PRODUCTION, totals.production());
        tag.putFloat(COST, totals.cost());
    }
}
