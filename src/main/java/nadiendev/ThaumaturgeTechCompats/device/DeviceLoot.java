package nadiendev.ThaumaturgeTechCompats.device;

import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterLoot;
import nadiendev.ThaumaturgeTechCompats.compats.create.data.CreateLoot;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceLoot;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterLoot;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerLoot;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class DeviceLoot {
    private DeviceLoot() {
    }

    public static LootTableProvider provider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new LootTableProvider(output, Set.of(), List.of(SmelterLoot.entry(), StabilizerLoot.entry(), TransducerLoot.entry(), FurnaceLoot.entry(), CreateLoot.entry(), AuraConverterLoot.entry()), registries);
    }
}
