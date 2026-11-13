package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client.VisBarScreenAddon;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class VisStorageComponent<T extends IComponentHarness> extends EnergyStorageComponent<T> {
    public VisStorageComponent(int capacity, int x, int y) {
        super(capacity, 0, capacity, x, y);
    }

    public int room() {
        return getMaxEnergyStored() - getEnergyStored();
    }

    public int fill(int units) {
        int accepted = Math.max(0, Math.min(units, room()));
        if (accepted > 0) {
            setEnergyStored(getEnergyStored() + accepted);
        }
        return accepted;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
        IFactory<IScreenAddon> factory = () -> new VisBarScreenAddon(getX(), getY(), this);
        return List.of(factory);
    }
}
