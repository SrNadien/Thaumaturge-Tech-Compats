package nadiendev.ThaumaturgeTechCompats.datagen;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerIntegration;
import nadiendev.ThaumaturgeTechCompats.creative.CreativeIntegration;
import nadiendev.ThaumaturgeTechCompats.focus.FocusIntegration;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import nadiendev.ThaumaturgeTechCompats.golem.GolemIntegration;
import nadiendev.ThaumaturgeTechCompats.jar.JarIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.MagicalUneartherIntegration;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePowerIntegration;
import nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.FarmersDelightTaintedIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools.MekanismToolIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesIntegration;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.intelligence.UnholyIntelligenceIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.ThaummotorIntegration;
import nadiendev.ThaumaturgeTechCompats.scribing.ScribingIntegration;
import nadiendev.ThaumaturgeTechCompats.singularity.SingularityIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechIntegration;
import nadiendev.ThaumaturgeTechCompats.purity.PurityIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.FtbUnearthedIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.forcecraft.ForceCraftIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagAspectsIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedIntegration;
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
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill.MagicalInfinityDrillIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons.IndustrialForegoingAddonsIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateClusterProvider;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingClusterProvider;
import nadiendev.ThaumaturgeTechCompats.compats.mekanism.MekanismIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.solarflux.SolarFluxIntegration;
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
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new HaloItemModelProvider(output));
        generator.addProvider(event.includeClient(), new PonderSchematicProvider(output));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output, "en_us", ModTranslations::english));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output, "es_es", ModTranslations::spanish));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output, "es_ar", ModTranslations::spanish));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output, "es_mx", ModTranslations::spanish));

        generator.addProvider(event.includeServer(), new ThaumaturgeContentProvider(output));

        JarIntegration.addProviders(event);
        NodePowerIntegration.addProviders(event);
        FarmersDelightTaintedIntegration.addProviders(event);
        BotaniaIntegration.addProviders(event);
        InfusionIntegration.addProviders(event);
        VoidMinersMagicalIntegration.addProviders(event);
        MekanismToolIntegration.addProviders(event);
        AvaritiaMachinesIntegration.addProviders(event);
        TaintedFoodIntegration.addProviders(event);
        UnholyIntelligenceIntegration.addProviders(event);
        ThaummotorIntegration.addProviders(event);
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
        SolarFluxIntegration.addProviders(event);
        generator.addProvider(event.includeServer(), new TechCategory(generator.getPackOutput()));
        CreateIntegration.addProviders(event);
        OritechIntegration.addProviders(event);
        PurityIntegration.addProviders(event);
        FtbUnearthedIntegration.addProviders(event);
        MagicalUneartherIntegration.addProviders(event);
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
        SophisticatedIntegration.addProviders(event);
        IndustrialForegoingIntegration.addProviders(event);
        IndustrialForegoingAddonsIntegration.addProviders(event);
        MagicalInfinityDrillIntegration.addProviders(event);
        generator.addProvider(event.includeServer(), new CreateClusterProvider(generator.getPackOutput()));
        MekanismIntegration.addProviders(event);
        generator.addProvider(event.includeServer(), new IndustrialForegoingClusterProvider(generator.getPackOutput()));
    }
}
