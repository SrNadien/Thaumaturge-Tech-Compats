package nadiendev.ThaumaturgeTechCompats.device;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public final class EntityItemModels {
    private EntityItemModels() {
    }

    public static void nodeDevice(ItemModelProvider provider, String name, ResourceLocation particle) {
        ItemModelBuilder model = provider.getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile(provider.mcLoc("builtin/entity")))
                .texture("particle", particle);
        model.transforms()
                .transform(ItemDisplayContext.GUI).rotation(30, 45, 0).translation(0, 0, 0).scale(0.7F, 0.7F, 0.7F).end()
                .transform(ItemDisplayContext.GROUND).translation(0, 3, 0).scale(0.5F, 0.5F, 0.5F).end()
                .transform(ItemDisplayContext.FIXED).rotation(0, 180, 0).scale(0.75F, 0.75F, 0.75F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75, 45, 0).translation(0, 2.5F, 0).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, 45, 0).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 225, 0).scale(0.4F, 0.4F, 0.4F).end()
                .end();
    }
}
