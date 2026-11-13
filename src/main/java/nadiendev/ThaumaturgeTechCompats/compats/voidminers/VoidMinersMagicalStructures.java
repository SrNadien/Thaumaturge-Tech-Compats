package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.voidminersremastered.VoidMinersRemastered;
import nadiendev.voidminersremastered.init.ModBlocks;
import nadiendev.voidminersremastered.util.CustomColorUtil;
import nadiendev.voidminersremastered.util.MiscUtil;
import nadiendev.voidminersremastered.world.multiblock.MinerMultiblocks;
import nadiendev.voidminersremastered.world.multiblock.MultiBlockAislePatternBuilder;
import nadiendev.voidminersremastered.world.multiblock.SolarMultiblocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class VoidMinersMagicalStructures {
    public static final TagKey<Block> SOLAR_MODIFIERS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VoidMinersRemastered.MODID, "solar_modifiers"));

    private static final TagKey<Block> MINER_MODIFIERS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VoidMinersRemastered.MODID, "miner_modifiers"));
    private static final char CONTROLLER = '*';

    private VoidMinersMagicalStructures() {
    }

    public static CustomColorUtil color(VoidMinersMagicalTier tier) {
        return new CustomColorUtil(VoidMinersMagicalConfig.hex(tier));
    }

    public static Identifier minerStructure(VoidMinersMagicalTier tier) {
        return Identifier.fromNamespaceAndPath(VoidMinersRemastered.MODID, tier.minerStructure());
    }

    public static Identifier solarStructure(VoidMinersMagicalTier tier) {
        return Identifier.fromNamespaceAndPath(VoidMinersRemastered.MODID, tier.solarStructure());
    }

    public static void registerMiner(VoidMinersMagicalTier tier, Block controller, Block frame) {
        MiscUtil.tierMap.put(tier.minerStructure(), VoidMinersMagicalConfig.minerTier(tier));
        MultiBlockAislePatternBuilder builder = MinerMultiblocks.createAccessiblePattern(
                minerStructure(tier).toString(), minerAisles(tier), predicates(controller, frame, MINER_MODIFIERS, false), states(frame));
        MinerMultiblocks.MANAGER.register(tier.minerStructure(), builder.build());
    }

    public static void registerSolar(VoidMinersMagicalTier tier, Block controller, Block frame) {
        MiscUtil.tierMap.put(tier.solarStructure(), VoidMinersMagicalConfig.minerTier(tier));
        MultiBlockAislePatternBuilder builder = SolarMultiblocks.createAccessiblePattern(
                solarStructure(tier).toString(), solarAisles(tier), predicates(controller, frame, SOLAR_MODIFIERS, true), states(frame));
        SolarMultiblocks.MANAGER.register(tier.solarStructure(), builder.build());
    }

    private static Map<Character, Predicate<BlockInWorld>> predicates(Block controller, Block frame, TagKey<Block> modifiers, boolean solar) {
        Predicate<BlockInWorld> controllerTest = block -> block.getState().is(controller);
        Predicate<BlockInWorld> panelTest = block -> block.getState().is(ModBlocks.GLASS_PANEL.get());
        Predicate<BlockInWorld> frameTest = block -> block.getState().is(frame);
        Predicate<BlockInWorld> modifierTest = block -> block.getState().is(modifiers)
                || block.getState().getBlock() instanceof MagicalModifierBlock upgrade && upgrade.upgrade().solar() == solar;
        return Map.of(CONTROLLER, controllerTest, 'P', panelTest, 'F', frameTest, 'M', modifierTest);
    }

    private static Map<Character, Supplier<BlockState>> states(Block frame) {
        Supplier<BlockState> panel = () -> ModBlocks.GLASS_PANEL.get().defaultBlockState();
        Supplier<BlockState> frameState = frame::defaultBlockState;
        Supplier<BlockState> modifier = () -> ModBlocks.NULL_MOD.get().defaultBlockState();
        return Map.of('P', panel, 'F', frameState, 'M', modifier);
    }

    private static List<List<String>> minerAisles(VoidMinersMagicalTier tier) {
        return switch (tier) {
            case THAUMIUM -> List.of(
                    layer(5, "", "", "  *", "", ""),
                    layer(5, "", "  F", " F F", "  F", ""),
                    layer(5, "  F", "", "F   F", "", "  F"),
                    layer(5, " FFF", "FPMPF", "FPPPF", "FPMPF", " FFF"));
            case ALCHEMICAL_BRASS -> List.of(
                    layer(7, "", "", "", "   *", "", "", ""),
                    layer(7, "", "   F", "   F", " FF FF", "   F", "   F", ""),
                    wideLegs(),
                    wideLegs(),
                    layer(7, " FFFFF", "FMPPPMF", "FPPPPPF", "FPPPPPF", "FPPPPPF", "FMPPPMF", " FFFFF"));
            case VOID_METAL -> List.of(
                    layer(7, "", "", "", "   *", "", "", ""),
                    layer(7, "", "   F", "   F", " FF FF", "   F", "   F", ""),
                    wideLegs(),
                    wideLegs(),
                    wideLegs(),
                    layer(7, " FFFFF", "FMPPPMF", "FPPPPPF", "FMPPPMF", "FPPPPPF", "FMPPPMF", " FFFFF"));
        };
    }

    private static List<List<String>> solarAisles(VoidMinersMagicalTier tier) {
        return switch (tier) {
            case THAUMIUM -> List.of(
                    layer(5, " FFF", "FPMPF", "FPPPF", "FPMPF", " FFF"),
                    layer(5, "  F", "", "F   F", "", "  F"),
                    layer(5, "", "  F", " F F", "  F", ""),
                    layer(5, "", "", "  *", "", ""));
            case ALCHEMICAL_BRASS -> List.of(
                    layer(7, " FFFFF", "FMPPPMF", "FPPPPPF", "FPPPPPF", "FPPPPPF", "FMPPPMF", " FFFFF"),
                    wideLegs(),
                    wideLegs(),
                    layer(7, "", "   F", "   F", " FF FF", "   F", "   F", ""),
                    layer(7, "", "", "", "   *", "", "", ""));
            case VOID_METAL -> List.of(
                    layer(7, " FFFFF", "FMPPPMF", "FPPPPPF", "FMPPPMF", "FPPPPPF", "FMPPPMF", " FFFFF"),
                    wideLegs(),
                    wideLegs(),
                    wideLegs(),
                    layer(7, "", "   F", "   F", " FF FF", "   F", "   F", ""),
                    layer(7, "", "", "", "   *", "", "", ""));
        };
    }

    private static List<String> wideLegs() {
        return layer(7, "   F", "", "", "F     F", "", "", "   F");
    }

    private static List<String> layer(int width, String... rows) {
        List<String> padded = new ArrayList<>();
        for (String row : rows) {
            padded.add(row + " ".repeat(width - row.length()));
        }
        return padded;
    }
}
