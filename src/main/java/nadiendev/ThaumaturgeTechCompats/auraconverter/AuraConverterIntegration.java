package nadiendev.ThaumaturgeTechCompats.auraconverter;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class AuraConverterIntegration {
    private AuraConverterIntegration() {
    }

    public static void register(IEventBus modBus, ModContainer container) {
        AuraConverterRegistry.BLOCKS.register(modBus);
        AuraConverterRegistry.ITEMS.register(modBus);
        AuraConverterRegistry.BLOCK_ENTITIES.register(modBus);
        AuraConverterRegistry.MENUS.register(modBus);
        modBus.addListener(AuraConverterIntegration::registerCapabilities);
        container.registerConfig(ModConfig.Type.COMMON, AuraConverterConfig.SPEC, AuraConverterConfig.FILE_NAME);
    }

    public static List<Block> blocks() {
        return AuraConverterRegistry.blocks();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            if (AuraConverterConfig.enabled(tier)) {
                output.accept(AuraConverterRegistry.item(tier).get());
            }
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new AuraConverterBlockStates(output, helper));
        generator.addProvider(event.includeClient(), new AuraConverterItemModels(output, helper));
        generator.addProvider(event.includeServer(), new AuraConverterContent(output));
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, AuraConverterRegistry.ENTITY.get(), (converter, side) -> converter.energyStorage());
    }
}
