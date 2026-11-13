package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class FtbUnearthedItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<FtbUnearthedBrushTier, DeferredItem<Item>> BRUSHES = new EnumMap<>(FtbUnearthedBrushTier.class);

    private FtbUnearthedItems() {
    }

    public static void register(IEventBus modBus) {
        for (FtbUnearthedBrushTier tier : FtbUnearthedBrushTier.values()) {
            if (!FtbUnearthedIntegration.tierEnabled(tier)) {
                continue;
            }
            BRUSHES.put(tier, ITEMS.register(tier.itemId(), () -> new BrushItem(new Item.Properties()
                    .durability(tier.defaultDurability())
                    .rarity(tier.rarity()))));
        }
        ITEMS.register(modBus);
        modBus.addListener(FtbUnearthedItems::onModifyDefaults);
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<Item> holder : BRUSHES.values()) {
            items.add(holder.get());
        }
        return items;
    }

    private static void onModifyDefaults(ModifyDefaultComponentsEvent event) {
        for (Map.Entry<FtbUnearthedBrushTier, DeferredItem<Item>> entry : BRUSHES.entrySet()) {
            int durability = FtbUnearthedConfig.durability(entry.getKey());
            event.modify(entry.getValue().get(), builder -> builder.set(DataComponents.MAX_DAMAGE, durability));
        }
    }
}
