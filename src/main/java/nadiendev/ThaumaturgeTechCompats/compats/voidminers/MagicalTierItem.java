package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class MagicalTierItem extends BlockItem {
    private final VoidMinersMagicalTier tier;

    public MagicalTierItem(Block block, Properties properties, VoidMinersMagicalTier tier) {
        super(block, properties);
        this.tier = tier;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(super.getName(stack).getString()).withColor(VoidMinersMagicalConfig.textRgb(tier));
    }
}
