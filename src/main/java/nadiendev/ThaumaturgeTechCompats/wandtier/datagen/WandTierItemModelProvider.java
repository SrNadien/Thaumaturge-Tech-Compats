package nadiendev.ThaumaturgeTechCompats.wandtier.datagen;


import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class WandTierItemModelProvider extends ModelAssets {
    public WandTierItemModelProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void register() {
        part("matrix_wand_rod", "minecraft:item/handheld");
        part("matrix_wand_cap", "minecraft:item/generated");
    }

    private void part(String name, String parent) {
        item(name, parent, textures("layer0", mod("item/" + name)));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats wand tier item models";
    }
}
