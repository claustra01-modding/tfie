package net.claustra01.tfie.mixin;

import net.dries007.tfc.common.blockentities.CrucibleBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CrucibleBlockEntity.class)
public interface CrucibleBlockEntityAccessor {
    @Accessor("targetTemperature")
    void tfie$setTargetTemperature(float targetTemperature);

    @Accessor("targetTemperatureStabilityTicks")
    void tfie$setTargetTemperatureStabilityTicks(int ticks);
}
