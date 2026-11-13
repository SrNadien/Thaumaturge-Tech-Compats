package nadiendev.ThaumaturgeTechCompats.ponder;

import nadiendev.ThaumaturgeTechCompats.ponder.scene.AlchemyScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.InfusionScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.NodeScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.TieredScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.WorkbenchScenes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class PonderSchematics {
    private PonderSchematics() {
    }

    public static Map<String, Supplier<StructureSketch>> all() {
        Map<String, Supplier<StructureSketch>> sketches = new LinkedHashMap<>();
        sketches.put(PonderScripts.INFUSION_ARCANE.id(), () -> InfusionScenes.sketch(InfusionScenes.ARCANE));
        sketches.put(PonderScripts.INFUSION_ANCIENT.id(), () -> InfusionScenes.sketch(InfusionScenes.ANCIENT));
        sketches.put(PonderScripts.INFUSION_ELDRITCH.id(), () -> InfusionScenes.sketch(InfusionScenes.ELDRITCH));
        sketches.put(PonderScripts.ADVANCED_FURNACE.id(), () -> AlchemyScenes.furnaceSketch("thaumaturge:smelter_basic"));
        sketches.put(PonderScripts.INFERNAL_FURNACE.id(), AlchemyScenes::infernalSketch);
        sketches.put(PonderScripts.GOLEM_PRESS.id(), AlchemyScenes::pressSketch);
        sketches.put(PonderScripts.THAUMATORIUM.id(), () -> AlchemyScenes.thaumatoriumSketch(false, "thaumaturge:brain_box"));
        sketches.put(PonderScripts.NODE_TOWER.id(), () -> NodeScenes.towerSketch("thaumaturge:node_stabilizer", "thaumaturge:node_transducer"));
        sketches.put(PonderScripts.RESEARCH_TABLE.id(), WorkbenchScenes::tableSketch);
        sketches.put(PonderScripts.WORKBENCH_CHARGER.id(), () -> WorkbenchScenes.chargerSketch("thaumaturge:arcane_workbench_charger"));
        sketches.put(PonderScripts.TIERED_STABILIZERS.id(), TieredScenes::stabilizerSketch);
        sketches.put(PonderScripts.TIERED_TRANSDUCERS.id(), TieredScenes::transducerSketch);
        sketches.put(PonderScripts.TIERED_FURNACES.id(), TieredScenes::furnaceSketch);
        sketches.put(PonderScripts.TIERED_MATRICES.id(), TieredScenes::matrixSketch);
        sketches.put(PonderScripts.TIERED_CHARGERS.id(), TieredScenes::chargerSketch);
        return sketches;
    }
}
