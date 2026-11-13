package nadiendev.ThaumaturgeTechCompats.compats.enderio;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class EnderIOItemModels extends ModelAssets {
    public EnderIOItemModels(PackOutput output) {
        super(output);
    }

    @Override
    protected void register() {
        for (GrindingBall ball : EnderIORegistry.balls().keySet()) {
            basicItem(ball.itemName());
        }
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO item models";
    }
}
