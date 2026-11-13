package nadiendev.ThaumaturgeTechCompats.compats.tags;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public final class TagAspectsIntegration {
    public static final String PACK_PATH = "resourcepacks/tag_aspects";

    private TagAspectsIntegration() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(TagAspectsIntegration::addPacks);
    }

    public static void addProviders(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), new TagAspectsProvider(event.getGenerator().getPackOutput()));
    }

    private static void addPacks(AddPackFindersEvent event) {
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, PACK_PATH),
                PackType.SERVER_DATA,
                Component.literal("Thaumaturge Tech Compats tag aspects"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.BOTTOM);
    }
}
