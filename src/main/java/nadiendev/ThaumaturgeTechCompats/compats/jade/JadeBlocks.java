package nadiendev.ThaumaturgeTechCompats.compats.jade;

import nadiendev.ThaumaturgeTechCompats.braincharger.BoostTier;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerRegistry;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.jar.JarRegistry;
import nadiendev.ThaumaturgeTechCompats.jar.JarTier;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterIntegration;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterTier;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import net.minecraft.world.level.block.Block;

import java.util.IdentityHashMap;
import java.util.Map;

public final class JadeBlocks {
    public record Entry(JadeFamily family, int tier) {
    }

    private static final class Holder {
        private static final Map<Block, Entry> ENTRIES = build();

        private static Map<Block, Entry> build() {
            Map<Block, Entry> entries = new IdentityHashMap<>();
            for (StabilizerTier tier : StabilizerTier.values()) {
                entries.put(StabilizerIntegration.block(tier).get(), new Entry(JadeFamily.STABILIZER, tier.ordinal()));
            }
            for (SmelterTier tier : SmelterTier.values()) {
                entries.put(SmelterIntegration.block(tier).get(), new Entry(JadeFamily.SMELTER, tier.ordinal()));
            }
            for (DeviceTier tier : DeviceTier.values()) {
                entries.put(TransducerIntegration.block(tier).get(), new Entry(JadeFamily.TRANSDUCER, tier.ordinal()));
                entries.put(FurnaceIntegration.block(tier).get(), new Entry(JadeFamily.FURNACE, tier.ordinal()));
            }
            for (BoostTier tier : BoostTier.values()) {
                entries.put(BrainChargerRegistry.box(tier).get(), new Entry(JadeFamily.BRAIN_BOX, tier.ordinal()));
                entries.put(BrainChargerRegistry.charger(tier).get(), new Entry(JadeFamily.CHARGER, tier.ordinal()));
            }
            for (JarTier tier : JarTier.values()) {
                entries.put(JarRegistry.block(tier).get(), new Entry(JadeFamily.JAR, tier.ordinal()));
            }
            return entries;
        }
    }

    private JadeBlocks() {
    }

    public static Entry find(Block block) {
        return Holder.ENTRIES.get(block);
    }
}
