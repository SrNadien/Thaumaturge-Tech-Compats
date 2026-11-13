package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

import java.util.List;

public class ArcaneUpgradeItem extends UpgradeItemBase<ArcaneUpgradeWrapper> {
    private static final UpgradeType<ArcaneUpgradeWrapper> TYPE = new UpgradeType<>(ArcaneUpgradeWrapper::new);

    public ArcaneUpgradeItem(IUpgradeCountLimitConfig limits) {
        super(limits);
    }

    @Override
    public UpgradeType<ArcaneUpgradeWrapper> getType() {
        return TYPE;
    }

    @Override
    public List<UpgradeConflictDefinition> getUpgradeConflicts() {
        return List.of();
    }
}
