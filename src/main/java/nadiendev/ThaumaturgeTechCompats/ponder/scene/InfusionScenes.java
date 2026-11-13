package nadiendev.ThaumaturgeTechCompats.ponder.scene;

import nadiendev.ThaumaturgeTechCompats.ponder.PonderBlocks;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScript;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScripts;
import nadiendev.ThaumaturgeTechCompats.ponder.StructureSketch;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public final class InfusionScenes {
    public record Material(String stone, String pedestal, String pillar) {
    }

    public static final Material ARCANE = new Material("thaumaturge:stone_arcane", "thaumaturge:pedestal_arcane", "thaumaturge:pillar_arcane");
    public static final Material ANCIENT = new Material("thaumaturge:stone_ancient_tile", "thaumaturge:pedestal_ancient", "thaumaturge:pillar_ancient");
    public static final Material ELDRITCH = new Material("thaumaturge:stone_eldritch_tile", "thaumaturge:pedestal_eldritch", "thaumaturge:pillar_eldritch");

    private static final int SIZE = 7;
    private static final BlockPos ORIGIN = new BlockPos(2, 1, 2);
    private static final BlockPos PEDESTAL = new BlockPos(3, 1, 3);
    private static final BlockPos MATRIX = new BlockPos(3, 3, 3);
    private static final char[] STONE_KEYS = {'A', 'E', 'N', 'S', 'W'};
    private static final String[][] LAYERS = {
            {"   ", " I ", "   "},
            {"A A", "   ", "A A"},
            {"E N", " P ", "S W"}
    };
    private static final int[][] CORNERS = {{2, 2}, {4, 2}, {2, 4}, {4, 4}};
    private static final String[] FACINGS = {"east", "north", "south", "west"};
    private static final int[][] RING = {{0, 3}, {6, 3}, {3, 0}, {3, 6}};

    private InfusionScenes() {
    }

    public static StructureSketch sketch(Material material) {
        BlockState stone = PonderBlocks.state(material.stone());
        Map<Character, BlockState> keys = new HashMap<>();
        for (char key : STONE_KEYS) {
            keys.put(key, stone);
        }
        keys.put('I', PonderBlocks.state("thaumaturge:infusion_matrix"));
        keys.put('P', PonderBlocks.state(material.pedestal()));
        StructureSketch sketch = new StructureSketch(SIZE).floor(PonderBlocks.state(SceneKit.FLOOR));
        sketch.pattern(ORIGIN.getX(), ORIGIN.getY(), ORIGIN.getZ(), keys, LAYERS);
        for (int[] ring : RING) {
            sketch.set(ring[0], 1, ring[1], PonderBlocks.state(material.pedestal()));
        }
        return sketch;
    }

    public static void arcane(SceneBuilder scene, SceneBuildingUtil util) {
        program(scene, util, PonderScripts.INFUSION_ARCANE, ARCANE);
    }

    public static void ancient(SceneBuilder scene, SceneBuildingUtil util) {
        program(scene, util, PonderScripts.INFUSION_ANCIENT, ANCIENT);
    }

    public static void eldritch(SceneBuilder scene, SceneBuildingUtil util) {
        program(scene, util, PonderScripts.INFUSION_ELDRITCH, ELDRITCH);
    }

    private static void program(SceneBuilder scene, SceneBuildingUtil util, PonderScript script, Material material) {
        SceneKit kit = SceneKit.start(scene, util, script, SIZE);

        Selection pillars = column(util, 0);
        for (int i = 1; i < CORNERS.length; i++) {
            pillars = pillars.add(column(util, i));
        }

        scene.world().showSection(util.select().position(PEDESTAL), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(pillars, Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().position(MATRIX), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(MATRIX));

        kit.dust(new BlockPos(CORNERS[0][0], 2, CORNERS[0][1]));
        for (int i = 0; i < CORNERS.length; i++) {
            scene.world().setBlock(new BlockPos(CORNERS[i][0], 1, CORNERS[i][1]), PonderBlocks.state(material.pillar(), "facing", FACINGS[i]), false);
            scene.world().setBlock(new BlockPos(CORNERS[i][0], 2, CORNERS[i][1]), Blocks.AIR.defaultBlockState(), false);
        }
        scene.effects().indicateSuccess(MATRIX);
        scene.idle(10);
        kit.say(1, util.vector().topOf(new BlockPos(CORNERS[0][0], 1, CORNERS[0][1])));

        Selection ring = util.select().position(RING[0][0], 1, RING[0][1]);
        for (int i = 1; i < RING.length; i++) {
            ring = ring.add(util.select().position(RING[i][0], 1, RING[i][1]));
        }
        scene.world().showSection(ring, Direction.DOWN);
        scene.idle(15);
        kit.say(2, util.vector().topOf(new BlockPos(RING[0][0], 1, RING[0][1])));

        kit.click(MATRIX.below());
        kit.say(3, util.vector().centerOf(MATRIX));
    }

    private static Selection column(SceneBuildingUtil util, int corner) {
        return util.select().fromTo(CORNERS[corner][0], 1, CORNERS[corner][1], CORNERS[corner][0], 2, CORNERS[corner][1]);
    }
}
