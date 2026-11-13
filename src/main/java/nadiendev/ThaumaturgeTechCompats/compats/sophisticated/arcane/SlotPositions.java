package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import net.minecraft.world.inventory.Slot;

import java.lang.reflect.Field;

public final class SlotPositions {
    private static final Field X = field("x");
    private static final Field Y = field("y");

    private SlotPositions() {
    }

    public static void move(Slot slot, int x, int y) {
        try {
            X.setInt(slot, x);
            Y.setInt(slot, y);
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private static Field field(String name) {
        try {
            Field field = Slot.class.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
