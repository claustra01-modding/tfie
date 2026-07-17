package net.claustra01.tfie.compat.patchouli;

import net.dries007.tfc.common.recipes.AnvilRecipe;
import net.dries007.tfc.common.recipes.TFCRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

@SuppressWarnings("unused")
public final class TriAnvilComponent extends TriInputOutputComponent<AnvilRecipe> {
    @Override
    protected RecipeType<AnvilRecipe> getRecipeType() {
        return TFCRecipeTypes.ANVIL.get();
    }

    @Override
    Ingredient getIngredient(AnvilRecipe recipe) {
        return recipe.getInput();
    }

    @Override
    ItemStack getOutput(AnvilRecipe recipe) {
        return recipe.getResultItem(null);
    }
}
