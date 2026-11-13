package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.neoforged.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public final class OritechFeatures {
    public static final String MOD_ID = "oritech";
    public static final String NODES = "oritech_nodes";
    public static final String ADDONS = "oritech_addons";
    public static final String UNHOLY_INTELLIGENCE = "oritech_unholy_intelligence";

    private OritechFeatures() {
    }

    public static boolean loaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static String node(OritechNode node) {
        return "oritech_node_" + node.id();
    }

    public static String addon(OritechTier tier, OritechAddon addon) {
        return "oritech_" + tier.id() + "_" + addon.id() + "_addon";
    }

    public static List<String> all() {
        List<String> ids = new ArrayList<>();
        ids.add(NODES);
        ids.add(ADDONS);
        ids.add(UNHOLY_INTELLIGENCE);
        for (OritechNode node : OritechNode.values()) {
            ids.add(node(node));
        }
        for (OritechTier tier : OritechTier.values()) {
            for (OritechAddon addon : OritechAddon.values()) {
                ids.add(addon(tier, addon));
            }
        }
        return ids;
    }

    public static List<String> nodeChain(OritechNode node) {
        return List.of(NODES, node(node));
    }

    public static List<String> addonChain(OritechTier tier, OritechAddon addon) {
        List<String> ids = new ArrayList<>();
        ids.add(ADDONS);
        for (OritechTier step : OritechTier.values()) {
            ids.add(addon(step, addon));
            if (step == tier) {
                break;
            }
        }
        return ids;
    }

    public static boolean master() {
        return CompatToggles.enabled(MOD_ID);
    }

    public static boolean nodeEnabled(OritechNode node) {
        return master() && allEnabled(nodeChain(node));
    }

    public static boolean addonEnabled(OritechTier tier, OritechAddon addon) {
        return master() && allEnabled(addonChain(tier, addon));
    }

    private static boolean allEnabled(List<String> ids) {
        for (String id : ids) {
            if (!CompatToggles.enabled(id)) {
                return false;
            }
        }
        return true;
    }
}
