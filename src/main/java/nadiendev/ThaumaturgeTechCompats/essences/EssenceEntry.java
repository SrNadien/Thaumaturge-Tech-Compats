package nadiendev.ThaumaturgeTechCompats.essences;

import nadiendev.ThaumaturgeTechCompats.compats.tags.AspectPart;

import java.util.List;

public record EssenceEntry(String key, List<AspectPart> base, List<AspectPart> essences) {
}
