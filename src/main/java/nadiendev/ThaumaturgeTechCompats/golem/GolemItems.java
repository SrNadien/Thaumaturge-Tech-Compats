package nadiendev.ThaumaturgeTechCompats.golem;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class GolemItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<Item> MATRIX_PLATE = ITEMS.registerSimpleItem(Tier.MATRIX.plateName());
    public static final DeferredItem<Item> NEUTRONIUM_PLATE = ITEMS.registerSimpleItem(Tier.NEUTRONIUM.plateName());
    public static final DeferredItem<Item> INFINITY_PLATE = ITEMS.registerSimpleItem(Tier.INFINITY.plateName());

    private GolemItems() {
    }
}
