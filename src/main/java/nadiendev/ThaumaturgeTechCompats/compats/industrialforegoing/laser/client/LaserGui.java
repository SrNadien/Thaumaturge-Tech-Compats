package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client;

import com.hrznstudio.titanium.Titanium;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.client.screen.addon.TextScreenAddon;
import com.hrznstudio.titanium.client.screen.addon.WidgetScreenAddon;
import com.hrznstudio.titanium.network.locator.instance.TileEntityLocatorInstance;
import com.hrznstudio.titanium.network.messages.ButtonClickNetworkMessage;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.LaserDepthMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class LaserGui {
    private static final String TARGET_KEY = "text.industrialforegoing.target";
    private static final String TARGET_MISSING_KEY = "text.industrialforegoing.target_not_found";
    private static final String DEPTH_LABEL_KEY = "text.industrialforegoing.depth";

    private LaserGui() {
    }

    public static IScreenAddon targetLabel() {
        return new TextScreenAddon(ChatFormatting.DARK_GRAY + Component.translatable(TARGET_KEY).getString(), 44, 26, false);
    }

    public static IScreenAddon targetValue(Supplier<BlockPos> target) {
        return new TextScreenAddon("", 44, 36, false) {
            @Override
            public String getText() {
                BlockPos pos = target.get();
                if (pos == null || pos.equals(BlockPos.ZERO)) {
                    return ChatFormatting.DARK_GRAY + Component.translatable(TARGET_MISSING_KEY).getString();
                }
                return ChatFormatting.DARK_GRAY.toString() + pos.getX() + " " + pos.getY() + " " + pos.getZ();
            }
        };
    }

    public static IScreenAddon depthLabel() {
        return new TextScreenAddon("", 70, 87, false) {
            @Override
            public String getText() {
                return ChatFormatting.DARK_GRAY + Component.translatable(DEPTH_LABEL_KEY).getString();
            }
        };
    }

    public static IScreenAddon depthField(BlockPos machine, int depth) {
        EditBox box = new EditBox(Minecraft.getInstance().font, 80, 26, 40, 12, Component.literal(Integer.toString(depth)));
        box.setValue(Integer.toString(depth));
        box.setFilter(LaserGui::validDepth);
        box.setResponder(value -> sendDepth(machine, value));
        return new WidgetScreenAddon(102, 85, box);
    }

    private static boolean validDepth(String value) {
        if (value.isEmpty() || value.equals("-")) {
            return true;
        }
        try {
            Integer.decode(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private static void sendDepth(BlockPos machine, String value) {
        if (value.isEmpty() || value.equals("-")) {
            return;
        }
        CompoundTag tag = new CompoundTag();
        tag.putInt(LaserDepthMessage.KEY, Integer.decode(value));
        Titanium.NETWORK.sendToServer(new ButtonClickNetworkMessage(new TileEntityLocatorInstance(machine), LaserDepthMessage.ID, tag));
    }
}
