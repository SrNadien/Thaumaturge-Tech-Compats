package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;

import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class ChargerItem extends BlockItem {
    public static final String TOOLTIP = "tooltip.thaumaturgetechcompats.environmental_charger.";

    private final FluxTier tier;

    public ChargerItem(EnvironmentalChargerBlock block, Properties properties) {
        super(block, properties);
        this.tier = block.tier();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        tooltip.accept(Component.translatable(TOOLTIP + "description").withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "area", FluxConfig.chargerRadius(tier), format.format(FluxConfig.chargerRate(tier))).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "capacity", format.format(ChargerConfig.capacity(tier))).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
