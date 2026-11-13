package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.lang.reflect.Field;
import java.util.Map;

public final class MagicalUpgradeTotals {
    public static final MagicalUpgradeTotals NONE = new MagicalUpgradeTotals(1.0F, 1.0F, 1.0F, 1.0F);

    private final float speed;
    private final float production;
    private final float cost;
    private final float generation;

    private MagicalUpgradeTotals(float speed, float production, float cost, float generation) {
        this.speed = speed;
        this.production = production;
        this.cost = cost;
        this.generation = generation;
    }

    public static Field modifierField(Class<?> type) {
        return field(type, "modifierMap");
    }

    public static Field field(Class<?> type, String name) {
        try {
            Field field = type.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (ReflectiveOperationException | RuntimeException exception) {
            return null;
        }
    }

    public static Map<?, ?> modifiers(Field field, Object owner) {
        if (field == null) {
            return Map.of();
        }
        try {
            return (Map<?, ?>) field.get(owner);
        } catch (IllegalAccessException | RuntimeException exception) {
            return Map.of();
        }
    }

    public static boolean isMagical(Object key) {
        return key instanceof BlockInWorld position && position.getState().getBlock() instanceof MagicalModifierBlock;
    }

    public static MagicalUpgradeTotals scan(Map<?, ?> modifiers) {
        float speed = 1.0F;
        float production = 1.0F;
        float cost = 1.0F;
        float generation = 1.0F;
        for (Object key : modifiers.keySet()) {
            if (key instanceof BlockInWorld position && position.getState().getBlock() instanceof MagicalModifierBlock block) {
                float value = VoidMinersMagicalConfig.upgrade(block.tier(), block.upgrade());
                switch (block.upgrade()) {
                    case SPEED -> speed *= value;
                    case PRODUCTION -> production *= value;
                    case EFFICIENCY, SAVING -> cost *= value;
                    case GENERATION -> generation *= value;
                }
            }
        }
        return new MagicalUpgradeTotals(speed, production, cost, generation);
    }

    public float speed() {
        return speed;
    }

    public float production() {
        return production;
    }

    public float cost() {
        return cost;
    }

    public float generation() {
        return generation;
    }
}
