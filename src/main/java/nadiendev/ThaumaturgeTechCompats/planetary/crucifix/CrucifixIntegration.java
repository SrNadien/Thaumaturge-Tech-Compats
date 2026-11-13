package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class CrucifixIntegration {
    private CrucifixIntegration() {
    }

    public static boolean enabled(PlanetaryTier tier) {
        if (!CompatToggles.enabled(tier.crucifixToggle())) {
            return false;
        }
        if (tier.creative()) {
            return true;
        }
        for (PlanetaryTier step : PlanetaryTier.values()) {
            if (step.creative()) {
                continue;
            }
            if (step.ordinal() < tier.ordinal() && !CompatToggles.enabled(step.crucifixToggle())) {
                return false;
            }
        }
        return true;
    }

    public static void register(IEventBus modBus) {
        CrucifixRegistry.register(modBus);
        modBus.addListener(CrucifixNetwork::register);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, CrucifixConfig.SPEC, CrucifixConfig.FILE_NAME);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, CrucifixClientConfig.SPEC, CrucifixClientConfig.FILE_NAME);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (CrucifixRegistry.has(tier)) {
                output.accept(CrucifixRegistry.item(tier));
            }
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new CrucifixAssets(output));
        generator.addProvider(event.includeClient(), new CrucifixSoundProvider(output, helper));
        generator.addProvider(event.includeClient(), new CrucifixParticleProvider(output, helper));
        generator.addProvider(event.includeServer(), new CrucifixContent(output));
    }
}
