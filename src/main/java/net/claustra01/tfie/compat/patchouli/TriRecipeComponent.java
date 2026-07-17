package net.claustra01.tfie.compat.patchouli;

import com.google.gson.annotations.SerializedName;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.dries007.tfc.compat.patchouli.component.CustomComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import vazkii.patchouli.api.IVariable;

public abstract class TriRecipeComponent<T extends Recipe<?>> extends CustomComponent {
    @Nullable protected transient T recipe;
    @Nullable protected transient T recipe2;
    @Nullable protected transient T recipe3;
    @SerializedName("recipe") String recipeName;
    @SerializedName("recipe2") String recipeName2;
    @SerializedName("recipe3") String recipeName3;

    @Override
    public void build(int componentX, int componentY, int pageNum) {
        super.build(componentX, componentY, pageNum);
        recipe = asRecipe(recipeName, getRecipeType()).orElse(null);
        recipe2 = asRecipe(recipeName2, getRecipeType()).orElse(null);
        recipe3 = asRecipe(recipeName3, getRecipeType()).orElse(null);
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup, HolderLookup.Provider provider) {
        recipeName = lookup.apply(IVariable.wrap(recipeName, provider)).asString();
        recipeName2 = lookup.apply(IVariable.wrap(recipeName2, provider)).asString();
        recipeName3 = lookup.apply(IVariable.wrap(recipeName3, provider)).asString();
    }

    protected abstract RecipeType<T> getRecipeType();
}
