package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.flux.FluxComponents;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class FieldIntegration {
    private FieldIntegration() {
    }

    public static void register(IEventBus modBus) {
        FieldRegistry.BLOCKS.register(modBus);
        FieldRegistry.ITEMS.register(modBus);
        FieldRegistry.BLOCK_ENTITIES.register(modBus);
        modBus.addListener(ThrusterNetwork::register);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, FieldConfig.SPEC, FieldConfig.FILE_NAME);
    }

    public static boolean enabled(FluxTier tier) {
        for (FluxTier step : FluxTier.values()) {
            if (step.ordinal() <= tier.ordinal() && !CompatToggles.enabled(step.toggle())) {
                return false;
            }
        }
        return true;
    }

    public static boolean thrusterEnabled() {
        return enabled(FluxTier.ALCHEMICAL_BRASS);
    }

    public static List<Block> blocks() {
        return FieldRegistry.blocks();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (FluxTier tier : FluxTier.values()) {
            if (enabled(tier)) {
                output.accept(FieldRegistry.shieldItem(tier).get());
                output.accept(FieldRegistry.terraformerItem(tier).get());
            }
        }
        if (thrusterEnabled()) {
            FlightThrusterItem thruster = FieldRegistry.THRUSTER.get();
            ItemStack empty = new ItemStack(thruster);
            ItemStack charged = new ItemStack(thruster);
            FluxComponents.set(charged, thruster.fluxCapacity(charged));
            output.accept(empty);
            output.accept(charged);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new FieldAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new FieldContent(output));
    }
}
