package nadiendev.ThaumaturgeTechCompats.ponder.scene;

import nadiendev.ThaumaturgeTechCompats.ponder.PonderBlocks;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScripts;
import nadiendev.ThaumaturgeTechCompats.ponder.StructureSketch;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class WorkbenchScenes {
    public static final int CHARGER_SIZE = 3;
    public static final BlockPos WORKBENCH = new BlockPos(1, 1, 1);
    public static final BlockPos CHARGER = new BlockPos(1, 2, 1);

    private static final int TABLE_SIZE = 5;
    private static final BlockPos TABLE_MAIN = new BlockPos(2, 1, 2);
    private static final BlockPos TABLE_EXT = new BlockPos(3, 1, 2);
    private static final BlockPos[] AIDS = {new BlockPos(1, 1, 2), new BlockPos(1, 2, 2), new BlockPos(2, 1, 1), new BlockPos(3, 1, 1)};
    private static final BlockPos BRAIN = new BlockPos(2, 1, 3);

    private WorkbenchScenes() {
    }

    public static StructureSketch chargerSketch(String charger) {
        return new StructureSketch(CHARGER_SIZE).floor(PonderBlocks.state(SceneKit.FLOOR))
                .set(WORKBENCH.getX(), WORKBENCH.getY(), WORKBENCH.getZ(), PonderBlocks.state("thaumaturge:arcane_workbench"))
                .set(CHARGER.getX(), CHARGER.getY(), CHARGER.getZ(), PonderBlocks.state(charger));
    }

    public static void charger(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.WORKBENCH_CHARGER, CHARGER_SIZE);

        scene.world().showSection(util.select().position(WORKBENCH), Direction.DOWN);
        scene.idle(15);
        scene.world().showSection(util.select().position(CHARGER), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(CHARGER));
        kit.say(1, util.vector().centerOf(WORKBENCH));
    }

    public static StructureSketch tableSketch() {
        StructureSketch sketch = new StructureSketch(TABLE_SIZE).floor(PonderBlocks.state(SceneKit.FLOOR))
                .set(TABLE_MAIN.getX(), TABLE_MAIN.getY(), TABLE_MAIN.getZ(), PonderBlocks.state("thaumaturge:research_table", "facing", "north", "part", "main"))
                .set(TABLE_EXT.getX(), TABLE_EXT.getY(), TABLE_EXT.getZ(), PonderBlocks.state("thaumaturge:research_table", "facing", "north", "part", "ext"))
                .set(BRAIN.getX(), BRAIN.getY(), BRAIN.getZ(), PonderBlocks.state("thaumaturge:jar_brain"));
        for (BlockPos aid : AIDS) {
            sketch.set(aid.getX(), aid.getY(), aid.getZ(), PonderBlocks.state("minecraft:bookshelf"));
        }
        return sketch;
    }

    public static void researchTable(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.RESEARCH_TABLE, TABLE_SIZE);

        scene.world().showSection(util.select().position(TABLE_MAIN), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().position(TABLE_EXT), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(TABLE_MAIN));

        for (BlockPos aid : AIDS) {
            scene.world().showSection(util.select().position(aid), Direction.DOWN);
            scene.idle(5);
        }
        scene.world().showSection(util.select().position(BRAIN), Direction.DOWN);
        scene.idle(15);
        kit.say(1, util.vector().topOf(BRAIN));

        kit.click(TABLE_MAIN, "thaumaturge:scribing_tools");
        kit.say(2, util.vector().topOf(TABLE_EXT));
    }
}
