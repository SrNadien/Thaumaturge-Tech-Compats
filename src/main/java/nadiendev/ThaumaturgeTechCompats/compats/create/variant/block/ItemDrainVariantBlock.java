package nadiendev.ThaumaturgeTechCompats.compats.create.variant.block;

import com.simibubi.create.content.fluids.drain.ItemDrainBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CasingVariant;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;

public class ItemDrainVariantBlock extends ItemDrainBlock implements CasingVariant {
    private final CasingTier tier;

    public ItemDrainVariantBlock(Properties properties, CasingTier tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public CasingTier tier() {
        return tier;
    }

    @Override
    public VariantKind kind() {
        return VariantKind.ITEM_DRAIN;
    }
}
