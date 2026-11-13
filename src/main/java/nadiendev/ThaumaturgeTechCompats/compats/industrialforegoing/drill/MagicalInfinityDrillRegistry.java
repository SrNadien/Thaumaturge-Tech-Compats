package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import com.mojang.serialization.Codec;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MagicalInfinityDrillRegistry {
    public static final String NAME = "magical_infinity_drill";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<MagicalInfinityDrillItem> ITEM = ITEMS.register(NAME,
            () -> new MagicalInfinityDrillItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SELECTED_LEVEL = COMPONENTS.registerComponentType("selected_level",
            builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CHARGING = COMPONENTS.registerComponentType("charging",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> LIQUID = COMPONENTS.registerComponentType("liquid",
            builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));

    private MagicalInfinityDrillRegistry() {
    }
}
