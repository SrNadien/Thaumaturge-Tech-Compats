package nadiendev.ThaumaturgeTechCompats.registry;

import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.WandVisHelper;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterIntegration;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechFeatures;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.intelligence.UnholyIntelligenceIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.charger.ChargerIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.core.CoreIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.field.FieldIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.gear.GearIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.link.LinkIntegration;
import nadiendev.ThaumaturgeTechCompats.focus.FocusIntegration;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.golem.GolemIntegration;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionIntegration;
import nadiendev.ThaumaturgeTechCompats.jar.JarIntegration;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePowerIntegration;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixIntegration;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PearlIntegration;
import nadiendev.ThaumaturgeTechCompats.planetary.salis.SalisIntegration;
import nadiendev.ThaumaturgeTechCompats.purity.PurityIntegration;
import nadiendev.ThaumaturgeTechCompats.scribing.ScribingIntegration;
import nadiendev.ThaumaturgeTechCompats.singularity.SingularityIntegration;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodIntegration;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import nadiendev.ThaumaturgeTechCompats.wandtier.WandTierIntegration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public final class ModCreativeSubTabs {
    private ModCreativeSubTabs() {
    }

    public static List<CreativeSubTab> build(CreativeParent parent, CreativeModeTab parentTab) {
        List<CreativeSubTab> tabs = new ArrayList<>();
        for (CreativeSection section : CreativeSection.values()) {
            if (section.parent() == parent && present(section)) {
                tabs.add(new CreativeSubTab.Builder(CreativeModeTab.builder().displayItems((parameters, output) -> fill(section, output)),
                        parentTab, ThaumaturgeTechCompats.id(section.id()), Component.translatable(section.key()))
                        .hideParentTitle()
                        .build());
            }
        }
        return List.copyOf(tabs);
    }

    private static boolean loaded(String modId) {
        return ModList.get() != null && ModList.get().isLoaded(modId);
    }

    private static boolean present(CreativeSection section) {
        return switch (section) {
            case ORITECH -> loaded(OritechFeatures.MOD_ID);
            case BOTANIA -> loaded(BotaniaIntegration.MOD_ID);
            case VOID -> loaded(VoidMinersMagicalIntegration.MOD_ID);
            default -> true;
        };
    }

    private static void fill(CreativeSection section, CreativeModeTab.Output output) {
        switch (section) {
            case CORE -> core(output);
            case MACHINES -> AvaritiaMachinesIntegration.fillCreativeTab(output);
            case DEVICES -> devices(output);
            case JARS -> jars(output);
            case MATRICES -> matrices(output);
            case PEARLS -> pearls(output);
            case ORITECH -> oritech(output);
            case BOTANIA -> BotaniaIntegration.fillCreativeTab(output);
            case VOID -> VoidMinersMagicalIntegration.fillCreativeTab(output);
            case NODES -> NodePowerIntegration.fillCreativeTab(new FilteredOutput(output, path -> !fluxJar(path)));
            case FLUX_NETWORK -> fluxNetwork(output);
            case FLUX_GEAR -> fluxGear(output);
            case FOOD_TAINTED -> TaintedFoodIntegration.fillCreativeTab(output);
            case PURITY -> PurityIntegration.fillCreativeTab(output);
            case EXTRAS -> extras(output);
        }
    }

    private static boolean fluxJar(String path) {
        return path.endsWith("flux_jar");
    }

    private static void core(CreativeModeTab.Output output) {
        output.accept(ModItems.AKASHIC_RECORD.get());
        output.accept(ModItems.EXTREMELY_PRIMORDIAL_PEARL.get());
        ItemStack wand = ItemWand.create(TTItems.WAND.get(), ModWandParts.INFINITY_CAP.get(), ModWandParts.INFINITY_ROD.get(), false);
        WandVisHelper.fill(wand);
        output.accept(wand);
        ScribingIntegration.fillCreativeTab(output);
        SingularityIntegration.fillCreativeTab(output);
    }

    private static void jars(CreativeModeTab.Output output) {
        JarIntegration.fillCreativeTab(output);
        NodePowerIntegration.fillCreativeTab(new FilteredOutput(output, ModCreativeSubTabs::fluxJar));
    }

    private static void devices(CreativeModeTab.Output output) {
        SmelterIntegration.fillCreativeTab(output);
        StabilizerIntegration.fillCreativeTab(output);
        TransducerIntegration.fillCreativeTab(output);
        FurnaceIntegration.fillCreativeTab(output);
        BrainChargerIntegration.fillCreativeTab(output);
        GolemIntegration.fillCreativeTab(output);
        WandTierIntegration.fillCreativeTab(output);
        FocusIntegration.fillCreativeTab(output);
    }

    private static void matrices(CreativeModeTab.Output output) {
        InfusionIntegration.fillCreativeTab(output);
        SalisIntegration.fillCreativeTab(output);
    }

    private static void pearls(CreativeModeTab.Output output) {
        PearlIntegration.fillCreativeTab(output);
        CrucifixIntegration.fillCreativeTab(output);
    }

    private static void oritech(CreativeModeTab.Output output) {
        OritechIntegration.fillCreativeTab(output);
        UnholyIntelligenceIntegration.fillCreativeTab(output);
    }

    private static void extras(CreativeModeTab.Output output) {
        EnderIOIntegration.fillCreativeTab(output);
        AuraConverterIntegration.fillCreativeTab(output);
    }

    private static void fluxNetwork(CreativeModeTab.Output output) {
        CoreIntegration.fillCreativeTab(output);
        LinkIntegration.fillCreativeTab(output);
        ChargerIntegration.fillCreativeTab(output);
    }

    private static void fluxGear(CreativeModeTab.Output output) {
        GearIntegration.fillCreativeTab(output);
        FieldIntegration.fillCreativeTab(output);
    }
}
