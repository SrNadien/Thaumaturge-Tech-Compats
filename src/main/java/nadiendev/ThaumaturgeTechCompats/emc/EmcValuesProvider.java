package nadiendev.ThaumaturgeTechCompats.emc;

import com.yaskulsky.equivox.api.data.CustomConversionBuilder;
import com.yaskulsky.equivox.api.data.CustomConversionProvider;
import com.yaskulsky.equivox.api.nss.NSSItem;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EmcValuesProvider extends CustomConversionProvider {
    public EmcValuesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ThaumaturgeTechCompats.MOD_ID);
    }

    @Override
    protected void addCustomConversions(HolderLookup.Provider registries) {
        fill(createConversionBuilder(ThaumaturgeTechCompats.id("metals"))
                .comment("Base EMC of the Thaumaturge metals: thaumium between iron and gold, alchemical brass between gold and diamond, void metal between diamond and netherite."), EmcValues.METALS);
        fill(createConversionBuilder(ThaumaturgeTechCompats.id("materials"))
                .comment("Base EMC of the Thaumaturge raw materials that no recipe produces."), EmcValues.MATERIALS);
        fill(createConversionBuilder(ThaumaturgeTechCompats.id("planetary"))
                .comment("Base EMC of the first planetary endest pearl and star crucifix, whose catalysts have no EMC. Higher levels derive from these."), EmcValues.PLANETARY);
    }

    private static void fill(CustomConversionBuilder builder, List<EmcValues.Value> values) {
        for (EmcValues.Value value : values) {
            builder.before(NSSItem.createItem(Identifier.parse(value.item())), value.emc());
        }
    }
}
