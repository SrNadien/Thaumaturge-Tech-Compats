package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class TerraformerBaits {
    private static List<? extends String> baitSource;
    private static Map<Identifier, Identifier> baits = Map.of();
    private static List<? extends String> biomeSource;
    private static Set<Identifier> blockedBiomes = Set.of();
    private static List<? extends String> dimensionSource;
    private static Set<Identifier> blockedDimensions = Set.of();

    private TerraformerBaits() {
    }

    public static synchronized Optional<Identifier> biomeFor(Identifier item) {
        List<? extends String> current = FieldConfig.terraformerBaits();
        if (current != baitSource) {
            Map<Identifier, Identifier> parsed = new HashMap<>();
            for (String entry : current) {
                int split = entry.indexOf('=');
                Identifier key = split > 0 ? Identifier.tryParse(entry.substring(0, split)) : null;
                Identifier value = split > 0 ? Identifier.tryParse(entry.substring(split + 1)) : null;
                if (key != null && value != null) {
                    parsed.put(key, value);
                }
            }
            baits = parsed;
            baitSource = current;
        }
        return Optional.ofNullable(baits.get(item));
    }

    public static Optional<Identifier> biomeFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        return biomeFor(BuiltInRegistries.ITEM.getKey(stack.getItem()));
    }

    public static synchronized boolean biomeBlocked(Identifier biome) {
        List<? extends String> current = FieldConfig.terraformerBlockedBiomes();
        if (current != biomeSource) {
            blockedBiomes = parse(current);
            biomeSource = current;
        }
        return blockedBiomes.contains(biome);
    }

    public static synchronized boolean dimensionBlocked(Identifier dimension) {
        List<? extends String> current = FieldConfig.terraformerBlockedDimensions();
        if (current != dimensionSource) {
            blockedDimensions = parse(current);
            dimensionSource = current;
        }
        return blockedDimensions.contains(dimension);
    }

    public static Optional<Holder.Reference<Biome>> resolve(ServerLevel level, Identifier biome) {
        Registry<Biome> registry = level.registryAccess().lookupOrThrow(Registries.BIOME);
        return registry.get(ResourceKey.create(Registries.BIOME, biome));
    }

    private static Set<Identifier> parse(List<? extends String> entries) {
        Set<Identifier> parsed = new HashSet<>();
        for (String entry : entries) {
            Identifier id = Identifier.tryParse(entry);
            if (id != null) {
                parsed.add(id);
            }
        }
        return parsed;
    }
}
