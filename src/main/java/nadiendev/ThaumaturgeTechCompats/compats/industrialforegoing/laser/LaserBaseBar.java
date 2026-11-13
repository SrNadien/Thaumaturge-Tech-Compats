package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client.LaserBarScreenAddon;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class LaserBaseBar<T extends IComponentHarness> extends ProgressBarComponent<T> {
    public LaserBaseBar(int x, int y, int maxProgress) {
        super(x, y, 0, maxProgress);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
        IFactory<IScreenAddon> factory = () -> new LaserBarScreenAddon<T>(getPosX(), getPosY(), this);
        return List.of(factory);
    }
}
