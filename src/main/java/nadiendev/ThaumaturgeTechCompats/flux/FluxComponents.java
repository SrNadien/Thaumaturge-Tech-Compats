package nadiendev.ThaumaturgeTechCompats.flux;

import com.mojang.serialization.Codec;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FluxComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> STORED = COMPONENTS.register("dimensional_flux",
            () -> DataComponentType.<Long>builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build());

    private FluxComponents() {
    }

    public static long stored(ItemStack stack) {
        return Math.max(0L, stack.getOrDefault(STORED.get(), 0L));
    }

    public static void set(ItemStack stack, long value) {
        stack.set(STORED.get(), Math.max(0L, value));
    }

    public static long insert(ItemStack stack, long capacity, long amount, boolean simulate) {
        long accepted = Math.max(0L, Math.min(amount, capacity - stored(stack)));
        if (!simulate && accepted > 0L) {
            set(stack, stored(stack) + accepted);
        }
        return accepted;
    }

    public static long extract(ItemStack stack, long amount, boolean simulate) {
        long taken = Math.max(0L, Math.min(amount, stored(stack)));
        if (!simulate && taken > 0L) {
            set(stack, stored(stack) - taken);
        }
        return taken;
    }
}
