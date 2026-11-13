package nadiendev.ThaumaturgeTechCompats.compats.enderio.wand;

import com.leclowndu93150.thaumaturge.api.wands.WandCap;
import com.leclowndu93150.thaumaturge.api.wands.WandRod;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandCap;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
import com.leclowndu93150.thaumaturge.content.wands.WandVisHelper;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class EnderIOWandRegistry {
    public static final String ENTRY = "enderio_wand_parts";
    public static final ResourceLocation RESEARCH = ThaumaturgeTechCompats.id(ENTRY);

    private static final DeferredRegister<WandRod> RODS = DeferredRegister.create(WandRod.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister<WandCap> CAPS = DeferredRegister.create(WandCap.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<EnderIOWandMaterial, DeferredHolder<WandRod, WandRod>> ROD_PARTS = new EnumMap<>(EnderIOWandMaterial.class);
    private static final Map<EnderIOWandMaterial, DeferredHolder<WandCap, WandCap>> CAP_PARTS = new EnumMap<>(EnderIOWandMaterial.class);
    private static final Map<EnderIOWandMaterial, DeferredItem<ItemWandRod>> ROD_ITEMS = new EnumMap<>(EnderIOWandMaterial.class);
    private static final Map<EnderIOWandMaterial, DeferredItem<ItemWandCap>> CAP_ITEMS = new EnumMap<>(EnderIOWandMaterial.class);

    static {
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            DeferredHolder<WandRod, WandRod> rod = RODS.register(material.id(), () -> new WandRod(
                    EnderIOWandConfig.rodCapacity(material),
                    EnderIOWandConfig.rodCraftCost(material),
                    ThaumaturgeTechCompats.id("textures/models/wand_rod_" + material.id() + ".png"),
                    null,
                    material.glow(),
                    false,
                    false,
                    RESEARCH));
            ROD_PARTS.put(material, rod);
            ROD_ITEMS.put(material, ITEMS.registerItem(material.rodName(), properties -> new ItemWandRod(properties, rod),
                    new Item.Properties().rarity(material.rarity())));

            DeferredHolder<WandCap, WandCap> cap = CAPS.register(material.id(), () -> new WandCap(
                    EnderIOWandConfig.capBaseCost(material),
                    material.specialAspects(),
                    EnderIOWandConfig.capSpecialCost(material),
                    EnderIOWandConfig.capCraftCost(material),
                    ThaumaturgeTechCompats.id("textures/models/wand_cap_" + material.id() + ".png")));
            CAP_PARTS.put(material, cap);
            CAP_ITEMS.put(material, ITEMS.registerItem(material.capName(), properties -> new ItemWandCap(properties, cap),
                    new Item.Properties().rarity(material.rarity())));
        }
    }

    private EnderIOWandRegistry() {
    }

    public static void register(IEventBus modBus) {
        RODS.register(modBus);
        CAPS.register(modBus);
        ITEMS.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            output.accept(ROD_ITEMS.get(material).get());
            output.accept(CAP_ITEMS.get(material).get());
        }
        EnderIOWandMaterial top = EnderIOWandMaterial.END_STEEL;
        ItemStack wand = ItemWand.create(TTItems.WAND.get(), CAP_PARTS.get(top).get(), ROD_PARTS.get(top).get(), false);
        WandVisHelper.fill(wand);
        output.accept(wand);
    }
}
