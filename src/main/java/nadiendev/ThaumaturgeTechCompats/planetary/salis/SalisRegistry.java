package nadiendev.ThaumaturgeTechCompats.planetary.salis;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class SalisRegistry {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<PlanetaryTier, DeferredItem<SalisVariantItem>> VARIANTS = new EnumMap<>(PlanetaryTier.class);

    static {
        for (PlanetaryTier tier : SalisIntegration.tiers()) {
            Item.Properties properties = new Item.Properties().rarity(tier.rarity());
            if (tier != PlanetaryTier.MATRIX) {
                properties.fireResistant();
            }
            VARIANTS.put(tier, ITEMS.registerItem(SalisIntegration.itemName(tier), itemProperties -> new SalisVariantItem(tier, itemProperties), () -> properties));
        }
    }

    private SalisRegistry() {
    }

    static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    static DeferredItem<SalisVariantItem> item(PlanetaryTier tier) {
        return VARIANTS.get(tier);
    }

    static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (PlanetaryTier tier : SalisIntegration.tiers()) {
            items.add(VARIANTS.get(tier).get());
        }
        return items;
    }
}
