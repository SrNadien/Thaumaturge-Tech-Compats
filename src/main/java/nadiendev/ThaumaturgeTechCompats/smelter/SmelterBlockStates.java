package nadiendev.ThaumaturgeTechCompats.smelter;

import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockSmelter;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class SmelterBlockStates extends ModelAssets {
    public SmelterBlockStates(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Smelter Block States: " + MOD;
    }

    @Override
    protected void register() {
        for (SmelterTier tier : SmelterTier.values()) {
            String name = tier.blockName();
            blockModel(name + "_off", model("minecraft:block/cube", textures(
                    "down", "minecraft:block/furnace_top",
                    "east", mod("block/" + name + "_side"),
                    "north", mod("block/" + name + "_front_off"),
                    "particle", mod("block/" + name + "_side"),
                    "south", mod("block/" + name + "_side"),
                    "up", mod("block/" + name + "_top"),
                    "west", mod("block/" + name + "_side"))));
            blockModel(name + "_on", model(mod("block/" + name + "_off"), textures("north", mod("block/" + name + "_front_on"))));
            blockState(SmelterIntegration.block(tier).get(), state -> variant(
                    mod("block/" + name + (state.getValue(BlockSmelter.LIT) ? "_on" : "_off")),
                    ((int) state.getValue(BlockSmelter.FACING).toYRot() + 180) % 360));
        }
    }
}
