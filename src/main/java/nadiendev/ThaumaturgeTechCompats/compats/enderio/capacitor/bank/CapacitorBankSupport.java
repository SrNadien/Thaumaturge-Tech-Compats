package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank;

import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public final class CapacitorBankSupport {
    public static final String VIBRANT_BANK = EnderIOIntegration.MOD_ID + ":vibrant_capacitor_bank";

    private static final String SUFFIX = "_capacitor_bank";

    private CapacitorBankSupport() {
    }

    public static boolean enabled() {
        return CompatToggles.enabled(CompatToggles.ENDERIO)
                && CompatToggles.enabled(EnderIOIntegration.CAPACITORS)
                && CompatToggles.enabled(EnderIOIntegration.CAPACITOR_BANKS);
    }

    public static String blockName(EnderIOTier tier) {
        return tier.id() + SUFFIX;
    }

    public static String blockId(EnderIOTier tier) {
        return EnderIOIntegration.MOD_ID + ":" + blockName(tier);
    }

    public static List<ResourceLocation> blockIds() {
        List<ResourceLocation> ids = new ArrayList<>();
        for (EnderIOTier tier : EnderIOTier.values()) {
            ids.add(ResourceLocation.fromNamespaceAndPath(EnderIOIntegration.MOD_ID, blockName(tier)));
        }
        return ids;
    }
}
