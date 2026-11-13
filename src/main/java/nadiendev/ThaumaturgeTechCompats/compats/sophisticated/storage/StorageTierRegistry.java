package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage;

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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedstorage.block.BarrelBlock;
import net.p3pp3rf1y.sophisticatedstorage.block.LimitedBarrelBlock;
import net.p3pp3rf1y.sophisticatedstorage.block.ITintableBlockItem;
import net.p3pp3rf1y.sophisticatedstorage.block.WoodStorageBlockBase;
import net.p3pp3rf1y.sophisticatedstorage.item.BarrelBlockItem;
import net.p3pp3rf1y.sophisticatedstorage.item.WoodStorageBlockItem;
import net.p3pp3rf1y.sophisticatedstorage.util.GenericWoodStorageHelper;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class StorageTierRegistry {
    public static final String STORAGE_MOD = "sophisticatedstorage";
    public static final float EXPLOSION_RESISTANCE = 1200.0F;

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<SophisticatedTier, DeferredBlock<BarrelBlock>> BARRELS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, List<DeferredBlock<LimitedBarrelBlock>>> LIMITED_BARRELS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, DeferredBlock<TierChestBlock>> CHESTS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, DeferredBlock<TierShulkerBoxBlock>> SHULKER_BOXES = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, DeferredItem<? extends Item>> BARREL_BY_TIER = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, List<DeferredItem<? extends Item>>> LIMITED_BY_TIER = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, DeferredItem<? extends Item>> CHEST_BY_TIER = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, DeferredItem<? extends Item>> SHULKER_BY_TIER = new EnumMap<>(SophisticatedTier.class);
    private static final List<DeferredItem<? extends Item>> BARREL_ITEMS = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> CHEST_ITEMS = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> SHULKER_ITEMS = new ArrayList<>();
    private static final List<DeferredBlock<? extends Block>> ALL_BLOCKS = new ArrayList<>();

    static {
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            DeferredBlock<BarrelBlock> barrel = BLOCKS.register(tier.barrelName(),
                    () -> new BarrelBlock(SophisticatedConfig.barrelSlots(tier), SophisticatedConfig.barrelUpgrades(tier), EXPLOSION_RESISTANCE));
            BARRELS.put(tier, barrel);
            ALL_BLOCKS.add(barrel);
            DeferredItem<? extends Item> barrelItem = ITEMS.registerItem(tier.barrelName(), properties -> new TierBarrelBlockItem(tier, barrel.get(), properties.useBlockDescriptionPrefix()), () -> properties(tier));
            BARREL_BY_TIER.put(tier, barrelItem);
            BARREL_ITEMS.add(barrelItem);

            List<DeferredBlock<LimitedBarrelBlock>> limited = new ArrayList<>();
            List<DeferredItem<? extends Item>> limitedItems = new ArrayList<>();
            for (int size = 1; size <= SophisticatedConfig.LIMITED_BARRELS; size++) {
                int slots = size;
                DeferredBlock<LimitedBarrelBlock> block = BLOCKS.register(tier.limitedBarrelName(size), () -> new LimitedBarrelBlock(slots,
                        SophisticatedConfig.limitedBarrelMultiplier(tier, slots), SophisticatedConfig.limitedBarrelUpgrades(tier, slots), EXPLOSION_RESISTANCE));
                limited.add(block);
                ALL_BLOCKS.add(block);
                DeferredItem<? extends Item> limitedItem = ITEMS.registerItem(tier.limitedBarrelName(size), properties -> new TierBarrelBlockItem(tier, block.get(), properties.useBlockDescriptionPrefix()), () -> properties(tier));
                limitedItems.add(limitedItem);
                BARREL_ITEMS.add(limitedItem);
            }
            LIMITED_BARRELS.put(tier, limited);
            LIMITED_BY_TIER.put(tier, limitedItems);

            DeferredBlock<TierChestBlock> chest = BLOCKS.register(tier.chestName(),
                    () -> new TierChestBlock(tier, SophisticatedConfig.chestSlots(tier), SophisticatedConfig.chestUpgrades(tier), EXPLOSION_RESISTANCE));
            CHESTS.put(tier, chest);
            ALL_BLOCKS.add(chest);
            DeferredItem<? extends Item> chestItem = ITEMS.registerItem(tier.chestName(), properties -> new TierChestBlockItem(tier, chest.get(), properties.useBlockDescriptionPrefix()), () -> properties(tier));
            CHEST_BY_TIER.put(tier, chestItem);
            CHEST_ITEMS.add(chestItem);

            DeferredBlock<TierShulkerBoxBlock> shulker = BLOCKS.register(tier.shulkerBoxName(), () -> new TierShulkerBoxBlock(tier,
                    SophisticatedConfig.shulkerBoxSlots(tier), SophisticatedConfig.shulkerBoxUpgrades(tier), EXPLOSION_RESISTANCE));
            SHULKER_BOXES.put(tier, shulker);
            ALL_BLOCKS.add(shulker);
            DeferredItem<? extends Item> shulkerItem = ITEMS.registerItem(tier.shulkerBoxName(), properties -> new TierShulkerBoxItem(tier, shulker.get(), properties.useBlockDescriptionPrefix().stacksTo(1)), () -> properties(tier));
            SHULKER_BY_TIER.put(tier, shulkerItem);
            SHULKER_ITEMS.add(shulkerItem);
        }
    }

    private StorageTierRegistry() {
    }

    private static Item.Properties properties(SophisticatedTier tier) {
        return new Item.Properties().fireResistant().rarity(tier.rarity());
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(StorageTierRegistry::addBlockEntityBlocks);
        modBus.addListener(StorageTierRegistry::commonSetup);
        if (FMLEnvironment.getDist().isClient()) {
            StorageTierClient.register(modBus);
        }
    }

    public static List<Block> barrelBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            blocks.add(BARRELS.get(tier).get());
        }
        return blocks;
    }

    public static List<Block> limitedBarrelBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            LIMITED_BARRELS.get(tier).forEach(block -> blocks.add(block.get()));
        }
        return blocks;
    }

    public static List<Block> chestBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            blocks.add(CHESTS.get(tier).get());
        }
        return blocks;
    }

    public static List<Block> shulkerBoxBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            blocks.add(SHULKER_BOXES.get(tier).get());
        }
        return blocks;
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        ALL_BLOCKS.forEach(block -> blocks.add(block.get()));
        return blocks;
    }

    public static List<Item> barrelItems() {
        return resolve(BARREL_ITEMS);
    }

    public static List<Item> chestItems() {
        return resolve(CHEST_ITEMS);
    }

    public static List<Item> shulkerBoxItems() {
        return resolve(SHULKER_ITEMS);
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>(barrelItems());
        items.addAll(chestItems());
        items.addAll(shulkerBoxItems());
        return items;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        List<WoodType> woods = woodTypes();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            addWoodVariants(output, BARREL_BY_TIER.get(tier).get(), woods);
        }
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            ItemStack flat = WoodStorageBlockItem.setWoodType(new ItemStack(BARREL_BY_TIER.get(tier).get()), WoodType.ACACIA);
            BarrelBlockItem.toggleFlatTop(flat);
            output.accept(flat);
        }
        for (int size = 0; size < SophisticatedConfig.LIMITED_BARRELS; size++) {
            for (SophisticatedTier tier : SophisticatedTier.values()) {
                addWoodVariants(output, LIMITED_BY_TIER.get(tier).get(size).get(), woods);
            }
        }
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            addWoodVariants(output, CHEST_BY_TIER.get(tier).get(), woods);
        }
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            addShulkerBoxVariants(output, SHULKER_BOXES.get(tier).get(), SHULKER_BY_TIER.get(tier).get());
        }
    }

    private static List<WoodType> woodTypes() {
        if (!SophisticatedConfig.creativeAllWoodVariants()) {
            return List.of(WoodType.ACACIA);
        }
        List<WoodType> woods = new ArrayList<>(WoodStorageBlockBase.CUSTOM_TEXTURE_WOOD_TYPES.keySet());
        woods.addAll(GenericWoodStorageHelper.getEnabledGenericWoodTypes());
        return woods;
    }

    private static void addWoodVariants(CreativeModeTab.Output output, Item item, List<WoodType> woods) {
        for (WoodType wood : woods) {
            output.accept(WoodStorageBlockItem.setWoodType(new ItemStack(item), wood));
        }
    }

    private static void addShulkerBoxVariants(CreativeModeTab.Output output, TierShulkerBoxBlock block, Item item) {
        output.accept(new ItemStack(item));
        for (DyeColor color : DyeColor.values()) {
            output.accept(block.getTintedStack(color));
        }
        ItemStack twoTone = new ItemStack(item);
        if (twoTone.getItem() instanceof ITintableBlockItem tintable) {
            tintable.setMainColor(twoTone, DyeColor.YELLOW.getTextureDiffuseColor());
            tintable.setAccentColor(twoTone, DyeColor.LIME.getTextureDiffuseColor());
        }
        output.accept(twoTone);
    }

    private static List<Item> resolve(List<DeferredItem<? extends Item>> holders) {
        List<Item> items = new ArrayList<>();
        holders.forEach(holder -> items.add(holder.get()));
        return items;
    }

    private static void addBlockEntityBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(key("barrel"), barrelBlocks().toArray(new Block[0]));
        event.modify(key("limited_barrel"), limitedBarrelBlocks().toArray(new Block[0]));
        event.modify(key("chest"), chestBlocks().toArray(new Block[0]));
        event.modify(key("shulker_box"), shulkerBoxBlocks().toArray(new Block[0]));
    }

    private static ResourceKey<BlockEntityType<?>> key(String name) {
        return ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(STORAGE_MOD, name));
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        Identifier netherite = Identifier.fromNamespaceAndPath(STORAGE_MOD, "netherite_shulker_box");
        DispenseItemBehavior delegate = new DispenseItemBehavior() {
            @Override
            public ItemStack dispense(BlockSource source, ItemStack stack) {
                Item reference = BuiltInRegistries.ITEM.get(netherite);
                DispenseItemBehavior behavior = DispenserBlock.DISPENSER_REGISTRY.get(reference);
                return behavior == null || behavior == this ? stack : behavior.dispense(source, stack);
            }
        };
        event.enqueueWork(() -> shulkerBoxItems().forEach(item -> DispenserBlock.registerBehavior(item, delegate)));
    }
}
