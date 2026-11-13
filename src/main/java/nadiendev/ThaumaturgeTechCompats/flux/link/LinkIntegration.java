package nadiendev.ThaumaturgeTechCompats.flux.link;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class LinkIntegration {
    private LinkIntegration() {
    }

    public static void register(IEventBus modBus) {
        LinkComponents.COMPONENTS.register(modBus);
        LinkRegistry.BLOCKS.register(modBus);
        LinkRegistry.ITEMS.register(modBus);
        LinkRegistry.BLOCK_ENTITIES.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, LinkConfig.SPEC, LinkConfig.FILE_NAME);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (CompatToggles.enabled(FluxTier.THAUMIUM.toggle())) {
            output.accept(LinkRegistry.EXTRACTOR_ITEM.get());
            output.accept(LinkRegistry.LINKER.get());
        }
        for (FluxTier tier : FluxTier.values()) {
            if (CompatToggles.enabled(tier.toggle())) {
                output.accept(LinkRegistry.linkItem(tier).get());
            }
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new LinkAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new LinkContent(output));
    }

    public static List<Block> blocks() {
        return LinkRegistry.blocks();
    }
}
