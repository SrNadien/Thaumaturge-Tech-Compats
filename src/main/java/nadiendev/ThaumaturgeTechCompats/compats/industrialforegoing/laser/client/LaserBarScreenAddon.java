package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client;

import com.hrznstudio.titanium.client.screen.addon.ProgressBarScreenAddon;
import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.text.DecimalFormat;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LaserBarScreenAddon<T extends IComponentHarness> extends ProgressBarScreenAddon<T> {
    private static final DecimalFormat FORMAT = new DecimalFormat("#,##0");

    private final ProgressBarComponent<T> bar;

    public LaserBarScreenAddon(int x, int y, ProgressBarComponent<T> bar) {
        super(x, y, bar);
        this.bar = bar;
    }

    @Override
    public List<Component> getTooltipLines() {
        return List.of(Component.literal(ChatFormatting.GOLD
                + Component.translatable("tooltip.titanium.progressbar.progress").getString()
                + ChatFormatting.WHITE + FORMAT.format(bar.getProgress())
                + ChatFormatting.GOLD + "/"
                + ChatFormatting.WHITE + FORMAT.format(bar.getMaxProgress())));
    }
}
