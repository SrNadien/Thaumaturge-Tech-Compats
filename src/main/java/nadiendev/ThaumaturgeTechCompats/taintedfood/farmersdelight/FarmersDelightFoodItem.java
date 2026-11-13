package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class FarmersDelightFoodItem extends ConsumableItem {
    private final FarmersDelightFood food;

    public FarmersDelightFoodItem(FarmersDelightFood food, Properties properties) {
        super(properties);
        this.food = food;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component original = BuiltInRegistries.ITEM.getOptional(food.original())
                .map(Item::getDescription)
                .orElseGet(() -> Component.translatable(food.original().toLanguageKey("item")));
        return Component.translatable(food.gender().nameKey(), original);
    }
}
