package nadiendev.ThaumaturgeTechCompats.compats.jade;

import com.leclowndu93150.thaumaturge.content.essentia.smeltery.SmelterDataMaps;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.SmelterStats;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.braincharger.BoostTier;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterTier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public enum TierDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final ResourceLocation UID = ThaumaturgeTechCompats.id("block_info");

    private static final int ADVANCED_LOCK = 2;
    private static final int ADVANCED_FEEDING = 20;
    private static final int THAUMATURGE_BOX_FORMULAS = 2;
    private static final int THAUMATURGE_CHARGER_RADIUS = 1;

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (!JadeConfig.enabled()) {
            return;
        }
        JadeBlocks.Entry entry = JadeBlocks.find(accessor.getBlock());
        if (entry == null) {
            return;
        }
        CompoundTag tag = new CompoundTag();
        tag.putString(JadeKeys.FAMILY, entry.family().option());
        tag.putInt(JadeKeys.TIER, entry.tier());
        tag.putBoolean(JadeKeys.SHOW_TIER, JadeConfig.enabled(JadeFamily.TIER));
        if (JadeConfig.enabled(entry.family())) {
            CompoundTag stats = stats(entry, accessor.getBlock());
            if (stats != null) {
                tag.put(JadeKeys.STATS, stats);
            }
        }
        data.put(JadeKeys.ROOT, tag);
    }

    private static CompoundTag stats(JadeBlocks.Entry entry, Block block) {
        DeviceTier device = DeviceTier.values()[entry.tier()];
        BoostTier boost = BoostTier.values()[entry.tier()];
        CompoundTag stats = new CompoundTag();
        switch (entry.family()) {
            case STABILIZER -> {
                boolean enabled = DeviceConfig.stabilizersEnabled();
                stats.putInt(JadeKeys.LOCK, enabled ? device.lockLevel() : ADVANCED_LOCK);
                stats.putInt(JadeKeys.FEEDING, enabled ? DeviceConfig.feedingFactor(device) : ADVANCED_FEEDING);
                stats.putInt(JadeKeys.SPEED, enabled ? device.lockLevel() : ADVANCED_LOCK);
            }
            case SMELTER -> {
                SmelterTier tier = SmelterTier.values()[entry.tier()];
                SmelterStats smelter = block.builtInRegistryHolder().getData(SmelterDataMaps.SMELTER_STATS);
                stats.putInt(JadeKeys.INTERVAL, smelter != null ? smelter.smeltInterval() : tier.smeltInterval());
                stats.putFloat(JadeKeys.EFFICIENCY, smelter != null ? smelter.efficiency() : tier.efficiency());
            }
            case TRANSDUCER -> {
                boolean enabled = DeviceConfig.transducersEnabled();
                stats.putInt(JadeKeys.CHARGE, enabled ? DeviceConfig.chargePerTick(device) : 1);
                stats.putInt(JadeKeys.DECAY, enabled ? DeviceConfig.decayInterval(device) : 1);
            }
            case FURNACE -> {
                boolean enabled = DeviceConfig.furnacesEnabled();
                stats.putInt(JadeKeys.SPEED, enabled ? DeviceConfig.furnaceSpeed(device) : 1);
                stats.putInt(JadeKeys.SAVINGS, enabled ? DeviceConfig.furnaceSavings(device) : 0);
            }
            case BRAIN_BOX -> stats.putInt(JadeKeys.FORMULAS, BrainChargerConfig.boxesEnabled() ? BrainChargerConfig.formulas(boost) : THAUMATURGE_BOX_FORMULAS);
            case CHARGER -> {
                boolean enabled = BrainChargerConfig.chargersEnabled();
                stats.putInt(JadeKeys.DISCOUNT, enabled ? BrainChargerConfig.discountPercent(boost) : 0);
                stats.putInt(JadeKeys.RADIUS, enabled ? BrainChargerConfig.radius(boost) : THAUMATURGE_CHARGER_RADIUS);
            }
            default -> {
                return null;
            }
        }
        return stats;
    }
}
