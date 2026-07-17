package net.claustra01.tfie.common;

import net.claustra01.tfie.TFIE;
import net.dries007.tfc.common.recipes.INoopInputRecipe;
import net.dries007.tfc.common.recipes.RecipeSerializerImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class EmptyRecipe implements INoopInputRecipe {
    public static final ResourceLocation ID = TFIE.id("empty");
    public static final RecipeType<EmptyRecipe> TYPE = RecipeType.simple(ID);
    public static final RecipeSerializer<EmptyRecipe> SERIALIZER =
        new RecipeSerializerImpl<>(new EmptyRecipe());

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
