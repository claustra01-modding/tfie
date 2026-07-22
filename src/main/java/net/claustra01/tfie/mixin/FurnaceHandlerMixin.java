package net.claustra01.tfie.mixin;

import blusunrize.immersiveengineering.api.crafting.IESerializableRecipe;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.FurnaceHandler;
import net.claustra01.tfie.common.HeatBlastFurnaceRecipe;
import net.claustra01.tfie.common.HeatRecipeSupport;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceHandler.class)
public abstract class FurnaceHandlerMixin<R extends IESerializableRecipe> {
    @Unique
    private float tfie$outputTemperature = Float.NaN;

    @Inject(method = "doRecipeIO", at = @At("HEAD"))
    private void tfie$captureOutputTemperature(
        FurnaceHandler.IFurnaceEnvironment<R> environment,
        CallbackInfo callback
    ) {
        final R recipe = environment.getRecipeForInput();
        tfie$outputTemperature = recipe instanceof HeatBlastFurnaceRecipe heated
            ? heated.getOutputTemperature()
            : Float.NaN;
    }

    @Inject(method = "doRecipeIO", at = @At("TAIL"))
    private void tfie$applyOutputTemperature(
        FurnaceHandler.IFurnaceEnvironment<R> environment,
        CallbackInfo callback
    ) {
        if (!Float.isNaN(tfie$outputTemperature)) {
            final ItemStack output = environment.getInventory().getStackInSlot(2);
            HeatRecipeSupport.setTemperature(output, tfie$outputTemperature);
            tfie$outputTemperature = Float.NaN;
        }
    }
}
