package nadiendev.ThaumaturgeTechCompats.planetary.salis;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public final class SalisIntegration {
    public static final String TOGGLE = "salis_mundus_variants";
    public static final String BASE = "thaumaturge:salis_mundus";

    private static final List<PlanetaryTier> TIERS = List.of(
            PlanetaryTier.BLAZE_CUBE, PlanetaryTier.MATRIX, PlanetaryTier.NEUTRONIUM, PlanetaryTier.INFINITY);

    private SalisIntegration() {
    }

    public static boolean enabled() {
        return CompatToggles.enabled(TOGGLE);
    }

    public static List<PlanetaryTier> tiers() {
        return TIERS;
    }

    public static String itemName(PlanetaryTier tier) {
        return tier.id() + "_salis_mundus";
    }

    public static String itemId(PlanetaryTier tier) {
        return ThaumaturgeTechCompats.MOD_ID + ":" + itemName(tier);
    }

    public static DeferredItem<?> item(PlanetaryTier tier) {
        return SalisRegistry.item(tier);
    }

    public static void register(IEventBus modBus) {
        if (!enabled()) {
            return;
        }
        SalisRegistry.register(modBus);
        NeoForge.EVENT_BUS.addListener(SalisVariantItem::allowUseOnBlock);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!enabled()) {
            return;
        }
        for (Item item : SalisRegistry.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new SalisAssets(output));
        generator.addProvider(event.includeServer(), new SalisContent(output));
    }
}
