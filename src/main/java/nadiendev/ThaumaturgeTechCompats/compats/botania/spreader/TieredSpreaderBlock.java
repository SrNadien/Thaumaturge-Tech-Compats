package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader;

import net.minecraft.world.level.block.state.BlockBehaviour;
import velrondevs.botania.common.block.mana.ManaSpreaderBlock;

public class TieredSpreaderBlock extends ManaSpreaderBlock implements SpreaderStats {
    private final SpreaderTier tier;

    public TieredSpreaderBlock(SpreaderTier tier, BlockBehaviour.Properties properties) {
        super(ManaSpreaderBlock.Variant.GAIA, properties);
        this.tier = tier;
    }

    public SpreaderTier tier() {
        return tier;
    }

    @Override
    public int burstMana() {
        return BotaniaSpreaderConfig.burstMana(tier);
    }

    @Override
    public int manaCapacity() {
        return BotaniaSpreaderConfig.manaCapacity(tier);
    }

    @Override
    public int preLossTicks() {
        return BotaniaSpreaderConfig.preLossTicks(tier);
    }

    @Override
    public float lossPerTick() {
        return BotaniaSpreaderConfig.lossPerTick(tier);
    }

    @Override
    public float motionModifier() {
        return BotaniaSpreaderConfig.motionModifier(tier);
    }

    @Override
    public int burstColor() {
        return BotaniaSpreaderConfig.burstColor(tier);
    }
}
