package nadiendev.ThaumaturgeTechCompats.compats.tags;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TagAspectTable {
    private TagAspectTable() {
    }

    public static List<TagAspectEntry> entries(Set<String> alreadyDefined) {
        List<TagAspectEntry> entries = new ArrayList<>();
        for (TagMaterial material : TagMaterials.all()) {
            for (TagForm form : TagForm.values()) {
                if (!form.appliesTo(material.kind())) {
                    continue;
                }
                String key = form.tagKey(material.id());
                if (alreadyDefined.contains(key)) {
                    continue;
                }
                entries.add(new TagAspectEntry(key, form.scale(material.aspects()), List.of(CompatToggles.TAG_ASPECTS, form.toggle()), List.of()));
            }
        }
        for (Map.Entry<String, List<AspectPart>> food : TagFoods.all().entrySet()) {
            if (alreadyDefined.contains(food.getKey())) {
                continue;
            }
            entries.add(new TagAspectEntry(food.getKey(), food.getValue(), List.of(CompatToggles.TAG_ASPECTS, CompatToggles.TAG_ASPECTS_FOODS), List.of()));
        }
        return entries;
    }
}
