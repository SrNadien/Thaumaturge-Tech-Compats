package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.proxy.client.IndustrialAssetProvider;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IAssetType;
import com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset;
import com.hrznstudio.titanium.api.client.assets.types.ITankAsset;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.awt.Point;
import java.awt.Rectangle;

public final class PurpleAssetProvider implements IAssetProvider {
    public static final PurpleAssetProvider INSTANCE = new PurpleAssetProvider();
    public static final ResourceLocation BACKGROUND = ThaumaturgeTechCompats.id("textures/gui/laser_machine_background.png");
    public static final ResourceLocation BACKGROUND_ALT = ThaumaturgeTechCompats.id("textures/gui/laser_machine_background_alt.png");

    private PurpleAssetProvider() {
    }

    @Override
    public <T extends IAsset> T getAsset(IAssetType<T> type) {
        IAsset original = IndustrialAssetProvider.INSTANCE.getAsset(type);
        return type.castOrDefault(paint(original));
    }

    private static IAsset paint(IAsset asset) {
        ResourceLocation location = asset.getResourceLocation();
        ResourceLocation replacement;
        if (location.equals(IAssetProvider.DEFAULT_LOCATION)) {
            replacement = BACKGROUND;
        } else if (location.equals(IAssetProvider.DEFAULT_LOCATION_ALT)) {
            replacement = BACKGROUND_ALT;
        } else {
            return asset;
        }
        if (asset instanceof IBackgroundAsset background) {
            return new PaintedBackground(background, replacement);
        }
        if (asset instanceof ITankAsset tank) {
            return new PaintedTank(tank, replacement);
        }
        return new Painted(asset, replacement);
    }

    private record Painted(IAsset source, ResourceLocation texture) implements IAsset {
        @Override
        public ResourceLocation getResourceLocation() {
            return texture;
        }

        @Override
        public Rectangle getArea() {
            return source.getArea();
        }

        @Override
        public Point getOffset() {
            return source.getOffset();
        }
    }

    private record PaintedBackground(IBackgroundAsset source, ResourceLocation texture) implements IBackgroundAsset {
        @Override
        public ResourceLocation getResourceLocation() {
            return texture;
        }

        @Override
        public Rectangle getArea() {
            return source.getArea();
        }

        @Override
        public Point getOffset() {
            return source.getOffset();
        }

        @Override
        public Point getInventoryPosition() {
            return source.getInventoryPosition();
        }

        @Override
        public Point getHotbarPosition() {
            return source.getHotbarPosition();
        }
    }

    private record PaintedTank(ITankAsset source, ResourceLocation texture) implements ITankAsset {
        @Override
        public ResourceLocation getResourceLocation() {
            return texture;
        }

        @Override
        public Rectangle getArea() {
            return source.getArea();
        }

        @Override
        public Point getOffset() {
            return source.getOffset();
        }

        @Override
        public int getFluidRenderPadding(Direction direction) {
            return source.getFluidRenderPadding(direction);
        }
    }
}
