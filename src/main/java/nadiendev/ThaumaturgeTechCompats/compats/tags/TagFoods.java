package nadiendev.ThaumaturgeTechCompats.compats.tags;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TagFoods {
    private static final Map<String, List<AspectPart>> ALL = new LinkedHashMap<>();

    static {
        add("c:crops", Asp.HERBA.of(5), Asp.VICTUS.of(2));
        add("c:seeds", Asp.HERBA.of(2), Asp.VICTUS.of(2));
        add("c:mushrooms", Asp.HERBA.of(3), Asp.VICTUS.of(2), Asp.TENEBRAE.of(2));
        add("c:foods/fruit", Asp.HERBA.of(3), Asp.VICTUS.of(3));
        add("c:foods/berry", Asp.HERBA.of(2), Asp.VICTUS.of(3));
        add("c:foods/vegetable", Asp.HERBA.of(3), Asp.VICTUS.of(4));
        add("c:foods/bread", Asp.HERBA.of(5), Asp.VICTUS.of(5), Asp.FABRICO.of(2));
        add("c:foods/raw_meat", Asp.BESTIA.of(5), Asp.VICTUS.of(5), Asp.MORTUUS.of(2));
        add("c:foods/cooked_meat", Asp.BESTIA.of(5), Asp.VICTUS.of(8), Asp.IGNIS.of(1));
        add("c:foods/raw_fish", Asp.AQUA.of(5), Asp.BESTIA.of(3), Asp.VICTUS.of(3));
        add("c:foods/cooked_fish", Asp.AQUA.of(3), Asp.BESTIA.of(3), Asp.VICTUS.of(6), Asp.IGNIS.of(1));
    }

    private TagFoods() {
    }

    private static void add(String tag, AspectPart... parts) {
        ALL.put("#" + tag, List.of(parts));
    }

    public static Map<String, List<AspectPart>> all() {
        return ALL;
    }
}
