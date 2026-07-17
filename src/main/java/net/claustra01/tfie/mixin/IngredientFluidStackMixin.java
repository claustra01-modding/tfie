package net.claustra01.tfie.mixin;

import blusunrize.immersiveengineering.common.crafting.fluidaware.IngredientFluidStack;
import java.util.Objects;
import java.util.stream.Stream;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.recipes.RecipeHelpers;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IngredientFluidStack.class)
public abstract class IngredientFluidStackMixin {
    @Shadow @Final
    private SizedFluidIngredient fluidIngredient;

    @Inject(method = "getItems", at = @At("HEAD"), cancellable = true)
    private void tfie$getTFCFluidContainers(CallbackInfoReturnable<Stream<ItemStack>> callback) {
        callback.setReturnValue(RecipeHelpers.stream(fluidIngredient)
            .flatMap(fluid -> Helpers.allItems(TFCTags.Items.FLUID_ITEM_INGREDIENT_EMPTY_CONTAINERS)
                .map(item -> {
                    ItemStack stack = new ItemStack(item);
                    IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
                    if (handler != null) {
                        handler.fill(new FluidStack(fluid, Integer.MAX_VALUE), IFluidHandler.FluidAction.EXECUTE);
                        FluidStack content = handler.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE);
                        if (content.getFluid() == fluid && content.getAmount() >= fluidIngredient.amount()) {
                            return handler.getContainer();
                        }
                    }
                    return null;
                }))
            .filter(Objects::nonNull));
    }
}
