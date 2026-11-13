package nadiendev.ThaumaturgeTechCompats.flux.core;

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

public final class CoreIntegration {
    public static final String CORE_TOGGLE = "flux_storage_core";
    public static final String CELL_TOGGLE = "flux_capacity_cell";

    private CoreIntegration() {
    }

    public static void register(IEventBus modBus) {
        CoreRegistry.BLOCKS.register(modBus);
        CoreRegistry.ITEMS.register(modBus);
        CoreRegistry.BLOCK_ENTITIES.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, CoreConfig.SPEC, CoreConfig.FILE_NAME);
    }

    public static List<String> toggleIds() {
        return List.of(CORE_TOGGLE, CELL_TOGGLE);
    }

    public static List<Block> blocks() {
        return CoreRegistry.blocks();
    }

    public static boolean tierEnabled(FluxTier tier) {
        for (FluxTier step = tier; step != null; step = step.previous()) {
            if (!CompatToggles.enabled(step.toggle())) {
                return false;
            }
        }
        return true;
    }

    public static boolean enabled(FluxTier tier) {
        return tierEnabled(tier) && CompatToggles.enabled(CORE_TOGGLE);
    }

    public static boolean cellEnabled(FluxTier tier) {
        return tierEnabled(tier) && CompatToggles.enabled(CELL_TOGGLE);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (FluxTier tier : FluxTier.values()) {
            if (enabled(tier)) {
                output.accept(CoreRegistry.coreItem(tier).get());
            }
            if (cellEnabled(tier)) {
                output.accept(CoreRegistry.cell(tier).get());
            }
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new CoreAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new CoreContent(output));
    }
}
