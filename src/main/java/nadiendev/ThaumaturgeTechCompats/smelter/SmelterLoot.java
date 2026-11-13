package nadiendev.ThaumaturgeTechCompats.smelter;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class SmelterLoot extends BlockLootSubProvider {
    public SmelterLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    public static LootTableProvider.SubProviderEntry entry() {
        return new LootTableProvider.SubProviderEntry(SmelterLoot::new, LootContextParamSets.BLOCK);
    }

    @Override
    protected void generate() {
        SmelterIntegration.blocks().forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SmelterIntegration.blocks();
    }
}
