package nadiendev.ThaumaturgeTechCompats.smelter;

import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockSmelter;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SmelterBlockStates extends BlockStateProvider {
    public SmelterBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Smelter Block States: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerStatesAndModels() {
        for (SmelterTier tier : SmelterTier.values()) {
            String name = tier.blockName();
            Block block = SmelterIntegration.block(tier).get();

            BlockModelBuilder off = models().withExistingParent(name + "_off", mcLoc("block/cube"))
                    .texture("down", mcLoc("block/furnace_top"))
                    .texture("east", modLoc("block/" + name + "_side"))
                    .texture("north", modLoc("block/" + name + "_front_off"))
                    .texture("particle", modLoc("block/" + name + "_side"))
                    .texture("south", modLoc("block/" + name + "_side"))
                    .texture("up", modLoc("block/" + name + "_top"))
                    .texture("west", modLoc("block/" + name + "_side"));
            BlockModelBuilder on = models().withExistingParent(name + "_on", modLoc("block/" + name + "_off"))
                    .texture("north", modLoc("block/" + name + "_front_on"));

            getVariantBuilder(block).forAllStates(state -> {
                Direction facing = state.getValue(BlockSmelter.FACING);
                boolean lit = state.getValue(BlockSmelter.LIT);
                return ConfiguredModel.builder()
                        .modelFile(lit ? on : off)
                        .rotationY(((int) facing.toYRot() + 180) % 360)
                        .build();
            });
        }
    }
}
