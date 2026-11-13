package nadiendev.ThaumaturgeTechCompats.purity;


import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class PurityItemModels extends ModelAssets {
    public PurityItemModels(PackOutput output) {
        super(output);
    }

    @Override
    protected void register() {
        basicItem(PurityRegistry.POTION_NAME);
        placeholder(PurityRegistry.BIOME_POTION_NAME);
        placeholder(PurityRegistry.CLARITY_POTION_NAME);
        placeholder(PurityRegistry.ABSOLUTION_POTION_NAME);
    }

    private void placeholder(String name) {
        item(name, "minecraft:item/generated", textures("layer0", mod("item/" + PurityRegistry.POTION_NAME)));
    }

    @Override
    public String getName() {
        return "Item Models: thaumaturgetechcompats purity";
    }
}
