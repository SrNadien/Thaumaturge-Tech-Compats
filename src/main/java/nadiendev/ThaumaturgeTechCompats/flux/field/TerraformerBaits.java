package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
    private static Map<ResourceLocation, ResourceLocation> baits = Map.of();
    private static List<? extends String> biomeSource;
    private static Set<ResourceLocation> blockedBiomes = Set.of();
    private static List<? extends String> dimensionSource;
    private static Set<ResourceLocation> blockedDimensions = Set.of();

    private TerraformerBaits() {
    }

    public static synchronized Optional<ResourceLocation> biomeFor(ResourceLocation item) {
        List<? extends String> current = FieldConfig.terraformerBaits();
        if (current != baitSource) {
            Map<ResourceLocation, ResourceLocation> parsed = new HashMap<>();
            for (String entry : current) {
                int split = entry.indexOf('=');
                ResourceLocation key = split > 0 ? ResourceLocation.tryParse(entry.substring(0, split)) : null;
                ResourceLocation value = split > 0 ? ResourceLocation.tryParse(entry.substring(split + 1)) : null;
                if (key != null && value != null) {
                    parsed.put(key, value);
                }
            }
            baits = parsed;
            baitSource = current;
        }
        return Optional.ofNullable(baits.get(item));
    }

    public static Optional<ResourceLocation> biomeFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        return biomeFor(BuiltInRegistries.ITEM.getKey(stack.getItem()));
    }

    public static synchronized boolean biomeBlocked(ResourceLocation biome) {
        List<? extends String> current = FieldConfig.terraformerBlockedBiomes();
        if (current != biomeSource) {
            blockedBiomes = parse(current);
            biomeSource = current;
        }
        return blockedBiomes.contains(biome);
    }

    public static synchronized boolean dimensionBlocked(ResourceLocation dimension) {
        List<? extends String> current = FieldConfig.terraformerBlockedDimensions();
        if (current != dimensionSource) {
            blockedDimensions = parse(current);
            dimensionSource = current;
        }
        return blockedDimensions.contains(dimension);
    }

    public static Optional<Holder.Reference<Biome>> resolve(ServerLevel level, ResourceLocation biome) {
        Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);
        return registry.getHolder(ResourceKey.create(Registries.BIOME, biome));
    }

    private static Set<ResourceLocation> parse(List<? extends String> entries) {
        Set<ResourceLocation> parsed = new HashSet<>();
        for (String entry : entries) {
            ResourceLocation id = ResourceLocation.tryParse(entry);
            if (id != null) {
                parsed.add(id);
            }
        }
        return parsed;
    }
}
