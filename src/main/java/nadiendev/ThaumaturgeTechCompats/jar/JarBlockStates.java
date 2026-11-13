package nadiendev.ThaumaturgeTechCompats.jar;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class JarBlockStates extends BlockStateProvider {
    private static final ResourceLocation JAR_PARENT = ResourceLocation.fromNamespaceAndPath("thaumaturge", "block/jar_normal");
    private static final ResourceLocation ITEM_PARENT = ResourceLocation.fromNamespaceAndPath("thaumaturge", "item/special_block_item_base");

    public JarBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (JarTier tier : JarTier.values()) {
            String name = tier.jarName();
            ResourceLocation side = modLoc("block/" + name + "_side");
            BlockModelBuilder model = models().getBuilder("block/" + name)
                    .parent(new ModelFile.UncheckedModelFile(JAR_PARENT))
                    .renderType("minecraft:translucent")
                    .texture("side", side)
                    .texture("top", modLoc("block/" + name + "_top"))
                    .texture("bottom", modLoc("block/" + name + "_bottom"))
                    .texture("particle", side);
            simpleBlock(JarRegistry.block(tier).get(), model);
            itemModels().getBuilder(name).parent(new ModelFile.UncheckedModelFile(ITEM_PARENT));
        }
    }

    @Override
    public String getName() {
        return "Block States: " + ThaumaturgeTechCompats.MOD_ID + " jars";
    }
}
