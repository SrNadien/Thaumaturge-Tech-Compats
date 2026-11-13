package nadiendev.ThaumaturgeTechCompats.ponder;

import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class StructureSketch {
    private final int size;
    private final Map<BlockPos, BlockState> blocks = new LinkedHashMap<>();

    public StructureSketch(int size) {
        this.size = size;
    }

    public StructureSketch floor(BlockState state) {
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                set(x, 0, z, state);
            }
        }
        return this;
    }

    public StructureSketch set(int x, int y, int z, BlockState state) {
        blocks.put(new BlockPos(x, y, z), state);
        return this;
    }

    public StructureSketch pattern(int originX, int originY, int originZ, Map<Character, BlockState> keys, String[][] layers) {
        for (int layer = 0; layer < layers.length; layer++) {
            int y = originY + layers.length - 1 - layer;
            String[] rows = layers[layer];
            for (int row = 0; row < rows.length; row++) {
                String line = rows[row];
                for (int column = 0; column < line.length(); column++) {
                    BlockState state = keys.get(line.charAt(column));
                    if (state != null) {
                        set(originX + column, y, originZ + row, state);
                    }
                }
            }
        }
        return this;
    }

    public CompoundTag toTag() {
        List<BlockState> palette = new ArrayList<>();
        ListTag blockList = new ListTag();
        int height = 1;
        for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
            BlockPos pos = entry.getKey();
            height = Math.max(height, pos.getY() + 1);
            int index = palette.indexOf(entry.getValue());
            if (index < 0) {
                index = palette.size();
                palette.add(entry.getValue());
            }
            CompoundTag block = new CompoundTag();
            ListTag position = new ListTag();
            position.add(IntTag.valueOf(pos.getX()));
            position.add(IntTag.valueOf(pos.getY()));
            position.add(IntTag.valueOf(pos.getZ()));
            block.put("pos", position);
            block.putInt("state", index);
            blockList.add(block);
        }
        ListTag paletteList = new ListTag();
        for (BlockState state : palette) {
            paletteList.add(NbtUtils.writeBlockState(state));
        }
        ListTag sizeList = new ListTag();
        sizeList.add(IntTag.valueOf(size));
        sizeList.add(IntTag.valueOf(height));
        sizeList.add(IntTag.valueOf(size));
        CompoundTag root = new CompoundTag();
        root.put("size", sizeList);
        root.put("palette", paletteList);
        root.put("blocks", blockList);
        root.put("entities", new ListTag());
        root.putInt("DataVersion", SharedConstants.getCurrentVersion().getDataVersion().getVersion());
        return root;
    }
}
