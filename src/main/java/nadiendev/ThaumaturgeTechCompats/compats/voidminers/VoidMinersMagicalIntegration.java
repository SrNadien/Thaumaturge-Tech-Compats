package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.ArrayList;
import java.util.List;

public final class VoidMinersMagicalIntegration {
    public static final String MOD_ID = "voidminersremastered";
    public static final String TOGGLE_MINER = "voidminers_magical_miner";
    public static final String TOGGLE_SOLAR = "voidminers_magical_solar";

    private static final ResourceKey<BlockEntityType<?>> MINER_ENTITY = ResourceKey.create(
            Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "controller_base"));
    private static final ResourceKey<BlockEntityType<?>> SOLAR_ENTITY = ResourceKey.create(
            Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "solar_base"));

    private VoidMinersMagicalIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean active() {
        return isLoaded() && CompatToggles.enabled(CompatToggles.NODE_POWER);
    }

    public static List<String> toggleIds() {
        List<String> ids = new ArrayList<>();
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            ids.add(tier.minerToggle());
            ids.add(tier.solarToggle());
            ids.add(tier.minerUpgradesToggle());
            ids.add(tier.solarUpgradesToggle());
        }
        return ids;
    }

    public static boolean minerTierEnabled(VoidMinersMagicalTier tier) {
        if (!active() || !CompatToggles.enabled(TOGGLE_MINER)) {
            return false;
        }
        for (VoidMinersMagicalTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.minerToggle())) {
                return false;
            }
        }
        return true;
    }

    public static boolean solarTierEnabled(VoidMinersMagicalTier tier) {
        if (!active() || !CompatToggles.enabled(TOGGLE_SOLAR)) {
            return false;
        }
        for (VoidMinersMagicalTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.solarToggle())) {
                return false;
            }
        }
        return true;
    }

    public static boolean minerUpgradesEnabled(VoidMinersMagicalTier tier) {
        if (!minerTierEnabled(tier)) {
            return false;
        }
        for (VoidMinersMagicalTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.minerUpgradesToggle())) {
                return false;
            }
        }
        return true;
    }

    public static boolean solarUpgradesEnabled(VoidMinersMagicalTier tier) {
        if (!solarTierEnabled(tier)) {
            return false;
        }
        for (VoidMinersMagicalTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.solarUpgradesToggle())) {
                return false;
            }
        }
        return true;
    }

    private static boolean anyEnabled() {
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            if (minerTierEnabled(tier) || solarTierEnabled(tier)) {
                return true;
            }
        }
        return false;
    }

    public static void register(IEventBus modBus) {
        if (!anyEnabled()) {
            return;
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, VoidMinersMagicalConfig.SPEC, VoidMinersMagicalConfig.FILE_NAME);
        VoidMinersMagicalRegistry.register(modBus);
        modBus.addListener(VoidMinersMagicalIntegration::addValidBlocks);
        modBus.addListener(VoidMinersMagicalIntegration::setup);
        if (FMLEnvironment.getDist().isClient()) {
            modBus.addListener(VoidMinersMagicalPack::register);
        }
    }

    public static List<Block> blocks() {
        if (!anyEnabled()) {
            return List.of();
        }
        return VoidMinersMagicalRegistry.blocks();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!anyEnabled()) {
            return;
        }
        for (Item item : VoidMinersMagicalRegistry.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new VoidMinersMagicalAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new VoidMinersMagicalContent(output));
    }

    private static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        List<Block> miners = new ArrayList<>();
        List<Block> solars = new ArrayList<>();
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            Block miner = VoidMinersMagicalRegistry.miner(tier);
            if (miner != null) {
                miners.add(miner);
            }
            Block solar = VoidMinersMagicalRegistry.solar(tier);
            if (solar != null) {
                solars.add(solar);
            }
        }
        if (!miners.isEmpty()) {
            event.modify(MINER_ENTITY, miners.toArray(Block[]::new));
        }
        if (!solars.isEmpty()) {
            event.modify(SOLAR_ENTITY, solars.toArray(Block[]::new));
        }
    }

    private static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
                Block miner = VoidMinersMagicalRegistry.miner(tier);
                if (miner != null) {
                    VoidMinersMagicalStructures.registerMiner(tier, miner, VoidMinersMagicalRegistry.minerFrame(tier));
                }
                Block solar = VoidMinersMagicalRegistry.solar(tier);
                if (solar != null) {
                    VoidMinersMagicalStructures.registerSolar(tier, solar, VoidMinersMagicalRegistry.solarFrame(tier));
                }
            }
        });
    }
}
