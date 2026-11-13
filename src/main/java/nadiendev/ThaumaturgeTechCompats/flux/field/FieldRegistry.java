package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class FieldRegistry {
    public static final String THRUSTER_NAME = "flight_thruster";
    public static final ResourceLocation THRUSTER_LAYER = ResourceLocation.fromNamespaceAndPath("thaumaturge", "thaumostatic_harness");

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<FluxTier, DeferredBlock<ShieldGeneratorBlock>> SHIELDS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, DeferredItem<FieldBlockItem>> SHIELD_ITEMS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, DeferredBlock<TerraformerBlock>> TERRAFORMERS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, DeferredItem<FieldBlockItem>> TERRAFORMER_ITEMS = new EnumMap<>(FluxTier.class);

    static {
        for (FluxTier tier : FluxTier.values()) {
            DeferredBlock<ShieldGeneratorBlock> shield = BLOCKS.registerBlock(shieldName(tier), properties -> new ShieldGeneratorBlock(properties, tier), machine(tier));
            SHIELDS.put(tier, shield);
            SHIELD_ITEMS.put(tier, ITEMS.register(shieldName(tier), () -> new FieldBlockItem(shield.get(), new Item.Properties().rarity(tier.rarity()))));
            DeferredBlock<TerraformerBlock> terraformer = BLOCKS.registerBlock(terraformerName(tier), properties -> new TerraformerBlock(properties, tier), machine(tier));
            TERRAFORMERS.put(tier, terraformer);
            TERRAFORMER_ITEMS.put(tier, ITEMS.register(terraformerName(tier), () -> new FieldBlockItem(terraformer.get(), new Item.Properties().rarity(tier.rarity()))));
        }
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShieldGeneratorBlockEntity>> SHIELD_ENTITY = BLOCK_ENTITIES.register("shield_generator",
            () -> BlockEntityType.Builder.of(ShieldGeneratorBlockEntity::new, shieldBlocks().toArray(Block[]::new)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TerraformerBlockEntity>> TERRAFORMER_ENTITY = BLOCK_ENTITIES.register("arcane_terraformer",
            () -> BlockEntityType.Builder.of(TerraformerBlockEntity::new, terraformerBlocks().toArray(Block[]::new)).build(null));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> THRUSTER_MATERIAL = ARMOR_MATERIALS.register(THRUSTER_NAME,
            () -> new ArmorMaterial(
                    Map.of(ArmorItem.Type.CHESTPLATE, 0),
                    0,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    () -> Ingredient.EMPTY,
                    List.of(new ArmorMaterial.Layer(THRUSTER_LAYER)),
                    0.0F,
                    0.0F));

    public static final DeferredItem<FlightThrusterItem> THRUSTER = ITEMS.register(THRUSTER_NAME,
            () -> new FlightThrusterItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    private FieldRegistry() {
    }

    private static BlockBehaviour.Properties machine(FluxTier tier) {
        return BlockBehaviour.Properties.of()
                .mapColor(tier == FluxTier.ALCHEMICAL_BRASS ? MapColor.GOLD : MapColor.COLOR_PURPLE)
                .strength(4.0F + tier.ordinal(), 15.0F * (tier.ordinal() + 1))
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops();
    }

    public static String shieldName(FluxTier tier) {
        return tier.id() + "_shield_generator";
    }

    public static String terraformerName(FluxTier tier) {
        return tier.id() + "_arcane_terraformer";
    }

    public static DeferredBlock<ShieldGeneratorBlock> shield(FluxTier tier) {
        return SHIELDS.get(tier);
    }

    public static DeferredItem<FieldBlockItem> shieldItem(FluxTier tier) {
        return SHIELD_ITEMS.get(tier);
    }

    public static DeferredBlock<TerraformerBlock> terraformer(FluxTier tier) {
        return TERRAFORMERS.get(tier);
    }

    public static DeferredItem<FieldBlockItem> terraformerItem(FluxTier tier) {
        return TERRAFORMER_ITEMS.get(tier);
    }

    public static List<Block> shieldBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            blocks.add(SHIELDS.get(tier).get());
        }
        return blocks;
    }

    public static List<Block> terraformerBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            blocks.add(TERRAFORMERS.get(tier).get());
        }
        return blocks;
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>(shieldBlocks());
        blocks.addAll(terraformerBlocks());
        return blocks;
    }
}
