package nadiendev.ThaumaturgeTechCompats.compats.enderio.mixin;

import com.enderio.enderio.content.machines.capacitor_bank.CapacitorTier;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = CapacitorTier.class, remap = false)
public interface CapacitorTierInvoker {
    @Invoker("<init>")
    static CapacitorTier thaumaturgetechcompats$create(String name, int ordinal, ModConfigSpec.IntValue capacity) {
        throw new AssertionError();
    }
}
