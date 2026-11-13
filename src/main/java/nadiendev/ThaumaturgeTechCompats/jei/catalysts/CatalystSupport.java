package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import com.mojang.logging.LogUtils;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

import java.util.Optional;

final class CatalystSupport {
    private static final Logger LOGGER = LogUtils.getLogger();

    private CatalystSupport() {
    }

    static boolean loaded(String modId) {
        return ModList.get() != null && ModList.get().isLoaded(modId);
    }

    static void add(IRecipeCatalystRegistration registration, String itemName, IRecipeType<?> type) {
        BuiltInRegistries.ITEM.getOptional(ThaumaturgeTechCompats.id(itemName))
                .ifPresent(item -> registration.addRecipeCatalyst(item, type));
    }

    static Optional<IRecipeType<?>> staticType(String className, String fieldName) {
        try {
            Object value = Class.forName(className).getField(fieldName).get(null);
            if (value instanceof IRecipeType<?> type) {
                return Optional.of(type);
            }
        } catch (ReflectiveOperationException | LinkageError exception) {
            LOGGER.warn("The JEI category {}.{} was not found, its catalysts were skipped", className, fieldName, exception);
        }
        return Optional.empty();
    }
}
