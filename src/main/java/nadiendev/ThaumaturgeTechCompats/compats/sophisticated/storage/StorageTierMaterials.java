package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTier;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.HashMap;
import java.util.Map;

public final class StorageTierMaterials {
    private static final Map<String, Material> CHEST = new HashMap<>();
    private static final Map<SophisticatedTier, Material> SHULKER_BOX = new HashMap<>();

    private StorageTierMaterials() {
    }

    public static Material chest(SophisticatedTier tier, ChestType type) {
        String prefix = switch (type) {
            case LEFT -> "left_";
            case RIGHT -> "right_";
            default -> "";
        };
        return CHEST.computeIfAbsent(prefix + tier.chestTexture(), name -> new Material(Sheets.CHEST_SHEET,
                Identifier.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, "entity/chest/" + name)));
    }

    public static Material shulkerBox(SophisticatedTier tier) {
        return SHULKER_BOX.computeIfAbsent(tier, key -> new Material(Sheets.SHULKER_SHEET,
                Identifier.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, "entity/shulker_box/" + key.chestTexture())));
    }
}
