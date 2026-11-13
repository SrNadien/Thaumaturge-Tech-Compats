package nadiendev.ThaumaturgeTechCompats.smelter;

import com.leclowndu93150.thaumaturge.content.essentia.smeltery.SmelterDataMaps;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.SmelterStats;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class SmelterStatsProvider extends DataMapProvider {
    public SmelterStatsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var builder = builder(SmelterDataMaps.SMELTER_STATS);
        for (SmelterTier tier : SmelterTier.values()) {
            builder.add(ThaumaturgeTechCompats.id(tier.blockName()), new SmelterStats(tier.smeltInterval(), tier.efficiency()), false);
        }
    }
}
