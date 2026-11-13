package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.voidminersremastered.util.ColorUtil;
import nadiendev.voidminersremastered.world.block.MinerControllerBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MagicalMinerBlock extends MinerControllerBlock implements MagicalEntityFactory {
    private final VoidMinersMagicalTier tier;

    public MagicalMinerBlock(Properties properties, VoidMinersMagicalTier tier) {
        super(properties, VoidMinersMagicalStructures.minerStructure(tier), tier.id(), VoidMinersMagicalStructures.color(tier));
        this.tier = tier;
    }

    public VoidMinersMagicalTier tier() {
        return tier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return createMagicalEntity(pos, state);
    }

    @Override
    public BlockEntity createMagicalEntity(BlockPos pos, BlockState state) {
        return new MagicalMinerBlockEntity(pos, state);
    }

    @Override
    public MutableComponent getName() {
        return Component.literal(Component.translatable(getDescriptionId()).getString()).withColor(VoidMinersMagicalConfig.textRgb(tier));
    }

    @Override
    public ColorUtil getColor() {
        return VoidMinersMagicalStructures.color(tier);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(VoidMinersMagicalTranslations.HOVER_MINER_COST,
                VoidMinersMagicalConfig.minerUnits(tier), VoidMinersMagicalConfig.minerInterval()).withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable(VoidMinersMagicalTranslations.HOVER_MINER_RATES,
                VoidMinersMagicalConfig.minerDuration(tier), VoidMinersMagicalConfig.minerSpeed(tier), VoidMinersMagicalConfig.minerProduction(tier)).withStyle(ChatFormatting.GRAY));
    }
}
