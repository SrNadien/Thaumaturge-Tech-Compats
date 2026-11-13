package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;

public class IndustrialForegoingAddonItem extends Item {
    private final AddonKind kind;
    private final AddonTier tier;

    public IndustrialForegoingAddonItem(AddonKind kind, AddonTier tier, Properties properties) {
        super(properties);
        this.kind = kind;
        this.tier = tier;
    }

    public AddonKind kind() {
        return kind;
    }

    public AddonTier tier() {
        return tier;
    }

    public static String tooltipKey(AddonKind kind) {
        return "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".industrialforegoing_addon." + kind.id();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return super.useOn(context);
        }
        ItemStack held = context.getItemInHand();
        ItemStack single = held.copyWithCount(1);
        if (TitaniumMachineBridge.install(context.getLevel().getBlockEntity(context.getClickedPos()), single)) {
            held.shrink(1);
            return InteractionResult.CONSUME_PARTIAL;
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        float value = IndustrialForegoingAddonsConfig.value(kind, tier);
        tooltip.add(Component.translatable(tooltipKey(kind), format(value)).withStyle(ChatFormatting.GRAY));
    }

    private String format(float value) {
        return switch (kind) {
            case SPEED -> String.valueOf(Math.round((value - 1.0F) * 100.0F));
            case EFFICIENCY -> String.valueOf(Math.round((1.0F - value) * 100.0F));
            case PROCESSING -> String.valueOf((int) value);
            case RANGE -> String.valueOf((int) value + 1);
        };
    }
}
