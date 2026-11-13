package nadiendev.ThaumaturgeTechCompats.emc;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public final class EmcIntegration {
    public static final String TOGGLE = "projecte_emc";
    public static final String MOD_ID = "projecte";
    public static final String PACK_PATH = "resourcepacks/projecte_emc";
    public static final String ENTRY = "projecte_emc";
    public static final String RECIPE_MAPPER_KEY = "emc." + ThaumaturgeTechCompats.MOD_ID + ".recipes";
    public static final String EXCLUSION_MAPPER_KEY = "emc." + ThaumaturgeTechCompats.MOD_ID + ".exclusions";
    public static final TagKey<Item> NO_EMC = TagKey.create(Registries.ITEM, ThaumaturgeTechCompats.id("no_emc"));

    private EmcIntegration() {
    }

    public static boolean loaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean enabled() {
        return CompatToggles.enabled(TOGGLE);
    }

    public static void register(IEventBus modBus) {
        if (!loaded() || !enabled()) {
            return;
        }
        modBus.addListener(EmcIntegration::addPacks);
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeServer(), new EmcExclusionTags(output));
        generator.addProvider(event.includeServer(), new EmcPackProvider(output));
        generator.addProvider(event.includeServer(), new EmcContent(output));
        if (loaded()) {
            PackOutput packOutput = new PackOutput(output.getOutputFolder().resolve(PACK_PATH));
            generator.addProvider(event.includeServer(), new EmcValuesProvider(packOutput, event.getLookupProvider()));
        }
    }

    private static void addPacks(AddPackFindersEvent event) {
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, PACK_PATH),
                PackType.SERVER_DATA,
                Component.literal("Thaumaturge Tech Compats ProjectE values"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.BOTTOM);
    }
}
