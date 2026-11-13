package nadiendev.ThaumaturgeTechCompats.compats.create;

import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CreateCasings;
import nadiendev.ThaumaturgeTechCompats.compats.create.data.CreateBlockStates;
import nadiendev.ThaumaturgeTechCompats.compats.create.data.CreateContent;
import nadiendev.ThaumaturgeTechCompats.compats.create.data.CreateItemModels;
import nadiendev.ThaumaturgeTechCompats.compats.create.data.CreateVariantModels;
import nadiendev.ThaumaturgeTechCompats.compats.create.seal.CreateSeals;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CreateVariants;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantBootstrap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;

public final class CreateIntegration {
    public static final String MOD_ID = "create";

    private CreateIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    public static void register(IEventBus modBus) {
        CreateCasings.BLOCKS.register(modBus);
        CreateCasings.ITEMS.register(modBus);
        CreateSeals.ITEMS.register(modBus);
        CreateSeals.SEALS.register(modBus);
        if (isLoaded()) {
            VariantBootstrap.register(modBus);
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, CreateConfig.SPEC, CreateConfig.FILE_NAME);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>(CreateCasings.blocks());
        if (isLoaded()) {
            blocks.addAll(CreateVariants.allBlocks());
        }
        return blocks;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!isLoaded() || !CreateToggles.enabled(CreateToggles.TECH)) {
            return;
        }
        if (CreateToggles.enabled(CreateToggles.SEAL)) {
            output.accept(CreateSeals.ITEM.get());
        }
        for (CasingTier tier : CasingTier.values()) {
            if (CreateToggles.enabled(tier.toggle())) {
                output.accept(CreateCasings.item(tier).get());
            }
        }
        VariantBootstrap.fillCreativeTab(output);
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new CreateBlockStates(output, helper));
        generator.addProvider(event.includeClient(), new CreateItemModels(output, helper));
        generator.addProvider(event.includeClient(), new CreateVariantModels(output, helper));
        generator.addProvider(event.includeServer(), new CreateContent(output));
    }
}
