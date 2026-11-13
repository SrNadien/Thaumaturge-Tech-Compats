package nadiendev.ThaumaturgeTechCompats.compats.create.data;

import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class CreateLoot extends BlockLootSubProvider {
    public CreateLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    public static LootTableProvider.SubProviderEntry entry() {
        return new LootTableProvider.SubProviderEntry(CreateLoot::new, LootContextParamSets.BLOCK);
    }

    @Override
    protected void generate() {
        CreateIntegration.blocks().forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CreateIntegration.blocks();
    }
}
