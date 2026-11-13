package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank;

import com.enderio.enderio.content.machines.capacitor_bank.CapacitorTier;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Locale;

public final class CapacitorBankRegistry {
    private CapacitorBankRegistry() {
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (EnderIOTier tier : EnderIOTier.values()) {
            CapacitorTier bank = find(tier);
            if (bank == null) {
                continue;
            }
            BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath("enderio", bank.name().toLowerCase(Locale.ROOT) + "_capacitor_bank"))
                    .ifPresent(item -> output.accept(item.value()));
        }
    }

    private static CapacitorTier find(EnderIOTier tier) {
        for (CapacitorTier candidate : CapacitorTier.values()) {
            if (candidate.name().equals(tier.name())) {
                return candidate;
            }
        }
        return null;
    }
}
