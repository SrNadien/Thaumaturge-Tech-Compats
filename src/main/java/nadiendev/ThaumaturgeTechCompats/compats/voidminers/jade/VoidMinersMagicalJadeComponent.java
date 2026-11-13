package nadiendev.ThaumaturgeTechCompats.compats.voidminers.jade;

import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalPower;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalTooltip;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalTranslations;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import nadiendev.voidminersremastered.world.block.entity.HaltReason;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum VoidMinersMagicalJadeComponent implements IBlockComponentProvider {
    INSTANCE;

    private static final int AFTER_VOID_MINERS = 1000;
    private static final String VM = "jade.voidminers.";

    @Override
    public ResourceLocation getUid() {
        return VoidMinersMagicalJadeData.UID;
    }

    @Override
    public int getDefaultPriority() {
        return AFTER_VOID_MINERS;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!data.contains(VoidMinersMagicalJadeData.ROOT)) {
            return;
        }
        CompoundTag tag = data.getCompound(VoidMinersMagicalJadeData.ROOT);
        boolean solar = tag.getBoolean(VoidMinersMagicalJadeData.SOLAR);
        NodeSourceKind source = NodeSourceKind.byId(tag.getString(VoidMinersMagicalJadeData.SOURCE));
        MagicalPower.Failure failure = MagicalPower.Failure.byId(tag.getString(VoidMinersMagicalJadeData.FAILURE));
        HaltReason halt = HaltReason.getHaltReasonFromInt(tag.getInt(VoidMinersMagicalJadeData.HALT));
        status(tooltip, halt, failure);
        int max = tag.getInt(VoidMinersMagicalJadeData.MAX_PROGRESS);
        if (!solar && max > 0) {
            int percent = (int) (tag.getInt(VoidMinersMagicalJadeData.PROGRESS) / (float) max * 100.0F);
            tooltip.add(Component.translatable(VM + "progress", percent));
        }
        if (source == null) {
            tooltip.add(Component.translatable(VoidMinersMagicalTranslations.JADE_NO_AURA).withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable(VoidMinersMagicalTranslations.JADE_AURA,
                    Component.translatable(VoidMinersMagicalTranslations.sourceKey(source)).withStyle(ChatFormatting.LIGHT_PURPLE)));
        }
        tooltip.add(Component.translatable(VoidMinersMagicalTranslations.JADE_COST, tag.getInt(VoidMinersMagicalJadeData.UNITS),
                tag.getInt(VoidMinersMagicalJadeData.INTERVAL), MagicalTooltip.number(tag.getFloat(VoidMinersMagicalJadeData.COST))));
        if (solar) {
            tooltip.add(Component.translatable(VoidMinersMagicalTranslations.JADE_GENERATION,
                    String.format("%,d", tag.getLong(VoidMinersMagicalJadeData.GENERATION))));
            tooltip.add(Component.translatable(VoidMinersMagicalTranslations.JADE_UPGRADES_SOLAR,
                    MagicalTooltip.number(tag.getFloat(VoidMinersMagicalJadeData.GENERATION_FACTOR)),
                    MagicalTooltip.number(tag.getFloat(VoidMinersMagicalJadeData.COST))));
        } else {
            tooltip.add(Component.translatable(VoidMinersMagicalTranslations.JADE_UPGRADES_MINER,
                    MagicalTooltip.number(tag.getFloat(VoidMinersMagicalJadeData.SPEED)),
                    MagicalTooltip.number(tag.getFloat(VoidMinersMagicalJadeData.PRODUCTION)),
                    MagicalTooltip.number(tag.getFloat(VoidMinersMagicalJadeData.COST))));
        }
    }

    private static void status(ITooltip tooltip, HaltReason halt, MagicalPower.Failure failure) {
        if (halt == HaltReason.NONE) {
            tooltip.add(Component.translatable(VM + "status.working"));
            return;
        }
        tooltip.add(Component.translatable(VM + "status.idle"));
        String reason = switch (halt) {
            case NO_RECIPES_IN_DIMENSION -> VM + "halt_reason.no_recipes_in_dimension";
            case STRUCTURE_NOT_FOUND -> VM + "halt_reason.structure_not_found";
            case TOO_MUCH_ITEM_MULTIPLIER -> VM + "halt_reason.too_much_item_multiplier";
            case NOT_ENOUGH_EMPTY_SLOTS -> VM + "halt_reason.not_enough_empty_slots";
            case NO_BEDROCK_OR_VOID_VIEW -> VM + "halt_reason.no_bedrock_or_void_view";
            case POWER_FULL -> VM + "halt_reason.power_full";
            case NO_SKY_VIEW -> VM + "halt_reason.no_sky_view";
            default -> VoidMinersMagicalTranslations.jadeReasonKey(failure);
        };
        tooltip.add(Component.translatable(reason));
    }
}
