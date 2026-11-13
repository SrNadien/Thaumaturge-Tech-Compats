package nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

public class ConduitPack extends AbstractPackResources {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PACK_ID = MOD + "_enderio_conduits";
    private static final String DIRECTORY = "enderio/conduit/";
    private static final int PACK_FORMAT = 48;

    public ConduitPack(PackLocationInfo location) {
        super(location);
    }

    public static void register(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA || !EnderIOIntegration.enabled(EnderIOIntegration.CONDUITS)) {
            return;
        }
        PackLocationInfo info = new PackLocationInfo(PACK_ID, Component.literal("Thaumaturge Tech Compats Ender IO conduits"), PackSource.BUILT_IN, Optional.empty());
        Pack.ResourcesSupplier supplier = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo location) {
                return new ConduitPack(location);
            }

            @Override
            public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                return new ConduitPack(location);
            }
        };
        Pack pack = Pack.readMetaAndCreate(info, supplier, PackType.SERVER_DATA, new PackSelectionConfig(true, Pack.Position.TOP, false));
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }

    public static String path(ConduitKind kind, EnderIOTier tier) {
        return DIRECTORY + kind.name(tier) + ".json";
    }

    public static String texture(ConduitKind kind, EnderIOTier tier) {
        return MOD + ":block/conduit/" + kind.name(tier);
    }

    private static String metadata() {
        return "{\"pack\":{\"description\":\"Thaumaturge Tech Compats Ender IO conduits\",\"pack_format\":" + PACK_FORMAT + "}}";
    }

    private static String conduit(ConduitKind kind, EnderIOTier tier) {
        JsonObject description = new JsonObject();
        description.addProperty("translate", "conduit." + MOD + "." + kind.name(tier));
        JsonObject json = new JsonObject();
        json.addProperty("type", kind.type());
        json.add("description", description);
        json.addProperty("texture", texture(kind, tier));
        json.addProperty("transfer_rate", ConduitConfig.rate(kind, tier));
        if (kind.hasTicks()) {
            json.addProperty("ticks_per_cycle", ConduitConfig.ticks(kind, tier));
        }
        return json.toString();
    }

    private static IoSupplier<InputStream> supplier(ConduitKind kind, EnderIOTier tier) {
        return () -> new ByteArrayInputStream(conduit(kind, tier).getBytes(StandardCharsets.UTF_8));
    }

    private static IoSupplier<InputStream> find(String path) {
        for (ConduitKind kind : ConduitKind.values()) {
            for (EnderIOTier tier : EnderIOTier.values()) {
                if (path(kind, tier).equals(path)) {
                    return supplier(kind, tier);
                }
            }
        }
        return null;
    }

    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        if (elements.length == 1 && elements[0].equals("pack.mcmeta")) {
            return () -> new ByteArrayInputStream(metadata().getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        if (type != PackType.SERVER_DATA || !location.getNamespace().equals(MOD)) {
            return null;
        }
        return find(location.getPath());
    }

    @Override
    public void listResources(PackType type, String namespace, String prefix, ResourceOutput output) {
        if (type != PackType.SERVER_DATA || !namespace.equals(MOD)) {
            return;
        }
        for (ConduitKind kind : ConduitKind.values()) {
            for (EnderIOTier tier : EnderIOTier.values()) {
                String path = path(kind, tier);
                if (path.startsWith(prefix)) {
                    output.accept(ResourceLocation.fromNamespaceAndPath(MOD, path), supplier(kind, tier));
                }
            }
        }
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return type == PackType.SERVER_DATA ? Set.of(MOD) : Set.of();
    }

    @Override
    public void close() {
    }
}
