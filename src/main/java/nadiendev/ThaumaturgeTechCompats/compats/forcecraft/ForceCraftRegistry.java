package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ForceCraftRegistry {
    public static final String REPAIR_SERIALIZER_ID = "forcecraft_repair_infusion";

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RepairInfuseSerializer> REPAIR_SERIALIZER = SERIALIZERS.register(REPAIR_SERIALIZER_ID, RepairInfuseSerializer::new);

    private ForceCraftRegistry() {
    }
}
