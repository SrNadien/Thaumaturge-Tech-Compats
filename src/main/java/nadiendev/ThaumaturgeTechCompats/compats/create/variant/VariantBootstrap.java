package nadiendev.ThaumaturgeTechCompats.compats.create.variant;

import com.simibubi.create.AllBlockEntityTypes;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.arm.VariantArmTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

public final class VariantBootstrap {
    private VariantBootstrap() {
    }

    public static void register(IEventBus modBus) {
        CreateVariants.BLOCKS.register(modBus);
        CreateVariants.ITEMS.register(modBus);
        VariantArmTypes.TYPES.register(modBus);
        modBus.addListener(VariantBootstrap::addBlocks);
        modBus.addListener(VariantBootstrap::setup);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!CreateToggles.enabled(CreateToggles.VARIANTS)) {
            return;
        }
        for (VariantKind kind : VariantKind.values()) {
            for (CasingTier tier : CasingTier.values()) {
                if (CreateToggles.variant(kind, tier)) {
                    output.accept(CreateVariants.item(kind, tier).get());
                }
            }
        }
    }

    private static void addBlocks(BlockEntityTypeAddBlocksEvent event) {
        for (VariantKind kind : VariantKind.values()) {
            event.modify(entityType(kind), CreateVariants.blocks(kind));
        }
    }

    private static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(VariantCompat::apply);
    }

    private static BlockEntityType<?> entityType(VariantKind kind) {
        return switch (kind) {
            case PRESS -> AllBlockEntityTypes.MECHANICAL_PRESS.get();
            case MIXER -> AllBlockEntityTypes.MECHANICAL_MIXER.get();
            case SAW -> AllBlockEntityTypes.SAW.get();
            case DRILL -> AllBlockEntityTypes.DRILL.get();
            case DEPLOYER -> AllBlockEntityTypes.DEPLOYER.get();
            case MILLSTONE -> AllBlockEntityTypes.MILLSTONE.get();
            case BASIN -> AllBlockEntityTypes.BASIN.get();
            case SPOUT -> AllBlockEntityTypes.SPOUT.get();
            case ITEM_DRAIN -> AllBlockEntityTypes.ITEM_DRAIN.get();
            case DEPOT -> AllBlockEntityTypes.DEPOT.get();
        };
    }
}
