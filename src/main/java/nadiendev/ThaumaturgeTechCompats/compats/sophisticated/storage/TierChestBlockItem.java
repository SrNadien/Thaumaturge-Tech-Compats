package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.p3pp3rf1y.sophisticatedstorage.item.ChestBlockItem;

public class TierChestBlockItem extends ChestBlockItem {
    private final SophisticatedTier tier;

    public TierChestBlockItem(SophisticatedTier tier, Block block, Properties properties) {
        super(block, properties);
        this.tier = tier;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(style -> style.withColor(tier.nameColor()));
    }
}
