package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class ForceCraftRepair {
    public static final String KEY = ThaumaturgeTechCompats.MOD_ID + "_force_repair";
    public static final String TOOLTIP_KEY = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".forcecraft_repair";

    private static final float EPSILON = 1.0E-4F;

    private ForceCraftRepair() {
    }

    public static boolean isMarked(ItemStack stack) {
        return !stack.isEmpty() && stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).contains(KEY);
    }

    public static void mark(ItemStack stack) {
        if (!stack.isEmpty()) {
            stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, data -> data.update(tag -> tag.putBoolean(KEY, true)));
        }
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || player.isSpectator() || !ForceCraftConfig.repairEnabled()) {
            return;
        }
        if (player.tickCount % ForceCraftConfig.repairInterval() != 0) {
            return;
        }
        pulse(player);
    }

    public static void onTooltip(ItemTooltipEvent event) {
        if (ForceCraftConfig.repairTooltip() && isMarked(event.getItemStack())) {
            event.getToolTip().add(Component.translatable(TOOLTIP_KEY).withStyle(ChatFormatting.AQUA));
        }
    }

    private static void pulse(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        BlockPos pos = player.blockPosition();
        float cost = ForceCraftConfig.repairVisPerPoint();
        if (cost > 0.0F && ForceCraftConfig.repairRespectPreservation() && AuraHelper.shouldPreserveAura(level, player, pos)) {
            return;
        }
        if (ForceCraftConfig.repairWholeInventory()) {
            Inventory inventory = player.getInventory();
            for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
                if (!repair(level, pos, inventory.getItem(slot), cost)) {
                    return;
                }
            }
            return;
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!repair(level, pos, player.getItemBySlot(slot), cost)) {
                return;
            }
        }
    }

    private static boolean repair(ServerLevel level, BlockPos pos, ItemStack stack, float cost) {
        if (!stack.isDamaged() || !isMarked(stack)) {
            return true;
        }
        int points = Math.min(stack.getDamageValue(), ForceCraftConfig.repairPoints());
        if (cost > 0.0F) {
            float available = AuraHelper.drainVis(level, pos, points * cost, true);
            points = Math.min(points, (int) ((available + EPSILON) / cost));
            if (points <= 0) {
                return false;
            }
            AuraHelper.drainVis(level, pos, points * cost, false);
        }
        stack.setDamageValue(stack.getDamageValue() - points);
        return true;
    }
}
