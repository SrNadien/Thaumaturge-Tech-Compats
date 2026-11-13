package nadiendev.ThaumaturgeTechCompats.compats.tags;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AspectMath {
    private AspectMath() {
    }

    public static List<AspectPart> scale(List<AspectPart> base, int numerator, int denominator, List<AspectPart> extras) {
        Map<Asp, Integer> merged = new LinkedHashMap<>();
        for (AspectPart part : base) {
            int value = Math.max(1, Math.round((float) part.amount() * numerator / denominator));
            merged.merge(part.aspect(), value, Integer::sum);
        }
        for (AspectPart part : extras) {
            merged.merge(part.aspect(), part.amount(), Integer::sum);
        }
        List<AspectPart> result = new ArrayList<>();
        merged.forEach((aspect, amount) -> result.add(new AspectPart(aspect, amount)));
        return result;
    }

    public static List<AspectPart> of(AspectPart... parts) {
        return List.of(parts);
    }
}
