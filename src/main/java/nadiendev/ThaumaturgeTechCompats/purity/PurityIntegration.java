package nadiendev.ThaumaturgeTechCompats.purity;

import nadiendev.ThaumaturgeTechCompats.purity.biome.BiomePurificationConfig;
import nadiendev.ThaumaturgeTechCompats.purity.warp.WarpPotionConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class PurityIntegration {
    private PurityIntegration() {
    }

    public static void register(IEventBus modBus) {
        PurityRegistry.ITEMS.register(modBus);
        PurityRegistry.ENTITIES.register(modBus);
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        container.registerConfig(ModConfig.Type.COMMON, PurityConfig.SPEC, PurityConfig.FILE_NAME);
        container.registerConfig(ModConfig.Type.COMMON, BiomePurificationConfig.SPEC, BiomePurificationConfig.FILE_NAME);
        container.registerConfig(ModConfig.Type.COMMON, WarpPotionConfig.SPEC, WarpPotionConfig.FILE_NAME);
        modBus.addListener(PurityIntegration::onCommonSetup);
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerProjectileBehavior(PurityRegistry.PURIFICATION_POTION.get());
            DispenserBlock.registerProjectileBehavior(PurityRegistry.BIOME_POTION.get());
        });
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (PurityConfig.enabled()) {
            output.accept(PurityRegistry.PURIFICATION_POTION.get());
        }
        if (BiomePurificationConfig.enabled()) {
            output.accept(PurityRegistry.BIOME_POTION.get());
        }
        if (WarpPotionConfig.CLARITY.enabled()) {
            output.accept(PurityRegistry.CLARITY_POTION.get());
        }
        if (WarpPotionConfig.ABSOLUTION.enabled()) {
            output.accept(PurityRegistry.ABSOLUTION_POTION.get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event instanceof GatherDataEvent.Client, new PurityItemModels(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new PurityContent(output));
    }
}
