package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.voidminersremastered.util.ColorUtil;
import nadiendev.voidminersremastered.world.block.SolarControllerBlock;
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

public class MagicalSolarBlock extends SolarControllerBlock implements MagicalEntityFactory {
    private final VoidMinersMagicalTier tier;

    public MagicalSolarBlock(Properties properties, VoidMinersMagicalTier tier) {
        super(properties, VoidMinersMagicalStructures.solarStructure(tier), tier.id(), VoidMinersMagicalStructures.color(tier));
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
        return new MagicalSolarBlockEntity(pos, state);
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
        tooltip.add(Component.translatable(VoidMinersMagicalTranslations.HOVER_SOLAR_COST,
                VoidMinersMagicalConfig.solarUnits(tier), VoidMinersMagicalConfig.solarInterval()).withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable(VoidMinersMagicalTranslations.HOVER_SOLAR_GENERATION,
                VoidMinersMagicalConfig.solarGeneration(tier)).withStyle(ChatFormatting.GRAY));
    }
}
