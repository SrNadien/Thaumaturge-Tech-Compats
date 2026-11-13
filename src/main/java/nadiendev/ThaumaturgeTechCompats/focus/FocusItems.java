package nadiendev.ThaumaturgeTechCompats.focus;

import com.leclowndu93150.thaumaturge.content.spell.item.FocusItem;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FocusItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<FocusItem> MATRIX_FOCUS = ITEMS.registerItem("matrix_focus",
            FocusItem::new, () -> new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    public static final DeferredItem<FocusItem> INFINITY_FOCUS = ITEMS.registerItem("infinity_focus",
            FocusItem::new, () -> new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    public static final DeferredItem<FocusItem> BLAZE_CUBE_FOCUS = ITEMS.registerItem("blaze_cube_focus",
            FocusItem::new, () -> new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

    private FocusItems() {
    }
}
