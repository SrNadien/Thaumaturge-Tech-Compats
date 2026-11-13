package nadiendev.ThaumaturgeTechCompats.compats.mekanism;

import nadiendev.ThaumaturgeTechCompats.compats.common.ClusterMaterial;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class MekanismIntegration {
    private MekanismIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded(ClusterMaterial.OWN_MOD);
    }

    public static boolean itemsEnabled() {
        return isLoaded() && CompatToggles.enabled(CompatToggles.MEKANISM_CLUSTERS);
    }

    public static void register(IEventBus modBus) {
        if (itemsEnabled()) {
            MekanismClusterItems.register(modBus);
        }
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!itemsEnabled()) {
            return;
        }
        for (Item item : MekanismClusterItems.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new MekanismClusterItemModels(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new MekanismClusterContent(output));
        generator.addProvider(event.includeServer(), new MekanismClusterProvider(output));
    }
}
