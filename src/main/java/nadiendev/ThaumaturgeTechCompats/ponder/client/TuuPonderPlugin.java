package nadiendev.ThaumaturgeTechCompats.ponder.client;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderConfig;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScript;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderScripts;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.AlchemyScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.InfusionScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.NodeScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.TieredScenes;
import nadiendev.ThaumaturgeTechCompats.ponder.scene.WorkbenchScenes;
import net.createmod.ponder.api.client.registration.PonderPlugin;
import net.createmod.ponder.api.client.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.client.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.client.scene.PonderStoryBoard;
import net.createmod.ponder.api.client.registration.TagBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;

public final class TuuPonderPlugin implements PonderPlugin {
    private static final Identifier TAG = ThaumaturgeTechCompats.id(PonderScripts.MULTIBLOCKS_TAG);
    private static final String BASE = "thaumaturge";

    private record Entry(PonderScript script, PonderStoryBoard board, List<Identifier> components) {
    }

    @Override
    public String getModId() {
        return ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<Identifier> helper) {
        for (Entry entry : enabledEntries()) {
            helper.forComponents(entry.components()).addStoryBoard(entry.script().id(), entry.board(), TAG);
        }
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<Identifier> helper) {
        List<Entry> entries = enabledEntries();
        if (entries.isEmpty()) {
            return;
        }
        Item icon = BuiltInRegistries.ITEM.getValue(base("infusion_matrix"));
        TagBuilder tag = helper.registerTag(TAG).addToIndex();
        if (BuiltInRegistries.ITEM.wrapAsHolder(icon).areComponentsBound()) {
            tag = tag.item(icon, true, false);
        } else {
            tag = tag.idAsIcon();
        }
        tag
                .title(PonderScripts.MULTIBLOCKS_TITLE)
                .description(PonderScripts.MULTIBLOCKS_DESCRIPTION)
                .register();

        Set<Identifier> components = new LinkedHashSet<>();
        for (Entry entry : entries) {
            components.addAll(entry.components());
        }
        for (Identifier component : components) {
            helper.addToTag(TAG).add(component);
        }
    }

    private static List<Entry> enabledEntries() {
        List<Entry> enabled = new ArrayList<>();
        if (!PonderConfig.enabled()) {
            return enabled;
        }
        for (Entry entry : entries()) {
            if (PonderConfig.enabled(entry.script())) {
                enabled.add(entry);
            }
        }
        return enabled;
    }

    private static List<Entry> entries() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(PonderScripts.INFUSION_ARCANE, InfusionScenes::arcane,
                List.of(base("infusion_matrix"), base("pedestal_arcane"))));
        entries.add(new Entry(PonderScripts.INFUSION_ANCIENT, InfusionScenes::ancient,
                List.of(base("infusion_matrix"), base("pedestal_ancient"))));
        entries.add(new Entry(PonderScripts.INFUSION_ELDRITCH, InfusionScenes::eldritch,
                List.of(base("infusion_matrix"), base("pedestal_eldritch"))));
        entries.add(new Entry(PonderScripts.ADVANCED_FURNACE, AlchemyScenes::advancedFurnace,
                List.of(base("advanced_alchemical_furnace"), base("advanced_alchemical_construct"))));
        entries.add(new Entry(PonderScripts.INFERNAL_FURNACE, AlchemyScenes::infernalFurnace,
                List.of(base("infernal_furnace"))));
        entries.add(new Entry(PonderScripts.GOLEM_PRESS, AlchemyScenes::golemPress,
                List.of(base("golem_builder"))));
        entries.add(new Entry(PonderScripts.THAUMATORIUM, AlchemyScenes::thaumatorium,
                List.of(base("thaumatorium"), base("brain_box"))));
        entries.add(new Entry(PonderScripts.NODE_TOWER, NodeScenes::energizing,
                List.of(base("node_stabilizer"), base("node_stabilizer_advanced"), base("node_transducer"))));
        entries.add(new Entry(PonderScripts.RESEARCH_TABLE, WorkbenchScenes::researchTable,
                List.of(base("research_table"))));
        entries.add(new Entry(PonderScripts.WORKBENCH_CHARGER, WorkbenchScenes::charger,
                List.of(base("arcane_workbench_charger"))));
        entries.add(new Entry(PonderScripts.TIERED_STABILIZERS, TieredScenes::stabilizers,
                tiers(TieredScenes::stabilizer)));
        entries.add(new Entry(PonderScripts.TIERED_TRANSDUCERS, TieredScenes::transducers,
                tiers(TieredScenes::transducer)));
        List<Identifier> furnaces = tiers(TieredScenes::furnace);
        furnaces.addAll(tiers(TieredScenes::smelter));
        entries.add(new Entry(PonderScripts.TIERED_FURNACES, TieredScenes::furnaces, furnaces));
        entries.add(new Entry(PonderScripts.TIERED_MATRICES, TieredScenes::matrices,
                tiers(TieredScenes::matrix)));
        entries.add(new Entry(PonderScripts.TIERED_CHARGERS, TieredScenes::chargers,
                tiers(TieredScenes::charger)));
        return entries;
    }

    private static Identifier base(String name) {
        return Identifier.fromNamespaceAndPath(BASE, name);
    }

    private static List<Identifier> tiers(IntFunction<String> name) {
        List<Identifier> components = new ArrayList<>();
        for (int tier = 0; tier < 3; tier++) {
            components.add(Identifier.parse(name.apply(tier)));
        }
        return components;
    }
}
