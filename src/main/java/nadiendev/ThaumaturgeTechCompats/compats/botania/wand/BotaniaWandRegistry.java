package nadiendev.ThaumaturgeTechCompats.compats.botania.wand;

import com.leclowndu93150.thaumaturge.api.wands.WandCap;
import com.leclowndu93150.thaumaturge.api.wands.WandRod;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandCap;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
import com.leclowndu93150.thaumaturge.content.wands.WandVisHelper;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class BotaniaWandRegistry {
    public static final String ENTRY = "botania_wand";
    public static final Identifier RESEARCH = ThaumaturgeTechCompats.id(ENTRY);

    private static final DeferredRegister<WandRod> RODS = DeferredRegister.create(WandRod.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister<WandCap> CAPS = DeferredRegister.create(WandCap.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<BotaniaWandRod, DeferredHolder<WandRod, WandRod>> ROD_PARTS = new EnumMap<>(BotaniaWandRod.class);
    private static final Map<BotaniaWandCap, DeferredHolder<WandCap, WandCap>> CAP_PARTS = new EnumMap<>(BotaniaWandCap.class);
    private static final Map<BotaniaWandRod, DeferredItem<ItemWandRod>> ROD_ITEMS = new EnumMap<>(BotaniaWandRod.class);
    private static final Map<BotaniaWandCap, DeferredItem<ItemWandCap>> CAP_ITEMS = new EnumMap<>(BotaniaWandCap.class);

    static {
        for (BotaniaWandRod rod : BotaniaWandRod.values()) {
            DeferredHolder<WandRod, WandRod> part = RODS.register(rod.id(), () -> new WandRod(
                    BotaniaWandConfig.rodCapacity(rod),
                    BotaniaWandConfig.rodCraftCost(rod),
                    ThaumaturgeTechCompats.id("textures/models/wand_rod_" + rod.id() + ".png"),
                    null,
                    rod.glow(),
                    false,
                    false,
                    RESEARCH));
            ROD_PARTS.put(rod, part);
            ROD_ITEMS.put(rod, ITEMS.registerItem(rod.itemName(), properties -> new ItemWandRod(properties, part),
                    () -> new Item.Properties().rarity(rod.rarity())));
        }
        for (BotaniaWandCap cap : BotaniaWandCap.values()) {
            DeferredHolder<WandCap, WandCap> part = CAPS.register(cap.id(), () -> new WandCap(
                    BotaniaWandConfig.capBaseCost(cap),
                    cap.specialAspects(),
                    BotaniaWandConfig.capSpecialCost(cap),
                    BotaniaWandConfig.capCraftCost(cap),
                    ThaumaturgeTechCompats.id("textures/models/wand_cap_" + cap.id() + ".png")));
            CAP_PARTS.put(cap, part);
            CAP_ITEMS.put(cap, ITEMS.registerItem(cap.itemName(), properties -> new ItemWandCap(properties, part),
                    () -> new Item.Properties().rarity(cap.rarity())));
        }
    }

    private BotaniaWandRegistry() {
    }

    public static void register(IEventBus modBus) {
        RODS.register(modBus);
        CAPS.register(modBus);
        ITEMS.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (BotaniaWandRod rod : BotaniaWandRod.values()) {
            output.accept(ROD_ITEMS.get(rod).get());
        }
        for (BotaniaWandCap cap : BotaniaWandCap.values()) {
            output.accept(CAP_ITEMS.get(cap).get());
        }
        ItemStack wand = ItemWand.create(TTItems.WAND.get(), CAP_PARTS.get(BotaniaWandCap.TERRASTEEL).get(), ROD_PARTS.get(BotaniaWandRod.DREAMWOOD).get(), false);
        WandVisHelper.fill(wand);
        output.accept(wand);
    }
}
