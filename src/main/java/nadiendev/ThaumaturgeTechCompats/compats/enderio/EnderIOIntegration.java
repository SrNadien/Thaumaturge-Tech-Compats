package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.CapacitorConfig;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.CapacitorContent;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.CapacitorItemModels;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.CapacitorRegistry;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank.CapacitorBankAssets;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank.CapacitorBankContent;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank.CapacitorBankRegistry;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank.CapacitorBankSupport;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit.ConduitConfig;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit.ConduitContent;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit.ConduitPack;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.wand.EnderIOWandConfig;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.wand.EnderIOWandContent;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.wand.EnderIOWandItemModels;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.wand.EnderIOWandRegistry;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class EnderIOIntegration {
    public static final String MOD_ID = "enderio";
    public static final String CONDUITS = "enderio_conduits";
    public static final String CAPACITORS = "enderio_capacitors";
    public static final String CAPACITOR_BANKS = "enderio_capacitor_banks";
    public static final String WAND_PARTS = "enderio_wand_parts";

    private EnderIOIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean enabled(String feature) {
        return isLoaded() && CompatToggles.enabled(CompatToggles.ENDERIO) && CompatToggles.enabled(feature);
    }

    public static void register(IEventBus modBus) {
        if (!isLoaded() || !CompatToggles.enabled(CompatToggles.ENDERIO)) {
            return;
        }
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        if (CompatToggles.enabled(CompatToggles.ENDERIO_GRINDING_BALLS)) {
            EnderIORegistry.registerBalls();
        }
        if (CompatToggles.enabled(CONDUITS)) {
            container.registerConfig(ModConfig.Type.COMMON, ConduitConfig.SPEC, ConduitConfig.FILE_NAME);
            modBus.addListener(ConduitPack::register);
        }
        if (CompatToggles.enabled(CAPACITORS)) {
            container.registerConfig(ModConfig.Type.STARTUP, CapacitorConfig.SPEC, CapacitorConfig.FILE_NAME);
            CapacitorRegistry.register(modBus);
        }
        if (CompatToggles.enabled(WAND_PARTS)) {
            container.registerConfig(ModConfig.Type.STARTUP, EnderIOWandConfig.SPEC, EnderIOWandConfig.FILE_NAME);
            EnderIOWandRegistry.register(modBus);
        }
        EnderIORegistry.ITEMS.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (enabled(CompatToggles.ENDERIO_GRINDING_BALLS)) {
            EnderIORegistry.balls().values().forEach(output::accept);
        }
        if (enabled(CAPACITORS)) {
            CapacitorRegistry.fillCreativeTab(output);
        }
        if (enabled(CAPACITORS) && enabled(CAPACITOR_BANKS)) {
            CapacitorBankRegistry.fillCreativeTab(output);
        }
        if (enabled(WAND_PARTS)) {
            EnderIOWandRegistry.fillCreativeTab(output);
        }
    }

    public static List<Identifier> blockIds() {
        return CapacitorBankSupport.blockIds();
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new EnderIOItemModels(output));
        generator.addProvider(event instanceof GatherDataEvent.Client, new CapacitorItemModels(output));
        generator.addProvider(event instanceof GatherDataEvent.Client, new EnderIOWandItemModels(output));
        generator.addProvider(event instanceof GatherDataEvent.Client, new CapacitorBankAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new EnderIOContent(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new ConduitContent(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new CapacitorContent(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new CapacitorBankContent(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new EnderIOWandContent(output));
    }
}
