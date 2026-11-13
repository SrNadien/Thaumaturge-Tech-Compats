package nadiendev.ThaumaturgeTechCompats.planetary.salis;

import com.leclowndu93150.thaumaturge.registry.TTItems;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelReader;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import java.util.List;

public class SalisVariantItem extends Item {
    public static final String TOOLTIP_VARIANT = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".salis_variant";
    public static final String TOOLTIP_SAME = TOOLTIP_VARIANT + ".same";

    private final PlanetaryTier tier;

    public SalisVariantItem(PlanetaryTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    public PlanetaryTier tier() {
        return tier;
    }

    public static void allowUseOnBlock(UseItemOnBlockEvent event) {
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.BLOCK) {
            return;
        }
        if (!(event.getItemStack().getItem() instanceof SalisVariantItem)) {
            return;
        }
        Player player = event.getPlayer();
        if (player == null || !player.isCrouching()) {
            return;
        }
        event.cancelWithResult(ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return TTItems.SALIS_MUNDUS.get().useOn(context);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.empty().append(Component.translatable(getDescriptionId(stack)).withStyle(Style.EMPTY.withColor(tier.color())));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        Component material = Component.translatable(TOOLTIP_VARIANT + "." + tier.id()).withStyle(Style.EMPTY.withColor(tier.color()));
        tooltip.add(Component.translatable(TOOLTIP_VARIANT, material).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(TOOLTIP_SAME).withStyle(ChatFormatting.DARK_GRAY));
    }
}
