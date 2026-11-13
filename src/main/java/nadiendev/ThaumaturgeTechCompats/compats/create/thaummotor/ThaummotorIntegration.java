package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import com.simibubi.create.api.stress.BlockStressValues;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class ThaummotorIntegration {
    public static final String TOGGLE = "create_thaummotor";

    private ThaummotorIntegration() {
    }

    public static void register(IEventBus modBus) {
        if (CreateIntegration.isLoaded()) {
            ThaummotorRegistry.BLOCKS.register(modBus);
            ThaummotorRegistry.ITEMS.register(modBus);
            ThaummotorRegistry.BLOCK_ENTITIES.register(modBus);
            modBus.addListener(ThaummotorIntegration::setup);
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ThaummotorConfig.SPEC, ThaummotorConfig.FILE_NAME);
    }

    public static List<Block> blocks() {
        if (!CreateIntegration.isLoaded()) {
            return List.of();
        }
        return List.of(ThaummotorRegistry.THAUMMOTOR.get());
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!CreateIntegration.isLoaded() || !CreateToggles.enabled(TOGGLE) || !CompatToggles.enabled(CompatToggles.NODE_POWER)) {
            return;
        }
        output.accept(ThaummotorRegistry.THAUMMOTOR_ITEM.get());
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new ThaummotorAssets(output));
        generator.addProvider(event.includeServer(), new ThaummotorContent(output));
    }

    private static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Block block = ThaummotorRegistry.THAUMMOTOR.get();
            BlockStressValues.IMPACTS.register(block, () -> 0.0D);
            BlockStressValues.CAPACITIES.register(block, () -> ThaummotorConfig.capacity(NodeSourceKind.JAR_NODE) / (double) ThaummotorConfig.speed());
            BlockStressValues.RPM.register(block, new BlockStressValues.GeneratedRpm(ThaummotorConfig.speed(), false));
        });
    }
}
