package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.stack;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedConfig;
import net.minecraft.resources.Identifier;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeItem;

import javax.annotation.Nullable;

public final class UpgradeLimits implements IUpgradeCountLimitConfig {
    public static final UpgradeLimits INSTANCE = new UpgradeLimits();

    private UpgradeLimits() {
    }

    @Override
    public int getMaxUpgradesPerStorage(String storageType, @Nullable Identifier upgradeRegistryName) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxUpgradesInGroupPerStorage(String storageType, UpgradeGroup upgradeGroup) {
        if (StackUpgradeItem.UPGRADE_GROUP.name().equals(upgradeGroup.name())) {
            return SophisticatedConfig.stackUpgradesPerStorage();
        }
        return Integer.MAX_VALUE;
    }
}
