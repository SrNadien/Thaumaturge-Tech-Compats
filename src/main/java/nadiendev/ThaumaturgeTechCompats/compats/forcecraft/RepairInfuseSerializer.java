package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.forcecraft.upgrade.UpgradeBookTier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class RepairInfuseSerializer {
    private static final MapCodec<RepairInfuseRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("center").forGetter(RepairInfuseRecipe::getCenter),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(RepairInfuseRecipe::getIngredient),
            UpgradeBookTier.CODEC.fieldOf("tier").forGetter(RepairInfuseRecipe::getTier),
            Codec.INT.fieldOf("time").forGetter(RepairInfuseRecipe::getTime)
    ).apply(instance, RepairInfuseRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, RepairInfuseRecipe> STREAM_CODEC = StreamCodec.of(
            RepairInfuseSerializer::toNetwork, RepairInfuseSerializer::fromNetwork);

    private RepairInfuseSerializer() {
    }

    public static RecipeSerializer<RepairInfuseRecipe> create() {
        return new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, RepairInfuseRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getCenter());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
        buffer.writeEnum(recipe.getTier());
        buffer.writeInt(recipe.getTime());
    }

    private static RepairInfuseRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient center = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        UpgradeBookTier tier = buffer.readEnum(UpgradeBookTier.class);
        int time = buffer.readInt();
        return new RepairInfuseRecipe(center, ingredient, tier, time);
    }
}
