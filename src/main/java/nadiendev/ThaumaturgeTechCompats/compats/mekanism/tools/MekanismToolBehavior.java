package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public final class MekanismToolBehavior {
    public static final int BAR_COLOR = 0x3CFE9A;

    private MekanismToolBehavior() {
    }

    public static long stored(ItemStack stack) {
        return Math.min(stack.getOrDefault(MekanismToolRegistry.ENERGY.get(), 0L), capacity(stack));
    }

    public static long capacity(ItemStack stack) {
        return stack.getItem() instanceof MekanismEnergyTool tool ? MekanismToolConfig.capacity(tool.tier()) : 0L;
    }

    public static void store(ItemStack stack, long energy) {
        stack.set(MekanismToolRegistry.ENERGY.get(), Math.max(0L, Math.min(energy, capacity(stack))));
    }

    public static boolean hasEnergy(ItemStack stack) {
        return stored(stack) > 0L;
    }

    public static ItemStack charged(Item item) {
        ItemStack stack = new ItemStack(item);
        store(stack, capacity(stack));
        return stack;
    }

    public static void spend(ItemStack stack, long cost, Level level, LivingEntity user) {
        if (level.isClientSide() || cost <= 0L) {
            return;
        }
        if (user instanceof Player player && player.getAbilities().instabuild) {
            return;
        }
        store(stack, stored(stack) - cost);
    }

    public static float destroySpeed(ItemStack stack, float speed) {
        return hasEnergy(stack) ? speed : 1.0F;
    }

    public static int barWidth(ItemStack stack) {
        long capacity = capacity(stack);
        if (capacity <= 0L) {
            return 0;
        }
        return Math.round(13.0F * (float) ((double) stored(stack) / (double) capacity));
    }

    public static void onAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof MekanismEnergyTool && !hasEnergy(stack)) {
            event.removeAllModifiersFor(Attributes.ATTACK_DAMAGE);
        }
    }
}
