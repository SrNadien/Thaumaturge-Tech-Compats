package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.backpack;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedConfig;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTier;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackLinkedStorageResolver;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class BackpackTierRegistry {
    public static final String BACKPACKS_MOD = "sophisticatedbackpacks";
    public static final float EXPLOSION_RESISTANCE = 1200.0F;

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<SophisticatedTier, DeferredBlock<BackpackBlock>> TIER_BLOCKS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, DeferredItem<BackpackItem>> TIER_ITEMS = new EnumMap<>(SophisticatedTier.class);

    static {
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            DeferredBlock<BackpackBlock> block = BLOCKS.register(tier.backpackName(), () -> new BackpackBlock(EXPLOSION_RESISTANCE));
            TIER_BLOCKS.put(tier, block);
            Supplier<Integer> slots = SophisticatedConfig.backpackSlots(tier);
            Supplier<Integer> upgrades = SophisticatedConfig.backpackUpgrades(tier);
            TIER_ITEMS.put(tier, ITEMS.register(tier.backpackName(),
                    () -> new BackpackItem(slots::get, upgrades::get, block, properties -> properties.fireResistant().rarity(tier.rarity()))));
        }
    }

    private BackpackTierRegistry() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(BackpackTierRegistry::addBlockEntityBlocks);
        modBus.addListener(BackpackTierRegistry::registerCapabilities);
        modBus.addListener(BackpackTierRegistry::commonSetup);
        if (FMLEnvironment.getDist().isClient()) {
            BackpackTierClient.register(modBus);
        }
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            blocks.add(TIER_BLOCKS.get(tier).get());
        }
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            items.add(TIER_ITEMS.get(tier).get());
        }
        return items;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        items().forEach(output::accept);
    }

    private static void addBlockEntityBlocks(BlockEntityTypeAddBlocksEvent event) {
        ResourceKey<BlockEntityType<?>> key = ResourceKey.create(Registries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(BACKPACKS_MOD, "backpack"));
        event.modify(key, blocks().toArray(new Block[0]));
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        Item[] items = items().toArray(new Item[0]);
        event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> {
            IBackpackWrapper wrapper = wrapper(stack);
            return wrapper.getContentsUuid().isEmpty() ? EmptyItemHandler.INSTANCE : wrapper.getInventoryForInputOutput();
        }, items);
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> {
            if (!Config.SERVER.itemFluidHandlerEnabled.get()) {
                return null;
            }
            return BackpackWrapper.getCapabilityFluidHandler(stack).orElse(null);
        }, items);
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, context) -> wrapper(stack).getEnergyStorage().orElse(null), items);
    }

    private static IBackpackWrapper wrapper(ItemStack stack) {
        return BackpackLinkedStorageResolver.resolveServerCanonicalHost(stack).orElseGet(() -> BackpackWrapper.fromStack(stack));
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        Identifier netherite = Identifier.fromNamespaceAndPath(BACKPACKS_MOD, "netherite_backpack");
        DispenseItemBehavior delegate = new DispenseItemBehavior() {
            @Override
            public ItemStack dispense(BlockSource source, ItemStack stack) {
                Item reference = BuiltInRegistries.ITEM.get(netherite);
                DispenseItemBehavior behavior = DispenserBlock.DISPENSER_REGISTRY.get(reference);
                return behavior == null || behavior == this ? stack : behavior.dispense(source, stack);
            }
        };
        event.enqueueWork(() -> items().forEach(item -> DispenserBlock.registerBehavior(item, delegate)));
    }
}
