package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import committee.nova.mods.avaritia.api.common.crafting.ICompressorRecipe;
import committee.nova.mods.avaritia.api.common.wrapper.ItemStackWrapper;
import committee.nova.mods.avaritia.common.block.compressor.NeutronCompressorBlock;
import committee.nova.mods.avaritia.common.tile.NeutronCompressorTile;
import committee.nova.mods.avaritia.init.registry.ModTileEntities;
import committee.nova.mods.avaritia.init.registry.enums.CompressorTier;
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

public class TieredCompressorBlock extends NeutronCompressorBlock {
    private static final CompressorTier BASE = CompressorTier.DENSEST;

    private final AvaritiaMachineTier machineTier;

    public TieredCompressorBlock(AvaritiaMachineTier machineTier) {
        super();
        this.machineTier = machineTier;
    }

    public AvaritiaMachineTier machineTier() {
        return machineTier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        NeutronCompressorTile tile = new NeutronCompressorTile(pos, state);
        tile.setTier(BASE);
        return tile;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof NeutronCompressorTile tile) {
            ensureTier(tile);
            player.openMenu(new SimpleMenuProvider((id, inventory, user) -> tile.createMenu(id, inventory), getName()), pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(type, ModTileEntities.neutron_compressor_tile.get(), this::tickMachine);
    }

    private void tickMachine(Level level, BlockPos pos, BlockState state, NeutronCompressorTile tile) {
        ensureTier(tile);
        int speed = AvaritiaMachinesConfig.compressorSpeed(machineTier);
        int factor = AvaritiaMachinesConfig.compressorYield(machineTier) - 1;
        ItemStackWrapper inventory = tile.getInventory();
        for (int i = 0; i < speed; i++) {
            int materialsBefore = tile.getMaterialCount();
            int outputBefore = inventory.getStackInSlot(0).getCount();
            NeutronCompressorTile.tick(level, pos, state, tile);
            if (factor > 0 && (tile.getMaterialCount() < materialsBefore || inventory.getStackInSlot(0).getCount() > outputBefore)) {
                addExtraOutput(level, tile, inventory, factor);
            }
        }
    }

    private static void addExtraOutput(Level level, NeutronCompressorTile tile, ItemStackWrapper inventory, int factor) {
        ICompressorRecipe recipe = tile.getActiveRecipe();
        ItemStack output = inventory.getStackInSlot(0);
        if (recipe == null || output.isEmpty()) {
            return;
        }
        int produced = recipe.getResultItem(level.registryAccess()).getCount() * BASE.outputAmplifier;
        int room = output.getMaxStackSize() - output.getCount();
        int extra = Math.min(produced * factor, room);
        if (extra > 0) {
            inventory.setStackInSlot(0, output.copyWithCount(output.getCount() + extra));
        }
    }

    private static void ensureTier(NeutronCompressorTile tile) {
        if (tile.getTier() == null) {
            tile.setTier(BASE);
        }
    }
}
