package nadiendev.ThaumaturgeTechCompats.wandtier;

import com.leclowndu93150.thaumaturge.content.wands.ItemWandCap;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.registry.ModWandParts;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class WandTierItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<ItemWandRod> MATRIX_WAND_ROD = ITEMS.registerItem("matrix_wand_rod",
            properties -> new ItemWandRod(properties, WandTierParts.MATRIX_ROD));
    public static final DeferredItem<ItemWandCap> MATRIX_WAND_CAP = ITEMS.registerItem("matrix_wand_cap",
            properties -> new ItemWandCap(properties, ModWandParts.MATRIX_CAP));
    public static final DeferredItem<ItemWandRod> NEUTRONIUM_WAND_ROD = ITEMS.registerItem("neutronium_wand_rod",
            properties -> new ItemWandRod(properties, WandTierParts.NEUTRONIUM_ROD));
    public static final DeferredItem<ItemWandCap> NEUTRONIUM_WAND_CAP = ITEMS.registerItem("neutronium_wand_cap",
            properties -> new ItemWandCap(properties, WandTierParts.NEUTRONIUM_CAP));

    public static final DeferredItem<ItemWandCap> INFINITY_WAND_CAP = ITEMS.registerItem("infinity_wand_cap",
            properties -> new ItemWandCap(properties, ModWandParts.INFINITY_CAP));

    private WandTierItems() {
    }
}
