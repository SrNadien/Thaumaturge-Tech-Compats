package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class AddonBridge {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String BLOCK_TYPE = "rearth.oritech.block.blocks.addons.MachineAddonBlock";
    private static final String SETTINGS_TYPE = BLOCK_TYPE + "$AddonSettings";

    private static boolean resolved;
    private static boolean usable;
    private static Class<?> blockType;
    private static Class<?> settingsType;
    private static Constructor<?> blockConstructor;
    private static Method defaultSettings;
    private static Method withSpeed;
    private static Method withEfficiency;
    private static Method withCapacity;
    private static Method withInsert;
    private static Method withShape;

    private AddonBridge() {
    }

    public static synchronized boolean available() {
        if (!resolved) {
            resolved = true;
            if (OritechFeatures.loaded()) {
                resolve();
            }
        }
        return usable;
    }

    private static void resolve() {
        try {
            blockType = Class.forName(BLOCK_TYPE);
            settingsType = Class.forName(SETTINGS_TYPE);
            blockConstructor = blockType.getConstructor(BlockBehaviour.Properties.class, settingsType);
            defaultSettings = settingsType.getMethod("getDefaultSettings");
            withSpeed = settingsType.getMethod("withSpeedMultiplier", float.class);
            withEfficiency = settingsType.getMethod("withEfficiencyMultiplier", float.class);
            withCapacity = settingsType.getMethod("withAddedCapacity", long.class);
            withInsert = settingsType.getMethod("withAddedInsert", long.class);
            withShape = settingsType.getMethod("withBoundingShape", VoxelShape[][].class);
            for (OritechAddon addon : OritechAddon.values()) {
                blockType.getField(addon.shapeField());
            }
            usable = true;
        } catch (ReflectiveOperationException | LinkageError exception) {
            LOGGER.warn("Oritech is loaded but its machine addon API was not found, the tiered addons will not be registered", exception);
        }
    }

    public static Block create(BlockBehaviour.Properties properties, OritechTier tier, OritechAddon addon) {
        try {
            Object settings = defaultSettings.invoke(null);
            switch (addon) {
                case SPEED -> {
                    settings = withSpeed.invoke(settings, OritechConfig.speedAddonSpeed(tier));
                    settings = withEfficiency.invoke(settings, OritechConfig.speedAddonEnergy(tier));
                }
                case EFFICIENCY -> settings = withEfficiency.invoke(settings, OritechConfig.efficiencyAddonEnergy(tier));
                case CAPACITOR -> {
                    settings = withCapacity.invoke(settings, OritechConfig.capacitorCapacity(tier));
                    settings = withInsert.invoke(settings, OritechConfig.capacitorInsert(tier));
                }
            }
            Object shape = blockType.getField(addon.shapeField()).get(null);
            settings = withShape.invoke(settings, shape);
            return (Block) blockConstructor.newInstance(properties, settings);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Could not create the Oritech addon " + addon.blockName(tier), exception);
        }
    }
}
