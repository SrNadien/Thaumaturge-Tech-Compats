package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
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
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class VoidMinersMagicalPack extends AbstractPackResources {
    public static final String PACK_KEY = "pack." + ThaumaturgeTechCompats.MOD_ID + ".voidminers";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PACK_ID = MOD + "_voidminers_tint";
    private static final String TEXTURE_ROOT = "textures/block/voidminers/";
    private static final String SOURCE_TIER = "rubetine";
    private static final String MINER = "miner";
    private static final String FRAME = "frame";
    private static final String CORE = "core";
    private static final int PACK_FORMAT = 34;

    private final Map<String, byte[]> cache = new HashMap<>();

    public VoidMinersMagicalPack(PackLocationInfo location) {
        super(location);
    }

    public static void register(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES || !VoidMinersMagicalIntegration.active()) {
            return;
        }
        PackLocationInfo info = new PackLocationInfo(PACK_ID, Component.translatable(PACK_KEY), PackSource.BUILT_IN, Optional.empty());
        Pack.ResourcesSupplier supplier = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo location) {
                return new VoidMinersMagicalPack(location);
            }

            @Override
            public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                return new VoidMinersMagicalPack(location);
            }
        };
        Pack pack = Pack.readMetaAndCreate(info, supplier, PackType.CLIENT_RESOURCES, new PackSelectionConfig(true, Pack.Position.TOP, false));
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }

    public static String texturePath(VoidMinersMagicalTier tier, String name) {
        return TEXTURE_ROOT + tier.id() + "/" + name + ".png";
    }

    private static String metadata() {
        return "{\"pack\":{\"description\":{\"translate\":\"" + PACK_KEY + "\"},\"pack_format\":" + PACK_FORMAT + "}}";
    }

    private static VoidMinersMagicalTier tierOf(String path) {
        if (!path.startsWith(TEXTURE_ROOT) || !path.endsWith(".png")) {
            return null;
        }
        String rest = path.substring(TEXTURE_ROOT.length());
        int slash = rest.indexOf('/');
        if (slash < 0) {
            return null;
        }
        String id = rest.substring(0, slash);
        String name = rest.substring(slash + 1, rest.length() - ".png".length());
        if (!name.equals(MINER) && !name.equals(FRAME) && !name.equals(CORE)) {
            return null;
        }
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            if (tier.id().equals(id)) {
                return tier;
            }
        }
        return null;
    }

    private synchronized byte[] texture(String path) throws IOException {
        byte[] cached = cache.get(path);
        if (cached != null) {
            return cached;
        }
        VoidMinersMagicalTier tier = tierOf(path);
        String name = path.substring(path.lastIndexOf('/') + 1, path.length() - ".png".length());
        byte[] bytes = tint(readSource(name), VoidMinersMagicalConfig.rgb(tier));
        cache.put(path, bytes);
        return bytes;
    }

    private static byte[] readSource(String name) throws IOException {
        Path file = ModList.get().getModContainerById(VoidMinersMagicalIntegration.MOD_ID)
                .orElseThrow(() -> new IOException(VoidMinersMagicalIntegration.MOD_ID))
                .getModInfo().getOwningFile().getFile()
                .findResource("assets", VoidMinersMagicalIntegration.MOD_ID, "textures", "block", SOURCE_TIER, name + ".png");
        return Files.readAllBytes(file);
    }

    private static byte[] tint(byte[] source, int rgb) throws IOException {
        BufferedImage input = ImageIO.read(new ByteArrayInputStream(source));
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int targetRed = (rgb >> 16) & 0xFF;
        int targetGreen = (rgb >> 8) & 0xFF;
        int targetBlue = rgb & 0xFF;
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                int argb = input.getRGB(x, y);
                int alpha = argb >>> 24;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = argb & 0xFF;
                if (alpha != 0 && green == 0 && blue == 0 && red > 0) {
                    float shade = red / 255.0F;
                    argb = (alpha << 24)
                            | (Math.round(targetRed * shade) << 16)
                            | (Math.round(targetGreen * shade) << 8)
                            | Math.round(targetBlue * shade);
                }
                output.setRGB(x, y, argb);
            }
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ImageIO.write(output, "png", bytes);
        return bytes.toByteArray();
    }

    private IoSupplier<InputStream> textureSupplier(String path) {
        return () -> new ByteArrayInputStream(texture(path));
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
        if (type != PackType.CLIENT_RESOURCES || !location.getNamespace().equals(MOD) || tierOf(location.getPath()) == null) {
            return null;
        }
        return textureSupplier(location.getPath());
    }

    @Override
    public void listResources(PackType type, String namespace, String prefix, ResourceOutput output) {
        if (type != PackType.CLIENT_RESOURCES || !namespace.equals(MOD)) {
            return;
        }
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            for (String name : new String[]{MINER, FRAME, CORE}) {
                String path = texturePath(tier, name);
                if (path.startsWith(prefix)) {
                    output.accept(ResourceLocation.fromNamespaceAndPath(MOD, path), textureSupplier(path));
                }
            }
        }
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return type == PackType.CLIENT_RESOURCES ? Set.of(MOD) : Set.of();
    }

    @Override
    public void close() {
    }
}
