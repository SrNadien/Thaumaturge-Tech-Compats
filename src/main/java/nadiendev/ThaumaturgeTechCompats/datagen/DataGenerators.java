package nadiendev.ThaumaturgeTechCompats.datagen;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerIntegration;
import nadiendev.ThaumaturgeTechCompats.creative.CreativeIntegration;
import nadiendev.ThaumaturgeTechCompats.focus.FocusIntegration;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import nadiendev.ThaumaturgeTechCompats.golem.GolemIntegration;
import nadiendev.ThaumaturgeTechCompats.jar.JarIntegration;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePowerIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesIntegration;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.intelligence.UnholyIntelligenceIntegration;
import nadiendev.ThaumaturgeTechCompats.scribing.ScribingIntegration;
import nadiendev.ThaumaturgeTechCompats.singularity.SingularityIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechIntegration;
import nadiendev.ThaumaturgeTechCompats.purity.PurityIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.forcecraft.ForceCraftIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagAspectsIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterIntegration;
import nadiendev.ThaumaturgeTechCompats.emc.EmcIntegration;
import nadiendev.ThaumaturgeTechCompats.essences.EssencesIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.charger.ChargerIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.core.CoreIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.field.FieldIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.gear.GearIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.link.LinkIntegration;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixIntegration;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PearlIntegration;
import nadiendev.ThaumaturgeTechCompats.planetary.salis.SalisIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.wandtier.WandTierIntegration;
import nadiendev.ThaumaturgeTechCompats.datagen.provider.HaloItemModelProvider;
import nadiendev.ThaumaturgeTechCompats.ponder.PonderSchematicProvider;
import nadiendev.ThaumaturgeTechCompats.datagen.provider.ModLanguageProvider;
import nadiendev.ThaumaturgeTechCompats.datagen.provider.ModTranslations;
import nadiendev.ThaumaturgeTechCompats.datagen.provider.ThaumaturgeContentProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID)
public final class DataGenerators {
    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        gatherData(event);
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        gatherData(event);
    }

    private static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event instanceof GatherDataEvent.Client, new HaloItemModelProvider(output));
        generator.addProvider(event instanceof GatherDataEvent.Client, new PonderSchematicProvider(output));
        generator.addProvider(event instanceof GatherDataEvent.Client, new ModLanguageProvider(output, "en_us", ModTranslations::english));
        generator.addProvider(event instanceof GatherDataEvent.Client, new ModLanguageProvider(output, "es_es", ModTranslations::spanish));
        generator.addProvider(event instanceof GatherDataEvent.Client, new ModLanguageProvider(output, "es_ar", ModTranslations::spanish));
        generator.addProvider(event instanceof GatherDataEvent.Client, new ModLanguageProvider(output, "es_mx", ModTranslations::spanish));

        generator.addProvider(event instanceof GatherDataEvent.Server, new ThaumaturgeContentProvider(output));

        JarIntegration.addProviders(event);
        NodePowerIntegration.addProviders(event);
        BotaniaIntegration.addProviders(event);
        InfusionIntegration.addProviders(event);
        VoidMinersMagicalIntegration.addProviders(event);
        AvaritiaMachinesIntegration.addProviders(event);
        TaintedFoodIntegration.addProviders(event);
        UnholyIntelligenceIntegration.addProviders(event);
        ScribingIntegration.addProviders(event);
        SmelterIntegration.addProviders(event);
        StabilizerIntegration.addProviders(event);
        TransducerIntegration.addProviders(event);
        FurnaceIntegration.addProviders(event);
        GolemIntegration.addProviders(event);
        WandTierIntegration.addProviders(event);
        FocusIntegration.addProviders(event);
        BrainChargerIntegration.addProviders(event);
        CreativeIntegration.addProviders(event);
        SingularityIntegration.addProviders(event);
        generator.addProvider(event instanceof GatherDataEvent.Server, new TechCategory(generator.getPackOutput()));
        OritechIntegration.addProviders(event);
        PurityIntegration.addProviders(event);
        ForceCraftIntegration.addProviders(event);
        TagAspectsIntegration.addProviders(event);
        EnderIOIntegration.addProviders(event);
        AuraConverterIntegration.addProviders(event);
        CoreIntegration.addProviders(event);
        LinkIntegration.addProviders(event);
        ChargerIntegration.addProviders(event);
        GearIntegration.addProviders(event);
        FieldIntegration.addProviders(event);
        SalisIntegration.addProviders(event);
        PearlIntegration.addProviders(event);
        CrucifixIntegration.addProviders(event);
        EssencesIntegration.addProviders(event);
        EmcIntegration.addProviders(event);
    }
}
