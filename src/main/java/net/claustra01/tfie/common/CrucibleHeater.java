package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.api.tool.ExternalHeaterHandler;
import net.claustra01.tfie.config.TFIEConfig;
import net.claustra01.tfie.mixin.CrucibleBlockEntityAccessor;
import net.dries007.tfc.common.blockentities.CrucibleBlockEntity;
import net.minecraft.core.Direction;

public record CrucibleHeater(CrucibleBlockEntity crucible, Direction side)
    implements ExternalHeaterHandler.IExternalHeatable {

    @Override
    public int doHeatTick(int energyAvailable, boolean redstone) {
        int energyPerTick = TFIEConfig.CRUCIBLE_EXTERNAL_HEATER_FE_PER_TICK.get();
        if (energyAvailable >= energyPerTick && !redstone) {
            setTargetTemperature(TFIEConfig.CRUCIBLE_EXTERNAL_HEATER_TEMPERATURE.get());
            return energyPerTick;
        }
        setTargetTemperature(0);
        return 0;
    }

    private void setTargetTemperature(float temperature) {
        CrucibleBlockEntityAccessor accessor = (CrucibleBlockEntityAccessor) crucible;
        accessor.tfie$setTargetTemperature(temperature);
        accessor.tfie$setTargetTemperatureStabilityTicks(5);
        crucible.markForSync();
    }
}
