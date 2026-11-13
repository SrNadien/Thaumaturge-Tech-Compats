package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import committee.nova.mods.avaritia.api.common.wrapper.ItemStackWrapper;
import committee.nova.mods.avaritia.common.block.collector.NeutronCollectorBlock;
import committee.nova.mods.avaritia.common.tile.NeutronCollectorTile;
import committee.nova.mods.avaritia.init.registry.ModTileEntities;
import committee.nova.mods.avaritia.init.registry.enums.CollectorTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TieredCollectorBlock extends NeutronCollectorBlock {
    private static final CollectorTier BASE = CollectorTier.DENSEST;

    private final AvaritiaMachineTier machineTier;

    public TieredCollectorBlock(AvaritiaMachineTier machineTier) {
        super();
        this.machineTier = machineTier;
    }

    public AvaritiaMachineTier machineTier() {
        return machineTier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        NeutronCollectorTile tile = new NeutronCollectorTile(pos, state);
        tile.setTier(BASE);
        return tile;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof NeutronCollectorTile tile) {
            ensureTier(tile);
            player.openMenu(new SimpleMenuProvider((id, inventory, user) -> tile.createMenu(id, inventory), getName()), pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(type, ModTileEntities.neutron_collector_tile.get(), this::tickMachine);
    }

    private void tickMachine(Level level, BlockPos pos, BlockState state, NeutronCollectorTile tile) {
        ensureTier(tile);
        int speed = AvaritiaMachinesConfig.collectorSpeed(machineTier);
        int extra = AvaritiaMachinesConfig.collectorYield(machineTier) - 1;
        ItemStackWrapper inventory = tile.getInventory();
        for (int i = 0; i < speed; i++) {
            NeutronCollectorTile.serverTick(level, pos, state, tile);
            if (tile.data.get(0) >= tile.getProductionTicks()) {
                tile.data.set(0, 0);
                ItemStack output = inventory.getStackInSlot(0);
                int room = output.getMaxStackSize() - output.getCount();
                if (extra > 0 && !output.isEmpty() && room > 0) {
                    inventory.setStackInSlot(0, output.copyWithCount(output.getCount() + Math.min(extra, room)));
                }
            }
        }
    }

    private static void ensureTier(NeutronCollectorTile tile) {
        if (tile.getTier() == null) {
            tile.setTier(BASE);
        }
    }
}
