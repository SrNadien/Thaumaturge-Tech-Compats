package nadiendev.ThaumaturgeTechCompats.compats.create;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CreateCasings;
import nadiendev.ThaumaturgeTechCompats.compats.create.data.CreateContent;
import nadiendev.ThaumaturgeTechCompats.compats.create.seal.CreateSeals;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CreateVariants;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CreateTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private CreateTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(CreateSeals.ITEM.get(), "Create Seal");
        lang.add(CreateCasings.block(CasingTier.BRASS).get(), "Alchemical Brass Casing");
        lang.add(CreateCasings.block(CasingTier.THAUMIUM).get(), "Thaumium Casing");
        lang.add(CreateCasings.block(CasingTier.VOID).get(), "Void Metal Casing");
        for (VariantKind kind : VariantKind.values()) {
            for (CasingTier tier : CasingTier.values()) {
                lang.add(CreateVariants.block(kind, tier).get(), englishTier(tier) + " " + englishKind(kind));
            }
        }
        lang.add("gui." + MOD + ".seal.setting.insert", "Insert carried items");
        lang.add("gui." + MOD + ".seal.setting.extract", "Extract items");
        lang.add("gui." + MOD + ".seal.setting.operate", "Operate the block (click)");
        lang.add("gui." + MOD + ".seal.setting.sneak", "Simulate sneaky click");
        entry(lang, CreateContent.SEAL, "Create Seal",
                "Create's machines are fed by belts, depots and basins, and none of them take orders from a golem. A control seal that speaks their language of inventories and levers might change that.",
                "The seal sits on a block of Create and lets a golem work it.<BR>It inserts the items the golem carries into the block, takes items out of it, and can also click the block, which turns hand cranks and valve handles.<BR>The filter picks the items for every task, and each task can be switched off in the seal's options.<PAGE>Create Seal<DIV>It is infused over a Use Seal with a cogwheel, a brass sheet, andesite alloy and a simple mechanism.");
        entry(lang, CreateContent.BRASS, "Alchemical Brass Machinery",
                "A machine is only as fast as the hand that drives it. If I wrap a Create casing in alchemical brass and build the machines of Create around it, an aura node close by should be able to speed up the machinery inside.",
                "The casing hums beside a node. Built into a machine of Create it makes a variant of that machine: a mechanical press, mixer, saw or drill, a deployer, millstone, basin, spout, item drain or depot that works exactly like the original.<BR>While an aura node is near, the variant works many times faster. Away from every node it behaves as the plain machine.<PAGE>Alchemical Brass Casing<DIV>It is made at the arcane workbench from a brass casing of Create and four brass plates.<PAGE>Machine variants<DIV>Each variant is infused over the matching machine of Create, with an alchemical brass casing and two brass plates.");
        entry(lang, CreateContent.THAUMIUM, "Thaumium Machinery",
                "Thaumium drinks magic far more readily than brass. A casing forged from it, and the machines built with it, should answer a node at a greater distance.",
                "The thaumium variants reach nodes from farther away than the alchemical brass ones.<PAGE>Thaumium Casing<DIV>It is infused over an alchemical brass casing with four thaumium plates and a simple mechanism.<PAGE>Machine variants<DIV>Each variant is infused over the alchemical brass variant of the same machine, with a thaumium casing, two thaumium plates and a simple mechanism.");
        entry(lang, CreateContent.VOID, "Void Metal Machinery",
                "Void metal swallows whatever feeds it. A casing made of it, and the machines built with it, should pull the most out of a node.",
                "The void metal variants are the finest, and answer a node from the greatest distance.<PAGE>Void Metal Casing<DIV>It is infused over a thaumium casing with four void metal plates and a complex mechanism.<PAGE>Machine variants<DIV>Each variant is infused over the thaumium variant of the same machine, with a void metal casing, two void metal plates and a complex mechanism.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(CreateSeals.ITEM.get(), "Sello de Create");
        lang.add(CreateCasings.block(CasingTier.BRASS).get(), "Carcasa de latón alquímico");
        lang.add(CreateCasings.block(CasingTier.THAUMIUM).get(), "Carcasa de taumio");
        lang.add(CreateCasings.block(CasingTier.VOID).get(), "Carcasa de metal del vacío");
        for (VariantKind kind : VariantKind.values()) {
            for (CasingTier tier : CasingTier.values()) {
                lang.add(CreateVariants.block(kind, tier).get(), spanishKind(kind) + " de " + spanishTier(tier));
            }
        }
        lang.add("gui." + MOD + ".seal.setting.insert", "Insertar los objetos que lleva");
        lang.add("gui." + MOD + ".seal.setting.extract", "Extraer objetos");
        lang.add("gui." + MOD + ".seal.setting.operate", "Accionar el bloque (clic)");
        lang.add("gui." + MOD + ".seal.setting.sneak", "Simular clic agachado");
        entry(lang, CreateContent.SEAL, "Sello de Create",
                "Las máquinas de Create se alimentan con cintas, depósitos y cuencos, y ninguna obedece a un gólem. Un sello de control que hable su idioma de inventarios y palancas podría cambiarlo.",
                "El sello se coloca sobre un bloque de Create y deja que un gólem lo trabaje.<BR>Inserta en el bloque los objetos que lleva el gólem, saca objetos de él y también puede hacer clic en el bloque, lo que gira manivelas y manillas de válvula.<BR>El filtro elige los objetos de cada tarea, y cada tarea se puede desactivar en las opciones del sello.<PAGE>Sello de Create<DIV>Se infunde sobre un sello de uso con un engranaje, una lámina de latón, aleación de andesita y un mecanismo simple.");
        entry(lang, CreateContent.BRASS, "Maquinaria de latón alquímico",
                "Una máquina es tan rápida como la mano que la mueve. Si envuelvo una carcasa de Create en latón alquímico y construyo con ella las máquinas de Create, un nodo de aura cercano debería poder acelerar la maquinaria de dentro.",
                "La carcasa zumba junto a un nodo. Integrada en una máquina de Create da una variante de esa máquina: una prensa, mezcladora, sierra o taladro mecánicos, un desplegador, muela, cuenca, surtidor, escurridor o depósito que funciona igual que el original.<BR>Mientras haya un nodo de aura cerca, la variante trabaja muchas veces más rápido. Lejos de todo nodo se comporta como la máquina normal.<PAGE>Carcasa de latón alquímico<DIV>Se fabrica en la mesa de trabajo arcana con una carcasa de latón de Create y cuatro placas de latón.<PAGE>Variantes de máquina<DIV>Cada variante se infunde sobre la máquina de Create correspondiente, con una carcasa de latón alquímico y dos placas de latón.");
        entry(lang, CreateContent.THAUMIUM, "Maquinaria de taumio",
                "El taumio bebe la magia con más facilidad que el latón. Una carcasa forjada con él, y las máquinas construidas con ella, deberían responder a un nodo desde más lejos.",
                "Las variantes de taumio alcanzan nodos desde más lejos que las de latón alquímico.<PAGE>Carcasa de taumio<DIV>Se infunde sobre una carcasa de latón alquímico con cuatro placas de taumio y un mecanismo simple.<PAGE>Variantes de máquina<DIV>Cada variante se infunde sobre la variante de latón alquímico de la misma máquina, con una carcasa de taumio, dos placas de taumio y un mecanismo simple.");
        entry(lang, CreateContent.VOID, "Maquinaria de metal del vacío",
                "El metal del vacío se traga todo lo que lo alimenta. Una carcasa hecha con él, y las máquinas construidas con ella, deberían sacar lo máximo de un nodo.",
                "Las variantes de metal del vacío son las mejores y responden a un nodo desde la mayor distancia.<PAGE>Carcasa de metal del vacío<DIV>Se infunde sobre una carcasa de taumio con cuatro placas de metal del vacío y un mecanismo complejo.<PAGE>Variantes de máquina<DIV>Cada variante se infunde sobre la variante de taumio de la misma máquina, con una carcasa de metal del vacío, dos placas de metal del vacío y un mecanismo complejo.");
    }

    private static String englishTier(CasingTier tier) {
        return switch (tier) {
            case BRASS -> "Alchemical Brass";
            case THAUMIUM -> "Thaumium";
            case VOID -> "Void Metal";
        };
    }

    private static String spanishTier(CasingTier tier) {
        return switch (tier) {
            case BRASS -> "latón alquímico";
            case THAUMIUM -> "taumio";
            case VOID -> "metal del vacío";
        };
    }

    private static String englishKind(VariantKind kind) {
        return switch (kind) {
            case PRESS -> "Mechanical Press";
            case MIXER -> "Mechanical Mixer";
            case SAW -> "Mechanical Saw";
            case DRILL -> "Mechanical Drill";
            case DEPLOYER -> "Deployer";
            case MILLSTONE -> "Millstone";
            case BASIN -> "Basin";
            case SPOUT -> "Spout";
            case ITEM_DRAIN -> "Item Drain";
            case DEPOT -> "Depot";
        };
    }

    private static String spanishKind(VariantKind kind) {
        return switch (kind) {
            case PRESS -> "Prensa mecánica";
            case MIXER -> "Mezcladora mecánica";
            case SAW -> "Sierra mecánica";
            case DRILL -> "Taladro mecánico";
            case DEPLOYER -> "Desplegador";
            case MILLSTONE -> "Muela";
            case BASIN -> "Cuenca";
            case SPOUT -> "Surtidor";
            case ITEM_DRAIN -> "Escurridor";
            case DEPOT -> "Depósito";
        };
    }

    private static void entry(LanguageProvider lang, String name, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + name;
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
