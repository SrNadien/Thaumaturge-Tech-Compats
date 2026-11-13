package nadiendev.ThaumaturgeTechCompats.flux.gear;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class GearRegistry {
    public static final String SIPHON_NAME = "portable_void_siphon";
    public static final String CANNON_NAME = "discharge_cannon";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<PortableVoidSiphonItem> SIPHON = ITEMS.register(SIPHON_NAME,
            () -> new PortableVoidSiphonItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final DeferredItem<DischargeCannonItem> CANNON = ITEMS.register(CANNON_NAME,
            () -> new DischargeCannonItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    private GearRegistry() {
    }
}
