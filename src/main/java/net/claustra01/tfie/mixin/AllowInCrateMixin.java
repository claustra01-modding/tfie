package net.claustra01.tfie.mixin;

import blusunrize.immersiveengineering.api.IEApi;
import net.claustra01.tfie.config.TFIEConfig;
import net.dries007.tfc.common.component.size.ItemSizeManager;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IEApi.class)
public abstract class AllowInCrateMixin {
    @Inject(method = "isAllowedInCrate", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tfie$limitItemSize(ItemStack stack, CallbackInfoReturnable<Boolean> callback) {
        if (!ItemSizeManager.get(stack).getSize(stack)
            .isEqualOrSmallerThan(TFIEConfig.CRATE_MAXIMUM_ITEM_SIZE.get())) {
            callback.setReturnValue(false);
        }
    }
}
