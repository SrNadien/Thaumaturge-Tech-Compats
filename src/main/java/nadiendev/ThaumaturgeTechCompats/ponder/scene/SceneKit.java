package nadiendev.ThaumaturgeTechCompats.ponder.scene;

import nadiendev.ThaumaturgeTechCompats.ponder.PonderBlocks;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScript;
import net.createmod.catnip.api.math.Pointing;
import net.createmod.ponder.api.client.element.TextElementBuilder;
import net.createmod.ponder.api.client.scene.SceneBuilder;
import net.createmod.ponder.api.client.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public final class SceneKit {
    public static final String FLOOR = "minecraft:smooth_stone";
    public static final String SALIS_MUNDUS = "thaumaturge:salis_mundus";

    private final SceneBuilder scene;
    private final SceneBuildingUtil util;
    private final PonderScript script;

    private SceneKit(SceneBuilder scene, SceneBuildingUtil util, PonderScript script) {
        this.scene = scene;
        this.util = util;
        this.script = script;
    }

    public static SceneKit start(SceneBuilder scene, SceneBuildingUtil util, PonderScript script, int size) {
        scene.title(script.id(), script.title());
        scene.configureBasePlate(0, 0, size);
        scene.showBasePlate();
        scene.idle(10);
        return new SceneKit(scene, util, script);
    }

    public void say(int line) {
        say(line, null);
    }

    public void say(int line, Vec3 target) {
        String text = script.line(line);
        int ticks = Math.min(240, 50 + text.length());
        TextElementBuilder builder = scene.overlay().showText(ticks).text(text);
        if (target != null) {
            builder.pointAt(target).placeNearTarget();
        }
        scene.idle(ticks + 10);
    }

    public void click(BlockPos pos, String itemId) {
        scene.overlay().showControls(util.vector().topOf(pos), Pointing.DOWN, 40).rightClick().withItem(PonderBlocks.stack(itemId));
        scene.idle(30);
    }

    public void click(BlockPos pos) {
        scene.overlay().showControls(util.vector().topOf(pos), Pointing.DOWN, 40).rightClick();
        scene.idle(30);
    }

    public void dust(BlockPos pos) {
        click(pos, SALIS_MUNDUS);
    }

    public void assemble(BlockPos origin, String[][] layers, Map<Character, BlockState> targets) {
        for (int layer = 0; layer < layers.length; layer++) {
            int y = origin.getY() + layers.length - 1 - layer;
            String[] rows = layers[layer];
            for (int row = 0; row < rows.length; row++) {
                String line = rows[row];
                for (int column = 0; column < line.length(); column++) {
                    BlockState target = targets.get(line.charAt(column));
                    if (target != null) {
                        scene.world().setBlock(new BlockPos(origin.getX() + column, y, origin.getZ() + row), target, false);
                    }
                }
            }
        }
        scene.idle(10);
    }
}
