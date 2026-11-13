package nadiendev.ThaumaturgeTechCompats.ponder.scene;

import nadiendev.ThaumaturgeTechCompats.ponder.PonderBlocks;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScripts;
import nadiendev.ThaumaturgeTechCompats.ponder.StructureSketch;
import net.createmod.ponder.api.client.PonderPalette;
import net.createmod.ponder.api.client.scene.SceneBuilder;
import net.createmod.ponder.api.client.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class NodeScenes {
    public static final int SIZE = 5;
    public static final BlockPos STABILIZER = new BlockPos(2, 1, 2);
    public static final BlockPos NODE = new BlockPos(2, 2, 2);
    public static final BlockPos TRANSDUCER = new BlockPos(2, 3, 2);
    public static final BlockPos POWER = new BlockPos(3, 3, 2);

    private NodeScenes() {
    }

    public static StructureSketch towerSketch(String stabilizer, String transducer) {
        StructureSketch sketch = new StructureSketch(SIZE).floor(PonderBlocks.state(SceneKit.FLOOR))
                .set(STABILIZER.getX(), STABILIZER.getY(), STABILIZER.getZ(), PonderBlocks.state(stabilizer))
                .set(NODE.getX(), NODE.getY(), NODE.getZ(), PonderBlocks.state("thaumaturge:node"));
        if (transducer != null) {
            sketch.set(TRANSDUCER.getX(), TRANSDUCER.getY(), TRANSDUCER.getZ(), PonderBlocks.state(transducer));
            sketch.set(POWER.getX(), POWER.getY(), POWER.getZ(), PonderBlocks.state("minecraft:redstone_block"));
        }
        return sketch;
    }

    public static void showNode(SceneBuilder scene, SceneBuildingUtil util) {
        scene.world().showSection(util.select().position(NODE), Direction.DOWN);
        scene.overlay().showOutline(PonderPalette.BLUE, "node", util.select().position(NODE), 60);
        scene.idle(15);
    }

    public static void energizing(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.NODE_TOWER, SIZE);

        scene.world().showSection(util.select().position(STABILIZER), Direction.DOWN);
        scene.idle(10);
        showNode(scene, util);
        kit.say(0, util.vector().centerOf(STABILIZER));

        scene.world().setBlock(STABILIZER, PonderBlocks.state("thaumaturge:node_stabilizer_advanced"), true);
        scene.idle(10);
        kit.say(1, util.vector().centerOf(STABILIZER));

        scene.world().showSection(util.select().position(TRANSDUCER), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().position(POWER), Direction.DOWN);
        scene.idle(10);
        scene.effects().indicateRedstone(TRANSDUCER);
        kit.say(2, util.vector().topOf(TRANSDUCER));

        scene.overlay().showOutline(PonderPalette.RED, "stabilizer", util.select().position(STABILIZER), 80);
        kit.say(3, util.vector().centerOf(STABILIZER));
    }
}
