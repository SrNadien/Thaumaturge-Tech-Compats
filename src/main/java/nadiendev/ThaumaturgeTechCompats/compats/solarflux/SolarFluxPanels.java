package nadiendev.ThaumaturgeTechCompats.compats.solarflux;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.world.item.Item;
import org.zeith.solarflux.panels.SolarPanel;

import java.util.ArrayList;
import java.util.List;

public final class SolarFluxPanels {
    private static final List<SolarPanel> PANELS = new ArrayList<>();

    private SolarFluxPanels() {
    }

    public static void register() {
        add(CompatToggles.SOLAR_ALCHEMICAL, "alchemical", 8L, 64L, 125_000L, "Alchemical Solar Panel", "Panel solar alquímico");
        add(CompatToggles.SOLAR_ALCHEMICAL_BRASS, "alchemical_brass", 32L, 256L, 425_000L, "Alchemical Brass Solar Panel", "Panel solar de latón alquímico");
        add(CompatToggles.SOLAR_THAUMIUM, "thaumium", 128L, 1_024L, 2_000_000L, "Thaumium Solar Panel", "Panel solar de taumio");
        add(CompatToggles.SOLAR_VOID_METAL, "void_metal", 512L, 4_096L, 8_000_000L, "Void Metal Solar Panel", "Panel solar de metal del vacío");
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (SolarPanel panel : PANELS) {
            items.add(panel.asItem());
        }
        return items;
    }

    private static void add(String feature, String name, long generation, long transfer, long capacity, String english, String spanish) {
        if (!CompatToggles.enabled(feature)) {
            return;
        }
        SolarPanel panel = SolarPanel.builder()
                .name(name)
                .generation(generation)
                .capacity(capacity)
                .transfer(transfer)
                .buildAndRegister()
                .setCompatMod(ThaumaturgeTechCompats.MOD_ID)
                .langBuilder()
                .put("en_us", english)
                .put("es_es", spanish)
                .put("es_ar", spanish)
                .put("es_mx", spanish)
                .build();
        PANELS.add(panel);
    }
}
