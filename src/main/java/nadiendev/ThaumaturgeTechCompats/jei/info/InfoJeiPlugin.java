package nadiendev.ThaumaturgeTechCompats.jei.info;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.Set;

@JeiPlugin
public class InfoJeiPlugin implements IModPlugin {
    private static final Identifier ID = ThaumaturgeTechCompats.id("info");

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (!CompatToggles.enabled(JeiInfoTexts.TOGGLE)) {
            return;
        }
        Set<Item> items = new LinkedHashSet<>();
        for (ItemStack stack : registration.getIngredientManager().getAllItemStacks()) {
            if (!stack.isEmpty() && ThaumaturgeTechCompats.MOD_ID.equals(BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace())) {
                items.add(stack.getItem());
            }
        }
        for (Item item : items) {
            String path = BuiltInRegistries.ITEM.getKey(item).getPath();
            registration.addIngredientInfo(item, JeiInfoTexts.components(path, item instanceof BlockItem));
        }
    }
}
