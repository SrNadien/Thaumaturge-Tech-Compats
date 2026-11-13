package nadiendev.ThaumaturgeTechCompats.furnace;

import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class FurnaceLoot extends BlockLootSubProvider {
    public FurnaceLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    public static LootTableProvider.SubProviderEntry entry() {
        return new LootTableProvider.SubProviderEntry(FurnaceLoot::new, LootContextParamSets.BLOCK);
    }

    @Override
    protected void generate() {
        for (DeviceTier tier : DeviceTier.values()) {
            dropOther(FurnaceIntegration.block(tier).get(), FurnaceIntegration.smelter(tier));
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FurnaceIntegration.blocks();
    }
}
