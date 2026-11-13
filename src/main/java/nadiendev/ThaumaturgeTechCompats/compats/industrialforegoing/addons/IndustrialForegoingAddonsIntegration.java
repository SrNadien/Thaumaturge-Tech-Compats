package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;

public final class IndustrialForegoingAddonsIntegration {
    public static final String MOD_ID = "industrialforegoing";
    public static final String TOGGLE = "industrialforegoing_addons";
    public static final String TITANIUM_AUGMENTS = "titanium:augments";
    public static final String LIQUID_DEATH = "thaumaturge:liquid_death";

    private IndustrialForegoingAddonsIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    public static String itemId(AddonKind kind, AddonTier tier) {
        return tier.id() + "_" + kind.id() + "_addon";
    }

    public static String toggleId(AddonKind kind, AddonTier tier) {
        return MOD_ID + "_addon_" + tier.id() + "_" + kind.id();
    }

    public static List<String> toggleIds() {
        List<String> ids = new ArrayList<>();
        for (AddonTier tier : AddonTier.values()) {
            for (AddonKind kind : AddonKind.values()) {
                ids.add(toggleId(kind, tier));
            }
        }
        return ids;
    }

    public static void register(IEventBus modBus, ModContainer container) {
        if (!isLoaded() || !CompatToggles.enabled(TOGGLE)) {
            return;
        }
        container.registerConfig(ModConfig.Type.STARTUP, IndustrialForegoingAddonsConfig.SPEC, IndustrialForegoingAddonsConfig.FILE_NAME);
        IndustrialForegoingAddonItems.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (Item item : IndustrialForegoingAddonItems.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new IndustrialForegoingAddonsItemModels(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new IndustrialForegoingAddonsContent(output));
    }
}
