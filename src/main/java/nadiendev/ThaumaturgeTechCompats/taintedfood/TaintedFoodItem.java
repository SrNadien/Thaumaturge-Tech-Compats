package nadiendev.ThaumaturgeTechCompats.taintedfood;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TaintedFoodItem extends Item {
    private final TaintedFoodType type;

    public TaintedFoodItem(TaintedFoodType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component original = BuiltInRegistries.ITEM.getOptional(type.original())
                .map(Item::getDescription)
                .orElseGet(() -> Component.translatable(type.original().toLanguageKey("item")));
        return Component.translatable(type.gender().nameKey(), original);
    }
}
