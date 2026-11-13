package nadiendev.ThaumaturgeTechCompats.datagen.provider;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Consumer;

public class ModLanguageProvider extends LanguageProvider {
    private final Consumer<LanguageProvider> translations;

    public ModLanguageProvider(PackOutput output, String locale, Consumer<LanguageProvider> translations) {
        super(output, ThaumaturgeTechCompats.MOD_ID, locale);
        this.translations = translations;
    }

    @Override
    protected void addTranslations() {
        translations.accept(this);
    }
}
