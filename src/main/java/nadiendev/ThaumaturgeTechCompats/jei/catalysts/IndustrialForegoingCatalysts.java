package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingConfig;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;

final class IndustrialForegoingCatalysts {
    private static final String RECIPE_TYPES = "com.buuz135.industrial.plugin.jei.IndustrialRecipeTypes";
    private static final String LASER_ORE = "LASER_ORE";
    private static final String LASER_FLUID = "LASER_FLUID";

    private IndustrialForegoingCatalysts() {
    }

    static void register(IRecipeCatalystRegistration registration) {
        for (LaserMachine machine : LaserMachine.values()) {
            if (!IndustrialForegoingConfig.enabled(machine)) {
                continue;
            }
            for (String field : fields(machine)) {
                CatalystSupport.staticType(RECIPE_TYPES, field)
                        .ifPresent(type -> CatalystSupport.add(registration, machine.blockName(), type));
            }
        }
    }

    private static String[] fields(LaserMachine machine) {
        return switch (machine) {
            case DRILL -> new String[]{LASER_ORE, LASER_FLUID};
            case ORE_BASE -> new String[]{LASER_ORE};
            case FLUID_BASE -> new String[]{LASER_FLUID};
        };
    }
}
