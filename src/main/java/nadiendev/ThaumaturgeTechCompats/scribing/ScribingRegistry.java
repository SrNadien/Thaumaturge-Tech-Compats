package nadiendev.ThaumaturgeTechCompats.scribing;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class ScribingRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<ScribingTier, DeferredItem<Item>> TOOLS = new EnumMap<>(ScribingTier.class);

    public static final DeferredItem<Item> MATRIX_SCRIBING_TOOLS = register(ScribingTier.MATRIX);
    public static final DeferredItem<Item> NEUTRONIUM_SCRIBING_TOOLS = register(ScribingTier.NEUTRONIUM);
    public static final DeferredItem<Item> INFINITY_SCRIBING_TOOLS = register(ScribingTier.INFINITY);

    private ScribingRegistry() {
    }

    public static DeferredItem<Item> tools(ScribingTier tier) {
        return TOOLS.get(tier);
    }

    private static DeferredItem<Item> register(ScribingTier tier) {
        Item.Properties properties = new Item.Properties().stacksTo(1).durability(tier.durability()).rarity(tier.rarity());
        if (tier.fireResistant()) {
            properties.fireResistant();
        }
        DeferredItem<Item> item = tier == ScribingTier.INFINITY
                ? ITEMS.registerItem(tier.toolsName(), InfinityScribingToolsItem::new, () -> properties)
                : ITEMS.registerItem(tier.toolsName(), Item::new, () -> properties);
        TOOLS.put(tier, item);
        return item;
    }
}
