package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public interface MagicalTooltipBlock {
    void appendTooltip(Consumer<Component> tooltip);
}
