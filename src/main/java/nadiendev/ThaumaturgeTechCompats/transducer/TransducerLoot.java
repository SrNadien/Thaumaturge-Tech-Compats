package nadiendev.ThaumaturgeTechCompats.transducer;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class TransducerLoot extends BlockLootSubProvider {
    public TransducerLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    public static LootTableProvider.SubProviderEntry entry() {
        return new LootTableProvider.SubProviderEntry(TransducerLoot::new, LootContextParamSets.BLOCK);
    }

    @Override
    protected void generate() {
        TransducerIntegration.blocks().forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return TransducerIntegration.blocks();
    }
}
