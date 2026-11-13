package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import com.mojang.logging.LogUtils;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public final class NodeProduction {
    public static final String RECIPE_FOLDER = "oritech/deep_drill/";

    private static final Logger LOGGER = LogUtils.getLogger();

    private NodeProduction() {
    }

    public static void onServerStarted(ServerStartedEvent event) {
        apply(event.getServer().getRecipeManager().getRecipes());
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        apply(event.getPlayerList().getServer().getRecipeManager().getRecipes());
    }

    public static void apply(Collection<RecipeHolder<?>> recipes) {
        for (RecipeHolder<?> holder : recipes) {
            if (!holder.id().identifier().getNamespace().equals(ThaumaturgeTechCompats.MOD_ID) || !holder.id().identifier().getPath().startsWith(RECIPE_FOLDER)) {
                continue;
            }
            OritechNode node = OritechNode.byId(holder.id().identifier().getPath().substring(RECIPE_FOLDER.length()));
            if (node == null) {
                continue;
            }
            setCount(holder.value(), OritechConfig.nodeProduction(node));
        }
    }

    @SuppressWarnings("unchecked")
    private static void setCount(Recipe<?> recipe, int count) {
        try {
            Method results = recipe.getClass().getMethod("getResults");
            for (ItemStack stack : (List<ItemStack>) results.invoke(recipe)) {
                stack.setCount(count);
            }
        } catch (ReflectiveOperationException exception) {
            LOGGER.warn("Could not set the production of the Oritech resource node recipe {}", recipe, exception);
        }
    }
}
