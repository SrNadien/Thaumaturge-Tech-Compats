package nadiendev.ThaumaturgeTechCompats.auraconverter.gui;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterBlock;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterBlockEntity;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterConfig;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterRegistry;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterStatus;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterTier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AuraConverterMenu extends AbstractContainerMenu {
    public static final String KEY = "gui." + ThaumaturgeTechCompats.MOD_ID + ".aura_converter.";

    public static final int ENERGY = 0;
    public static final int CAPACITY = 1;
    public static final int PRODUCED = 2;
    public static final int OUTPUT = 3;
    public static final int MAX_OUTPUT = 4;
    public static final int FE_PER_VIS = 5;
    public static final int VIS = 6;
    public static final int FLOOR = 7;
    public static final int BASE = 8;
    public static final int STATUS = 9;

    private static final int[] WORDS = {4, 4, 4, 4, 4, 4, 2, 2, 2, 1};
    private static final int WORD_BITS = 16;
    private static final long WORD_MASK = 0xFFFFL;
    private static final long INT_MASK = 0xFFFFFFFFL;
    private static final double MAX_DISTANCE_SQUARED = 64.0D;

    private final long[] values = new long[WORDS.length];
    private final AuraConverterBlockEntity converter;
    private final Level level;
    private final BlockPos pos;
    private final AuraConverterTier tier;

    public AuraConverterMenu(int id, Inventory inventory, RegistryFriendlyByteBuf buffer) {
        this(id, inventory, buffer.readBlockPos(), null);
    }

    public AuraConverterMenu(int id, Inventory inventory, AuraConverterBlockEntity converter) {
        this(id, inventory, converter.getBlockPos(), converter);
    }

    private AuraConverterMenu(int id, Inventory inventory, BlockPos pos, AuraConverterBlockEntity converter) {
        super(AuraConverterRegistry.MENU.get(), id);
        this.converter = converter;
        this.level = inventory.player.level();
        this.pos = pos;
        this.tier = level.getBlockState(pos).getBlock() instanceof AuraConverterBlock block ? block.tier() : AuraConverterTier.THAUMIUM;
        for (int field = 0; field < WORDS.length; field++) {
            for (int word = 0; word < WORDS[field]; word++) {
                addDataSlot(new Word(field, word * WORD_BITS));
            }
        }
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + column * 18, 142));
        }
        refresh();
    }

    public AuraConverterTier tier() {
        return tier;
    }

    public BlockPos pos() {
        return pos;
    }

    public long value(int field) {
        return values[field];
    }

    public float floatValue(int field) {
        return Float.intBitsToFloat((int) values[field]);
    }

    public AuraConverterStatus status() {
        return AuraConverterStatus.byOrdinal((int) values[STATUS]);
    }

    @Override
    public void broadcastChanges() {
        refresh();
        super.broadcastChanges();
    }

    private void refresh() {
        if (converter == null) {
            return;
        }
        float base = AuraHelper.getAuraBase(level, pos);
        values[ENERGY] = converter.energy();
        values[CAPACITY] = converter.capacity();
        values[PRODUCED] = converter.producedRate();
        values[OUTPUT] = converter.outputRate();
        values[MAX_OUTPUT] = AuraConverterConfig.maxOutput(tier);
        values[FE_PER_VIS] = AuraConverterConfig.fePerVis(tier);
        values[VIS] = bits(AuraHelper.getVis(level, pos));
        values[FLOOR] = bits(base * AuraConverterConfig.minAuraFraction());
        values[BASE] = bits(base);
        values[STATUS] = converter.status().ordinal();
    }

    private static long bits(float value) {
        return Float.floatToRawIntBits(value) & INT_MASK;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return level.getBlockState(pos).getBlock() instanceof AuraConverterBlock
                && player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= MAX_DISTANCE_SQUARED;
    }

    private final class Word extends DataSlot {
        private final int field;
        private final int shift;

        private Word(int field, int shift) {
            this.field = field;
            this.shift = shift;
        }

        @Override
        public int get() {
            return (int) ((values[field] >>> shift) & WORD_MASK);
        }

        @Override
        public void set(int value) {
            values[field] = (values[field] & ~(WORD_MASK << shift)) | (((long) value & WORD_MASK) << shift);
        }
    }
}
