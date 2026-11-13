package nadiendev.ThaumaturgeTechCompats.ponder.scene;

import nadiendev.ThaumaturgeTechCompats.ponder.PonderBlocks;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScripts;
import nadiendev.ThaumaturgeTechCompats.ponder.StructureSketch;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public final class AlchemyScenes {
    public static final String[][] FURNACE_LAYERS = {
            {"ACA", "C C", "ACA"},
            {"VNV", "NFN", "VNV"}
    };
    public static final BlockPos FURNACE_ORIGIN = new BlockPos(1, 1, 1);
    public static final BlockPos FURNACE_CORE = new BlockPos(2, 1, 2);
    public static final int FURNACE_SIZE = 5;

    private static final String[][] INFERNAL_LAYERS = {
            {"BOB", "O O", "BOB"},
            {"BOB", "OLO", "BIB"},
            {"BOB", "OOO", "BOB"}
    };
    private static final BlockPos INFERNAL_ORIGIN = new BlockPos(1, 1, 1);
    private static final BlockPos INFERNAL_LAVA = new BlockPos(2, 2, 2);
    private static final BlockPos INFERNAL_BARS = new BlockPos(2, 2, 3);
    private static final int INFERNAL_SIZE = 5;

    private static final String[][] PRESS_LAYERS = {
            {"  ", "I "},
            {"CA", "PT"}
    };
    private static final BlockPos PRESS_ORIGIN = new BlockPos(1, 1, 1);
    private static final BlockPos PRESS_PISTON = new BlockPos(1, 1, 2);
    private static final int PRESS_SIZE = 4;

    private static final String[][] THAUMATORIUM_LAYERS = {
            {"T"},
            {"M"},
            {"B"}
    };
    private static final BlockPos THAUMATORIUM_ORIGIN = new BlockPos(2, 1, 2);
    private static final BlockPos CRUCIBLE = new BlockPos(2, 1, 2);
    private static final BlockPos THAUMATORIUM_MASTER = new BlockPos(2, 2, 2);
    private static final BlockPos THAUMATORIUM_TOP = new BlockPos(2, 3, 2);
    private static final int[][] MATRIX_SPOTS = {{1, 2}, {3, 2}, {1, 3}, {3, 3}};
    private static final int THAUMATORIUM_SIZE = 5;

    private AlchemyScenes() {
    }

    public static Map<Character, BlockState> furnaceSources(String core) {
        Map<Character, BlockState> keys = new HashMap<>();
        keys.put('A', PonderBlocks.state("thaumaturge:alembic"));
        keys.put('C', PonderBlocks.state("thaumaturge:alchemical_construct"));
        keys.put('V', PonderBlocks.state("thaumaturge:advanced_alchemical_construct"));
        keys.put('N', PonderBlocks.state("thaumaturge:advanced_alchemical_construct"));
        keys.put('F', PonderBlocks.state(core));
        return keys;
    }

    public static Map<Character, BlockState> furnaceTargets(String core) {
        Map<Character, BlockState> keys = new HashMap<>();
        keys.put('A', PonderBlocks.state("thaumaturge:advanced_alchemical_furnace_alembic_placeholder"));
        keys.put('C', PonderBlocks.state("thaumaturge:advanced_alchemical_furnace_construct_placeholder"));
        keys.put('V', PonderBlocks.state("thaumaturge:advanced_alchemical_furnace_advanced_construct_placeholder"));
        keys.put('N', PonderBlocks.state("thaumaturge:advanced_alchemical_furnace_nozzle"));
        keys.put('F', PonderBlocks.state(core));
        return keys;
    }

    public static StructureSketch furnaceSketch(String core) {
        return new StructureSketch(FURNACE_SIZE).floor(PonderBlocks.state(SceneKit.FLOOR))
                .pattern(FURNACE_ORIGIN.getX(), FURNACE_ORIGIN.getY(), FURNACE_ORIGIN.getZ(), furnaceSources(core), FURNACE_LAYERS);
    }

    public static void advancedFurnace(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.ADVANCED_FURNACE, FURNACE_SIZE);

        scene.world().showSection(util.select().fromTo(1, 1, 1, 3, 1, 3), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(FURNACE_CORE));

        scene.world().showSection(util.select().fromTo(1, 2, 1, 3, 2, 3), Direction.DOWN);
        scene.idle(15);
        kit.say(1, util.vector().topOf(new BlockPos(1, 2, 1)));

        kit.dust(new BlockPos(1, 2, 2));
        kit.assemble(FURNACE_ORIGIN, FURNACE_LAYERS, furnaceTargets("thaumaturge:advanced_alchemical_furnace"));
        scene.effects().indicateSuccess(FURNACE_CORE);
        kit.say(2, util.vector().centerOf(FURNACE_CORE));

        kit.say(3, util.vector().topOf(new BlockPos(2, 2, 2)));
        kit.say(4, util.vector().blockSurface(new BlockPos(3, 1, 2), Direction.EAST));
    }

    public static StructureSketch infernalSketch() {
        Map<Character, BlockState> keys = new HashMap<>();
        keys.put('B', PonderBlocks.state("minecraft:nether_bricks"));
        keys.put('O', PonderBlocks.state("minecraft:obsidian"));
        keys.put('L', PonderBlocks.state("minecraft:lava"));
        keys.put('I', PonderBlocks.state("minecraft:iron_bars"));
        return new StructureSketch(INFERNAL_SIZE).floor(PonderBlocks.state(SceneKit.FLOOR))
                .pattern(INFERNAL_ORIGIN.getX(), INFERNAL_ORIGIN.getY(), INFERNAL_ORIGIN.getZ(), keys, INFERNAL_LAYERS);
    }

    public static void infernalFurnace(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.INFERNAL_FURNACE, INFERNAL_SIZE);

        scene.world().showSection(util.select().fromTo(1, 1, 1, 3, 1, 3), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(1, 2, 1, 3, 2, 3), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(1, 3, 1, 3, 3, 3), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().centerOf(INFERNAL_LAVA));

        kit.dust(new BlockPos(1, 3, 2));
        Map<Character, BlockState> targets = new HashMap<>();
        targets.put('B', PonderBlocks.state("thaumaturge:placeholder_nether_bricks"));
        targets.put('O', PonderBlocks.state("thaumaturge:placeholder_obsidian"));
        targets.put('I', Blocks.AIR.defaultBlockState());
        targets.put('L', PonderBlocks.state("thaumaturge:infernal_furnace", "facing", "south"));
        kit.assemble(INFERNAL_ORIGIN, INFERNAL_LAYERS, targets);
        scene.effects().indicateSuccess(INFERNAL_LAVA);
        kit.say(1, util.vector().blockSurface(INFERNAL_BARS, Direction.SOUTH));

        scene.overlay().showControls(util.vector().topOf(new BlockPos(2, 3, 2)), Pointing.DOWN, 40).withItem(PonderBlocks.stack("minecraft:iron_ore"));
        scene.idle(30);
        kit.say(2, util.vector().topOf(new BlockPos(2, 3, 2)));
        kit.say(3, util.vector().blockSurface(INFERNAL_BARS, Direction.SOUTH));
    }

    public static StructureSketch pressSketch() {
        Map<Character, BlockState> keys = new HashMap<>();
        keys.put('C', PonderBlocks.state("minecraft:cauldron"));
        keys.put('A', PonderBlocks.state("minecraft:anvil"));
        keys.put('I', PonderBlocks.state("minecraft:iron_bars"));
        keys.put('P', PonderBlocks.state("minecraft:piston", "facing", "up", "extended", "false"));
        keys.put('T', PonderBlocks.state("thaumaturge:table_stone"));
        return new StructureSketch(PRESS_SIZE).floor(PonderBlocks.state(SceneKit.FLOOR))
                .pattern(PRESS_ORIGIN.getX(), PRESS_ORIGIN.getY(), PRESS_ORIGIN.getZ(), keys, PRESS_LAYERS);
    }

    public static void golemPress(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.GOLEM_PRESS, PRESS_SIZE);

        scene.world().showSection(util.select().fromTo(1, 1, 1, 2, 1, 2), Direction.DOWN);
        scene.idle(15);
        scene.world().showSection(util.select().position(1, 2, 2), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(PRESS_PISTON.above()));

        kit.dust(new BlockPos(2, 1, 1));
        Map<Character, BlockState> targets = new HashMap<>();
        targets.put('C', PonderBlocks.state("thaumaturge:placeholder_cauldron"));
        targets.put('A', PonderBlocks.state("thaumaturge:placeholder_anvil"));
        targets.put('I', PonderBlocks.state("thaumaturge:placeholder_iron_bars"));
        targets.put('P', PonderBlocks.state("thaumaturge:golem_builder"));
        targets.put('T', PonderBlocks.state("thaumaturge:placeholder_table"));
        kit.assemble(PRESS_ORIGIN, PRESS_LAYERS, targets);
        scene.effects().indicateSuccess(PRESS_PISTON);
        kit.say(1, util.vector().centerOf(PRESS_PISTON));

        kit.say(2, util.vector().blockSurface(PRESS_PISTON, Direction.DOWN));
    }

    public static StructureSketch thaumatoriumSketch(boolean assembled, String matrixBlock) {
        Map<Character, BlockState> keys = new HashMap<>();
        keys.put('B', PonderBlocks.state("thaumaturge:crucible"));
        if (assembled) {
            keys.put('M', PonderBlocks.state("thaumaturge:thaumatorium", "facing", "south"));
            keys.put('T', PonderBlocks.state("thaumaturge:thaumatorium_top"));
        } else {
            keys.put('M', PonderBlocks.state("thaumaturge:alchemical_construct"));
            keys.put('T', PonderBlocks.state("thaumaturge:alchemical_construct"));
        }
        StructureSketch sketch = new StructureSketch(THAUMATORIUM_SIZE).floor(PonderBlocks.state(SceneKit.FLOOR))
                .pattern(THAUMATORIUM_ORIGIN.getX(), THAUMATORIUM_ORIGIN.getY(), THAUMATORIUM_ORIGIN.getZ(), keys, THAUMATORIUM_LAYERS);
        for (int[] spot : MATRIX_SPOTS) {
            sketch.set(spot[0], spot[1], 2, matrixState(matrixBlock, spot[0]));
        }
        return sketch;
    }

    public static BlockState matrixState(String block, int x) {
        return PonderBlocks.state(block, "facing", x < THAUMATORIUM_MASTER.getX() ? "east" : "west");
    }

    public static int[][] matrixSpots() {
        return MATRIX_SPOTS;
    }

    public static void thaumatorium(SceneBuilder scene, SceneBuildingUtil util) {
        SceneKit kit = SceneKit.start(scene, util, PonderScripts.THAUMATORIUM, THAUMATORIUM_SIZE);

        scene.world().showSection(util.select().position(CRUCIBLE), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(THAUMATORIUM_MASTER, THAUMATORIUM_TOP), Direction.DOWN);
        scene.idle(15);
        kit.say(0, util.vector().topOf(THAUMATORIUM_TOP));

        kit.dust(THAUMATORIUM_TOP);
        Map<Character, BlockState> targets = new HashMap<>();
        targets.put('M', PonderBlocks.state("thaumaturge:thaumatorium", "facing", "south"));
        targets.put('T', PonderBlocks.state("thaumaturge:thaumatorium_top"));
        kit.assemble(THAUMATORIUM_ORIGIN, THAUMATORIUM_LAYERS, targets);
        scene.effects().indicateSuccess(THAUMATORIUM_MASTER);
        kit.say(1, util.vector().centerOf(CRUCIBLE));

        kit.say(2, util.vector().blockSurface(THAUMATORIUM_MASTER, Direction.SOUTH));

        scene.world().showSection(util.select().fromTo(1, 2, 2, 1, 3, 2).add(util.select().fromTo(3, 2, 2, 3, 3, 2)), Direction.DOWN);
        scene.idle(15);
        kit.say(3, util.vector().topOf(new BlockPos(1, 3, 2)));
    }
}
