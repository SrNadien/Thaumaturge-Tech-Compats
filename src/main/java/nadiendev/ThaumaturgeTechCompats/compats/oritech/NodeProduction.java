package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import com.mojang.logging.LogUtils;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.List;

public final class NodeProduction {
    public static final String RECIPE_FOLDER = "oritech/deep_drill/";

    private static final Logger LOGGER = LogUtils.getLogger();

    private NodeProduction() {
    }

    public static void onServerStarted(ServerStartedEvent event) {
        apply(event.getServer().getRecipeManager());
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        apply(event.getPlayerList().getServer().getRecipeManager());
    }

    public static void apply(RecipeManager manager) {
        for (RecipeHolder<?> holder : manager.getRecipes()) {
            if (!holder.id().getNamespace().equals(ThaumaturgeTechCompats.MOD_ID) || !holder.id().getPath().startsWith(RECIPE_FOLDER)) {
                continue;
            }
            OritechNode node = OritechNode.byId(holder.id().getPath().substring(RECIPE_FOLDER.length()));
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
