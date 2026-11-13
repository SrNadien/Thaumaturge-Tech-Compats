package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank;

import com.enderio.enderio.content.machines.capacitor_bank.CapacitorBankItem;
import com.enderio.enderio.content.machines.capacitor_bank.CapacitorTier;
import com.enderio.enderio.init.EIOBlocks;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredItem;

public final class CapacitorBankRegistry {
    private CapacitorBankRegistry() {
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (EnderIOTier tier : EnderIOTier.values()) {
            CapacitorTier bank = find(tier);
            if (bank == null) {
                continue;
            }
            DeferredItem<CapacitorBankItem> item = EIOBlocks.CAPACITOR_BANK_ITEMS.get(bank);
            if (item != null) {
                output.accept(item.get());
            }
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
