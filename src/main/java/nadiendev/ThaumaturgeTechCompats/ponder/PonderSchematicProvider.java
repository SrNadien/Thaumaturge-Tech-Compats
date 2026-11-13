package nadiendev.ThaumaturgeTechCompats.ponder;

import com.google.common.hash.Hashing;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.NbtIo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PonderSchematicProvider implements DataProvider {
    private final PackOutput output;

    public PonderSchematicProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (Map.Entry<String, Supplier<StructureSketch>> entry : PonderSchematics.all().entrySet()) {
            Path path = output.getOutputFolder().resolve("assets/" + ThaumaturgeTechCompats.MOD_ID + "/ponder/" + entry.getKey() + ".nbt");
            byte[] bytes = encode(entry.getValue().get());
            tasks.add(CompletableFuture.runAsync(() -> write(cache, path, bytes)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats ponder schematics";
    }

    private static byte[] encode(StructureSketch sketch) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            NbtIo.writeCompressed(sketch.toTag(), stream);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
        return stream.toByteArray();
    }

    private static void write(CachedOutput cache, Path path, byte[] bytes) {
        try {
            cache.writeIfNeeded(path, bytes, Hashing.sha1().hashBytes(bytes));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
