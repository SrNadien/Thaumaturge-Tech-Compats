package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;
import java.util.function.Consumer;

public class ThaumaturgeKnifeItem extends KnifeItem {
    private final FarmersDelightKnife knife;

    public ThaumaturgeKnifeItem(FarmersDelightKnife knife, Properties properties) {
        super(knife, properties);
        this.knife = knife;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int duration = FarmersDelightTaintedConfig.taintDuration(knife);
        if (!attacker.level().isClientSide() && duration > 0
                && attacker.getRandom().nextFloat() < FarmersDelightTaintedConfig.taintChance(knife)) {
            MobEffectInstance effect = FarmersDelightTaintedEffect.create(
                    FarmersDelightTaintedConfig.knifeEffectId(),
                    duration,
                    FarmersDelightTaintedConfig.knifeEffectAmplifier());
            target.addEffect(effect, attacker);
        }
        super.postHurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        if (FarmersDelightTaintedConfig.taintDuration(knife) > 0 && FarmersDelightTaintedConfig.taintChance(knife) > 0.0F) {
            tooltip.accept(Component.translatable(FarmersDelightKnife.taintTooltipKey()).withStyle(ChatFormatting.DARK_PURPLE));
        }
        super.appendHoverText(stack, context, display, tooltip, flag);
    }
}
