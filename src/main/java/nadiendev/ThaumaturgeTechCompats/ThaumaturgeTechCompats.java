package nadiendev.ThaumaturgeTechCompats;

import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerIntegration;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.config.ThaumaturgeTechCompatsConfig;
import nadiendev.ThaumaturgeTechCompats.focus.FocusIntegration;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import nadiendev.ThaumaturgeTechCompats.golem.GolemIntegration;
import nadiendev.ThaumaturgeTechCompats.jar.JarIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.FluxComponents;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePowerIntegration;
import nadiendev.ThaumaturgeTechCompats.nodepower.client.RiftJarClientConfig;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesIntegration;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.intelligence.UnholyIntelligenceIntegration;
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
import nadiendev.ThaumaturgeTechCompats.compats.jade.JadeIntegration;
import nadiendev.ThaumaturgeTechCompats.registry.ModConditions;
import nadiendev.ThaumaturgeTechCompats.registry.ModCreativeTabs;
import nadiendev.ThaumaturgeTechCompats.scribing.ScribingIntegration;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.wandtier.WandTierIntegration;
import nadiendev.ThaumaturgeTechCompats.registry.ModItems;
import nadiendev.ThaumaturgeTechCompats.registry.ModWandParts;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ThaumaturgeTechCompats.MOD_ID)
public class ThaumaturgeTechCompats {
    public static final String MOD_ID = "thaumaturgetechcompats";

    public ThaumaturgeTechCompats(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, ThaumaturgeTechCompatsConfig.SPEC);
        container.registerConfig(ModConfig.Type.COMMON, CompatToggles.SPEC, CompatToggles.FILE_NAME);
        container.registerConfig(ModConfig.Type.CLIENT, RiftJarClientConfig.SPEC, RiftJarClientConfig.FILE_NAME);
        ModConditions.CONDITIONS.register(modBus);

        ModItems.ITEMS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
        ModWandParts.RODS.register(modBus);
        ModWandParts.CAPS.register(modBus);

        JarIntegration.register(modBus);
        FluxComponents.COMPONENTS.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, FluxConfig.SPEC, FluxConfig.FILE_NAME);
        NodePowerIntegration.register(modBus);
        CoreIntegration.register(modBus);
        LinkIntegration.register(modBus);
        ChargerIntegration.register(modBus);
        GearIntegration.register(modBus);
        FieldIntegration.register(modBus);
        SalisIntegration.register(modBus);
        PearlIntegration.register(modBus);
        CrucifixIntegration.register(modBus);
        EssencesIntegration.register(modBus);
        EmcIntegration.register(modBus);
        BotaniaIntegration.register(modBus);
        InfusionIntegration.register(modBus);
        VoidMinersMagicalIntegration.register(modBus);
        AvaritiaMachinesIntegration.register(modBus);
        TaintedFoodIntegration.register(modBus);
        UnholyIntelligenceIntegration.register(modBus);
        ScribingIntegration.register(modBus);
        SmelterIntegration.register(modBus);
        StabilizerIntegration.register(modBus);
        TransducerIntegration.register(modBus);
        FurnaceIntegration.register(modBus);
        GolemIntegration.register(modBus);
        WandTierIntegration.register(modBus);
        FocusIntegration.register(modBus);
        BrainChargerIntegration.register(modBus);
        OritechIntegration.register(modBus);
        PurityIntegration.register(modBus);
        ForceCraftIntegration.register(modBus, container);
        TagAspectsIntegration.register(modBus);
        EnderIOIntegration.register(modBus);
        AuraConverterIntegration.register(modBus, container);
        JadeIntegration.register();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
