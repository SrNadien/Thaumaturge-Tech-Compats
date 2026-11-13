package nadiendev.ThaumaturgeTechCompats.registry;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.item.AkashicRecordItem;
import nadiendev.ThaumaturgeTechCompats.item.ExtremelyPrimordialPearlItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<Item> AKASHIC_RECORD = ITEMS.register("akashic_record", AkashicRecordItem::new);
    public static final DeferredItem<Item> EXTREMELY_PRIMORDIAL_PEARL = ITEMS.register("extremely_primordial_pearl", ExtremelyPrimordialPearlItem::new);

    private ModItems() {
    }
}
