package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.UnaryOperator;

public class CrucifixSoundProvider extends SoundDefinitionsProvider {
    public static final String SUBTITLE_PREFIX = "subtitles." + ThaumaturgeTechCompats.MOD_ID + ".";

    public CrucifixSoundProvider(PackOutput output) {
        super(output, ThaumaturgeTechCompats.MOD_ID);
    }

    @Override
    public void registerSounds() {
        add(CrucifixRegistry.BIND, "item.star_crucifix.bind", files("crucifix/bind", 1));
        add(CrucifixRegistry.CHAINS, "item.star_crucifix.chains", files("crucifix/chains", 2, s -> s.volume(0.8F)));
        add(CrucifixRegistry.TREMBLE, "item.star_crucifix.tremble", files("crucifix/tremble", 1, s -> s.volume(0.7F)));
        add(CrucifixRegistry.DRAG, "item.star_crucifix.drag", files("crucifix/drag", 2));
        add(CrucifixRegistry.BANISH, "item.star_crucifix.banish", files("crucifix/banish", 1));
        add(CrucifixRegistry.BREAK, "item.star_crucifix.break", files("crucifix/break", 2));
        add(CrucifixRegistry.SHATTER, "item.star_crucifix.shatter", files("crucifix/shatter", 2));
        add(CrucifixRegistry.STRAIN, "item.star_crucifix.strain", files("crucifix/strain", 2, s -> s.volume(0.9F)));
        add(CrucifixRegistry.ROAR, "item.star_crucifix.roar", files("crucifix/roar", 2));
        add(CrucifixRegistry.CRACK, "item.star_crucifix.crack", files("crucifix/crack", 2));
        add(CrucifixRegistry.EXPLODE, "item.star_crucifix.explode", files("crucifix/explode", 1));
    }

    private void add(DeferredHolder<SoundEvent, SoundEvent> event, String subtitle, SoundDefinition.Sound... sounds) {
        add(event, definition().subtitle(SUBTITLE_PREFIX + subtitle).with(sounds));
    }

    private static SoundDefinition.Sound[] files(String path, int count) {
        return files(path, count, UnaryOperator.identity());
    }

    private static SoundDefinition.Sound[] files(String path, int count, UnaryOperator<SoundDefinition.Sound> tweak) {
        SoundDefinition.Sound[] sounds = new SoundDefinition.Sound[count];
        for (int i = 0; i < count; i++) {
            sounds[i] = tweak.apply(sound(ThaumaturgeTechCompats.id(path + (i + 1))));
        }
        return sounds;
    }
}
