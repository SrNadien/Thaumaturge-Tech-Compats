package nadiendev.ThaumaturgeTechCompats.device;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class MeshLoaderBuilder extends CustomLoaderBuilder<BlockModelBuilder> {
    private static final ResourceLocation LOADER = ResourceLocation.fromNamespaceAndPath("thaumaturge", "mesh");
    private static final String MESH = "thaumaturge:models/mesh/node_stabilizer.ttmesh";

    public MeshLoaderBuilder(BlockModelBuilder parent, ExistingFileHelper helper) {
        super(LOADER, parent, helper, false);
    }

    @Override
    public JsonObject toJson(JsonObject json) {
        JsonObject result = super.toJson(json);
        result.addProperty("model", MESH);
        return result;
    }
}
