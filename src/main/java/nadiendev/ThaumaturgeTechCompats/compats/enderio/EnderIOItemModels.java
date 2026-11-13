package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EnderIOItemModels extends ItemModelProvider {
    public EnderIOItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        for (GrindingBall ball : EnderIORegistry.balls().keySet()) {
            basicItem(ThaumaturgeTechCompats.id(ball.itemName()));
        }
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO item models";
    }
}
