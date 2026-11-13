package nadiendev.ThaumaturgeTechCompats.infusion;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.EnumMap;
import java.util.Map;

public final class InfusionTiers {
    public static final String TAG_FOLDER = "avaritia_tier/";

    private static final Map<MatrixVariant, TagKey<Item>> TAGS = new EnumMap<>(MatrixVariant.class);

    static {
        for (MatrixVariant variant : MatrixVariant.values()) {
            TAGS.put(variant, TagKey.create(Registries.ITEM, ThaumaturgeTechCompats.id(TAG_FOLDER + variant.id())));
        }
    }

    private InfusionTiers() {
    }

    public static TagKey<Item> tag(MatrixVariant variant) {
        return TAGS.get(variant);
    }

    public static MatrixVariant variantOf(Item item) {
        MatrixVariant[] variants = MatrixVariant.values();
        Holder<Item> holder = BuiltInRegistries.ITEM.wrapAsHolder(item);
        for (int i = variants.length - 1; i >= 0; i--) {
            if (holder.is(TAGS.get(variants[i]))) {
                return variants[i];
            }
        }
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
        if (!key.getNamespace().equals(ThaumaturgeTechCompats.MOD_ID)) {
            return null;
        }
        String path = key.getPath();
        for (MatrixVariant variant : variants) {
            if (path.startsWith(variant.id() + "_")) {
                return variant;
            }
        }
        return null;
    }

    public static Block requiredMatrix(Item item) {
        if (InfusionRegistry.resultVariant(item) != null) {
            return null;
        }
        MatrixVariant tier = variantOf(item);
        if (tier == null || !InfusionConfig.tierRuleEnabled(tier)) {
            return null;
        }
        return InfusionRegistry.ownMatrix(tier);
    }
}
