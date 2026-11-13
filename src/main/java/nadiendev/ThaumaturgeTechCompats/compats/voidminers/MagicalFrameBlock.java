package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.voidminersremastered.util.ColorUtil;
import nadiendev.voidminersremastered.world.block.ColoredBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class MagicalFrameBlock extends ColoredBlock {
    private final VoidMinersMagicalTier tier;

    public MagicalFrameBlock(Properties properties, VoidMinersMagicalTier tier) {
        super(properties, VoidMinersMagicalStructures.color(tier));
        this.tier = tier;
    }

    public VoidMinersMagicalTier tier() {
        return tier;
    }

    @Override
    public MutableComponent getName() {
        return Component.literal(Component.translatable(getDescriptionId()).getString()).withColor(VoidMinersMagicalConfig.textRgb(tier));
    }

    @Override
    public ColorUtil getColor() {
        return VoidMinersMagicalStructures.color(tier);
    }
}
