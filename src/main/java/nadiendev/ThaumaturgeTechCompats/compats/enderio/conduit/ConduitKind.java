package nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit;

import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;

public enum ConduitKind {
    ENERGY("energy", "enderio:energy", "enderio:ender_energy", "enderio:vibrant_alloy_ingot", "Energy", "de energía",
            new int[]{750_000, 3_000_000, 12_000_000}, null,
            "thaumaturge:potentia", "thaumaturge:machina", "thaumaturge:motus"),
    ITEM("item", "enderio:item", "enderio:ender_item", "enderio:pulsating_alloy_ingot", "Item", "de objetos",
            new int[]{64, 128, 256}, new int[]{5, 4, 2},
            "thaumaturge:motus", "thaumaturge:permutatio", "thaumaturge:machina"),
    FLUID("fluid", "enderio:fluid", "enderio:ender_fluid", "enderio:vibrant_crystal", "Fluid", "de fluidos",
            new int[]{32_000, 128_000, 512_000}, null,
            "thaumaturge:aqua", "thaumaturge:motus", "thaumaturge:machina");

    private final String id;
    private final String type;
    private final String base;
    private final String extra;
    private final String english;
    private final String spanish;
    private final int[] rates;
    private final int[] ticks;
    private final String[] aspects;

    ConduitKind(String id, String type, String base, String extra, String english, String spanish, int[] rates, int[] ticks, String... aspects) {
        this.id = id;
        this.type = type;
        this.base = base;
        this.extra = extra;
        this.english = english;
        this.spanish = spanish;
        this.rates = rates;
        this.ticks = ticks;
        this.aspects = aspects;
    }

    public String id() {
        return id;
    }

    public String type() {
        return type;
    }

    public String base() {
        return base;
    }

    public String extra() {
        return extra;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public boolean hasTicks() {
        return ticks != null;
    }

    public int defaultRate(EnderIOTier tier) {
        return rates[tier.ordinal()];
    }

    public int defaultTicks(EnderIOTier tier) {
        return ticks[tier.ordinal()];
    }

    public String[] aspects() {
        return aspects;
    }

    public String name(EnderIOTier tier) {
        return tier.id() + "_" + id;
    }

    public String itemName(EnderIOTier tier) {
        return name(tier) + "_conduit";
    }
}
