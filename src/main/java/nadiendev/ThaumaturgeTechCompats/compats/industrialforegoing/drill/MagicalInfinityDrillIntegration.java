package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import com.leclowndu93150.thaumaturge.api.items.ChargeDisplay;
import com.leclowndu93150.thaumaturge.api.items.ChargeProfile;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

public final class MagicalInfinityDrillIntegration {
    public static final String MOD_ID = "industrialforegoing";
    public static final String INFINITY_DRILL = MOD_ID + ":infinity_drill";

    private static boolean registered;

    private MagicalInfinityDrillIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean active() {
        return registered && MagicalInfinityDrillConfig.enabled();
    }

    public static void register(IEventBus modBus, ModContainer container) {
        if (!isLoaded() || !CompatToggles.enabled(CompatToggles.INDUSTRIAL_FOREGOING) || !CompatToggles.enabled(CompatToggles.MAGIC_INFINITY_DRILL)) {
            return;
        }
        container.registerConfig(ModConfig.Type.STARTUP, MagicalInfinityDrillConfig.SPEC, MagicalInfinityDrillConfig.FILE_NAME);
        MagicalInfinityDrillRegistry.COMPONENTS.register(modBus);
        MagicalInfinityDrillRegistry.ITEMS.register(modBus);
        modBus.addListener(MagicalInfinityDrillIntegration::onModifyDefaults);
        modBus.addListener(MagicalInfinityDrillIntegration::onRegisterCapabilities);
        registered = true;
    }

    private static void onModifyDefaults(ModifyDefaultComponentsEvent event) {
        event.modify(MagicalInfinityDrillRegistry.ITEM.get(), MagicalInfinityDrillIntegration::defaults);
    }

    private static void defaults(DataComponentPatch.Builder builder) {
        builder.set(TTDataComponents.RECHARGEABLE.get(), new ChargeProfile(MagicalInfinityDrillConfig.maxVis(), ChargeDisplay.ON_CHANGE));
        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(ThaumaturgeTechCompats.id("magical_infinity_drill_attack_damage"), MagicalInfinityDrillConfig.attackDamage(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(ThaumaturgeTechCompats.id("magical_infinity_drill_attack_speed"), MagicalInfinityDrillConfig.attackSpeed(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build());
    }

    private static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new DrillTank(stack), MagicalInfinityDrillRegistry.ITEM.get());
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!active()) {
            return;
        }
        output.accept(new ItemStack(MagicalInfinityDrillRegistry.ITEM.get()));
        output.accept(MagicalInfinityDrillItem.fullStack());
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new MagicalInfinityDrillAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new MagicalInfinityDrillContent(output));
    }
}
