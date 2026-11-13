package nadiendev.ThaumaturgeTechCompats.ponder.scene;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.braincharger.BoostTier;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderBlocks;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScripts;
import nadiendev.ThaumaturgeTechCompats.ponder.StructureSketch;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterTier;
import net.createmod.ponder.api.client.scene.SceneBuilder;
import net.createmod.ponder.api.client.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

import java.util.function.IntFunction;

public final class TieredScenes {
    private TieredScenes() {
    }

    public static String stabilizer(int tier) {
        return addon(DeviceTier.values()[tier].id() + "_node_stabilizer");
    }

    public static String transducer(int tier) {
        return addon(DeviceTier.values()[tier].id() + "_node_transducer");
    }

    public static String smelter(int tier) {
        return addon(SmelterTier.values()[tier].blockName());
    }

    public static String furnace(int tier) {
        return addon(FurnaceIntegration.blockName(DeviceTier.values()[tier]));
    }

    public static String matrix(int tier) {
        return addon(BoostTier.values()[tier].boxName());
    }

    public static String charger(int tier) {
        return addon(BoostTier.values()[tier].chargerName());
    }

    private static String addon(String name) {
        return ThaumaturgeTechCompats.id(name).toString();
    }

    private static void tiers(SceneKit kit, SceneBuilder scene, Runnable[] swaps, Vec3 target) {
        for (int tier = 0; tier < swaps.length; tier++) {
            if (swaps[tier] != null) {
                swaps[tier].run();
                scene.idle(10);
            }
            kit.say(tier + 1, target);
        }
    }

    private static Runnable[] swaps(SceneBuilder scene, BlockPos pos, IntFunction<String> block) {
        Runnable[] swaps = new Runnable[DeviceTier.values().length];
        for (int tier = 1; tier < swaps.length; tier++) {
            String id = block.apply(tier);
            swaps[tier] = () -> scene.world().setBlock(pos, PonderBlocks.state(id), true);
        }
        return swaps;
    }

    public static StructureSketch stabilizerSketch() {
        return NodeScenes.towerSketch(stabilizer(0), null);
    }

    public static void stabilizers(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.TIERED_STABILIZERS, NodeScenes.SIZE);

        scene.world().showSection(util.select().position(NodeScenes.STABILIZER), Direction.DOWN);
        scene.idle(10);
        NodeScenes.showNode(scene, util);
        kit.say(0, util.vector().centerOf(NodeScenes.STABILIZER));

        tiers(kit, scene, swaps(scene, NodeScenes.STABILIZER, TieredScenes::stabilizer), util.vector().centerOf(NodeScenes.STABILIZER));
    }

    public static StructureSketch transducerSketch() {
        return NodeScenes.towerSketch("thaumaturge:node_stabilizer_advanced", transducer(0));
    }

    public static void transducers(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.TIERED_TRANSDUCERS, NodeScenes.SIZE);

        scene.world().showSection(util.select().position(NodeScenes.STABILIZER), Direction.DOWN);
        scene.idle(10);
        NodeScenes.showNode(scene, util);
        scene.world().showSection(util.select().position(NodeScenes.TRANSDUCER), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().position(NodeScenes.POWER), Direction.DOWN);
        scene.idle(10);
        scene.effects().indicateRedstone(NodeScenes.TRANSDUCER);
        kit.say(0, util.vector().topOf(NodeScenes.TRANSDUCER));

        tiers(kit, scene, swaps(scene, NodeScenes.TRANSDUCER, TieredScenes::transducer), util.vector().topOf(NodeScenes.TRANSDUCER));
    }

    public static StructureSketch furnaceSketch() {
        return AlchemyScenes.furnaceSketch(smelter(0));
    }

    public static void furnaces(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.TIERED_FURNACES, AlchemyScenes.FURNACE_SIZE);

        scene.world().showSection(util.select().fromTo(1, 1, 1, 3, 1, 3), Direction.DOWN);
        scene.idle(15);
        scene.world().showSection(util.select().fromTo(1, 2, 1, 3, 2, 3), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(AlchemyScenes.FURNACE_CORE));

        kit.dust(new BlockPos(1, 2, 2));
        kit.assemble(AlchemyScenes.FURNACE_ORIGIN, AlchemyScenes.FURNACE_LAYERS, AlchemyScenes.furnaceTargets(furnace(0)));
        scene.effects().indicateSuccess(AlchemyScenes.FURNACE_CORE);
        kit.say(1, util.vector().centerOf(AlchemyScenes.FURNACE_CORE));

        Runnable[] swaps = swaps(scene, AlchemyScenes.FURNACE_CORE, TieredScenes::furnace);
        for (int tier = 1; tier < swaps.length; tier++) {
            swaps[tier].run();
            scene.idle(10);
            kit.say(tier + 1, util.vector().centerOf(AlchemyScenes.FURNACE_CORE));
        }
    }

    public static StructureSketch matrixSketch() {
        return AlchemyScenes.thaumatoriumSketch(true, matrix(0));
    }

    public static void matrices(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.TIERED_MATRICES, 5);

        scene.world().showSection(util.select().fromTo(2, 1, 2, 2, 3, 2), Direction.DOWN);
        scene.idle(15);
        scene.world().showSection(util.select().fromTo(1, 2, 2, 1, 3, 2).add(util.select().fromTo(3, 2, 2, 3, 3, 2)), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(new BlockPos(1, 3, 2)));

        for (int tier = 0; tier < DeviceTier.values().length; tier++) {
            if (tier > 0) {
                for (int[] spot : AlchemyScenes.matrixSpots()) {
                    scene.world().setBlock(new BlockPos(spot[0], spot[1], 2), AlchemyScenes.matrixState(matrix(tier), spot[0]), true);
                }
                scene.idle(10);
            }
            kit.say(tier + 1, util.vector().topOf(new BlockPos(1, 3, 2)));
        }
    }

    public static StructureSketch chargerSketch() {
        return WorkbenchScenes.chargerSketch(charger(0));
    }

    public static void chargers(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.TIERED_CHARGERS, WorkbenchScenes.CHARGER_SIZE);

        scene.world().showSection(util.select().position(WorkbenchScenes.WORKBENCH), Direction.DOWN);
        scene.idle(15);
        scene.world().showSection(util.select().position(WorkbenchScenes.CHARGER), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(WorkbenchScenes.CHARGER));

        tiers(kit, scene, swaps(scene, WorkbenchScenes.CHARGER, TieredScenes::charger), util.vector().topOf(WorkbenchScenes.CHARGER));
    }
}
