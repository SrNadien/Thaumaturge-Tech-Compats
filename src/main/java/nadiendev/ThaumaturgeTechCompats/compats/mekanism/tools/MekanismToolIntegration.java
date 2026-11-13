package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class MekanismToolIntegration {
    public static final String MOD_ID = "mekanism";
    public static final String TOGGLE = "mekanism_energy_tools";

    private static boolean registered;

    private MekanismToolIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean active() {
        return registered;
    }

    public static boolean tierEnabled(MekanismToolTier tier) {
        for (MekanismToolTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.toggle())) {
                return false;
            }
        }
        return CompatToggles.enabled(TOGGLE);
    }

    public static void register(IEventBus modBus) {
        if (!isLoaded() || !CompatToggles.enabled(TOGGLE)) {
            return;
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.STARTUP, MekanismToolConfig.SPEC, MekanismToolConfig.FILE_NAME);
        MekanismToolRegistry.register(modBus);
        NeoForge.EVENT_BUS.addListener(MekanismToolBehavior::onAttributes);
        registered = true;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!active()) {
            return;
        }
        for (Item item : MekanismToolRegistry.items()) {
            output.accept(item);
            output.accept(MekanismToolBehavior.charged(item));
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new MekanismToolItemModels(output));
        generator.addProvider(event.includeServer(), new MekanismToolContent(output));
    }
}
