package nadiendev.ThaumaturgeTechCompats.essences;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public final class EssencesIntegration {
    public static final String TOGGLE = "own_essences";
    public static final String PACK_PATH = "resourcepacks/own_essences";

    private EssencesIntegration() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(EssencesIntegration::addPacks);
    }

    public static void addProviders(GatherDataEvent event) {
        event.getGenerator().addProvider(event instanceof GatherDataEvent.Server, new EssencesProvider(event.getGenerator().getPackOutput()));
        event.getGenerator().addProvider(event instanceof GatherDataEvent.Server, new EssencesContent(event.getGenerator().getPackOutput()));
    }

    private static void addPacks(AddPackFindersEvent event) {
        event.addPackFinders(
                Identifier.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, PACK_PATH),
                PackType.SERVER_DATA,
                Component.literal("Thaumaturge Tech Compats own essences"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP);
    }
}
