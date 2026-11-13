package nadiendev.ThaumaturgeTechCompats.infusion.client;

import com.leclowndu93150.thaumaturge.content.infusion.InfusionRecipe;
import com.leclowndu93150.thaumaturge.registry.TTRecipeTypes;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionTranslations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
public final class InfusionTierTooltips {
    private static volatile Set<Item> results = Set.of();

    private InfusionTierTooltips() {
    }

    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        Set<Item> items = new HashSet<>();
        for (RecipeHolder<InfusionRecipe> holder : event.getRecipeManager().getAllRecipesFor(TTRecipeTypes.INFUSION.get())) {
            items.add(holder.value().resultItem().getItem());
        }
        results = items;
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();
        if (!results.contains(item)) {
            return;
        }
        Component line = InfusionTranslations.tierRequirement(item);
        if (line != null) {
            event.getToolTip().add(line.copy().withStyle(ChatFormatting.GRAY));
        }
    }
}
