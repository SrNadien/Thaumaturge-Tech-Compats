package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

public final class CrucifixTiming {
    public static final int FORM_TICKS = 20;
    public static final int LIFT_TICKS = 70;
    public static final double LIFT_HEIGHT = 1.5;
    public static final int SINK_TICKS = 36;
    public static final double SINK_EXTRA_DEPTH = 1.0;
    public static final int CHAINS = 3;

    public static final int FAIL_ROAR_TICKS = 56;
    public static final int FAIL_FIRST_SNAP_TICKS = 60;
    public static final int FAIL_SNAP_INTERVAL = 4;
    public static final int FAIL_CRACK_TICKS = 74;
    public static final int FAIL_TICKS = 82;
    public static final int FAIL_AFTERMATH_TICKS = 20;
    public static final int STRAIN_SOUND_INTERVAL = 10;
    public static final double FAIL_BOSS_LIFT = 0.06;
    public static final double SHOCKWAVE_RADIUS = 9.0;
    public static final int SHOCKWAVE_TICKS = 12;
    public static final int SNAP_RECOIL_TICKS = 6;
    public static final double SCREEN_SHAKE_RANGE = 24.0;
    public static final float SCREEN_SHAKE_DEGREES = 2.5F;
    public static final int FLASH_TICKS = 10;
    public static final float ROAR_PITCH = 0.55F;
    public static final double KNOCKBACK_HORIZONTAL = 0.4;
    public static final double KNOCKBACK_VERTICAL = 0.35;
    public static final double FAIL_AFTERMATH_RANGE = 16.0;
    public static final int SHAKEN_TICKS = 60;
    public static final int SHAKEN_SLOWNESS_AMPLIFIER = 1;
    public static final int BOSS_FURY_TICKS = 100;
    public static final int BOSS_FURY_AMPLIFIER = 0;

    public static final float SPIN_DEGREES_PER_TICK = 3.0F;
    public static final float TREMBLE_RATE = 0.5F;

    public static final double DISPLAY_GAP = 2.5;
    public static final double DISPLAY_ABOVE_EYES = 0.25;
    public static final double DISPLAY_NEAR_FRACTION = 0.4;
    public static final double DISPLAY_CLEARANCE = 0.75;
    public static final float DISPLAY_SIZE = 0.5F;
    public static final float DISPLAY_SCALE = 1.25F;

    private CrucifixTiming() {
    }

    public static int minBind() {
        return FORM_TICKS;
    }

    public static int duration(boolean success, int bind) {
        return success ? bind + SINK_TICKS : FAIL_TICKS;
    }

    public static boolean finished(boolean success, int age, int bind) {
        return age >= duration(success, bind);
    }

    public static double lift(double age, int bind) {
        return LIFT_HEIGHT * smooth(age / Math.min(LIFT_TICKS, bind));
    }

    public static double sink(double age, int bind) {
        double t = clamp01((age - bind) / SINK_TICKS);
        return t * t;
    }

    public static boolean sinking(double age, int bind) {
        return age >= bind;
    }

    public static double sinkDepth(double height) {
        return height + SINK_EXTRA_DEPTH;
    }

    public static double formation(double age) {
        return smooth(age / FORM_TICKS);
    }

    public static double strain(double age) {
        return clamp01((age - FORM_TICKS) / (double) (FAIL_ROAR_TICKS - FORM_TICKS));
    }

    public static int snapTick(int chain) {
        return FAIL_FIRST_SNAP_TICKS + chain * FAIL_SNAP_INTERVAL;
    }

    public static int snapped(double age) {
        int count = 0;
        while (count < CHAINS && age >= snapTick(count)) {
            count++;
        }
        return count;
    }

    public static boolean cracked(double age) {
        return age >= FAIL_CRACK_TICKS;
    }

    public static double shockwave(double age) {
        double t = (age - FAIL_ROAR_TICKS) / SHOCKWAVE_TICKS;
        if (t < 0.0 || t > 1.0) {
            return -1.0;
        }
        return 1.0 - (1.0 - t) * (1.0 - t);
    }

    public static double failShake(double age) {
        if (age < FORM_TICKS) {
            return 0.0;
        }
        if (age >= FAIL_TICKS) {
            return 1.0 - clamp01((age - FAIL_TICKS) / FAIL_AFTERMATH_TICKS);
        }
        double s = strain(age);
        double base = 0.1 + 0.35 * s * s;
        if (age < FAIL_ROAR_TICKS) {
            return base;
        }
        double kick = kick(age, FAIL_ROAR_TICKS, 0.35);
        for (int c = 0; c < CHAINS; c++) {
            kick = Math.max(kick, kick(age, snapTick(c), 0.25));
        }
        kick = Math.max(kick, kick(age, FAIL_CRACK_TICKS, 0.3));
        double climax = clamp01((age - FAIL_ROAR_TICKS) / (double) (FAIL_TICKS - FAIL_ROAR_TICKS));
        return Math.min(1.0, base + 0.2 * climax + kick);
    }

    private static double kick(double age, int at, double strength) {
        double t = (age - at) / 6.0;
        return t < 0.0 || t > 1.0 ? 0.0 : strength * (1.0 - t);
    }

    public static double shake(boolean success, double age, int bind) {
        if (!success) {
            return 0.02 + 0.08 * strain(age);
        }
        return (0.025 + 0.035 * clamp01(age / bind)) * (1.0 - clamp01((age - bind) / SINK_TICKS));
    }

    public static float spin(double age) {
        return (float) ((age * SPIN_DEGREES_PER_TICK) % 360.0);
    }

    public static double displayDistance(double toPlayer, double targetHalfWidth, double playerHalfWidth) {
        double min = targetHalfWidth + DISPLAY_CLEARANCE + DISPLAY_SIZE * 0.5;
        double max = toPlayer - playerHalfWidth - DISPLAY_CLEARANCE - DISPLAY_SIZE * 0.5;
        double far = targetHalfWidth + DISPLAY_GAP;
        double wanted = far <= max ? far : toPlayer * DISPLAY_NEAR_FRACTION;
        return Math.max(min, Math.min(wanted, Math.max(min, max)));
    }

    private static double smooth(double t) {
        double c = clamp01(t);
        return c * c * (3.0 - 2.0 * c);
    }

    private static double clamp01(double t) {
        return Math.max(0.0, Math.min(1.0, t));
    }
}
