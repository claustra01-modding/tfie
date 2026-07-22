package net.claustra01.tfie.common;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import malte0811.dualcodecs.DualCodec;
import net.dries007.tfc.common.component.heat.HeatCapability;
import net.dries007.tfc.common.component.heat.IHeat;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;

public final class HeatRecipeSupport {
    public static final DualCodec<ByteBuf, Float> TEMPERATURE_CODECS = new DualCodec<>(
        Codec.floatRange(0, Float.MAX_VALUE),
        ByteBufCodecs.FLOAT);

    private HeatRecipeSupport() {
    }

    public static void setDisplayTemperature(ItemStack stack, float temperature) {
        final IHeat heat = HeatCapability.get(stack);
        if (heat != null) {
            // JEIでは温度tooltipを表示しつつ、表示中に温度が実質低下しないようにする。
            heat.setHeatCapacity(Float.MAX_VALUE);
            heat.setTemperature(temperature);
        }
    }

    public static void setTemperature(ItemStack stack, float temperature) {
        final IHeat heat = HeatCapability.get(stack);
        if (heat != null) {
            // JEI表示用の最大熱容量を解除し、通常のTFC冷却処理へ戻してから温度を設定する。
            heat.setHeatCapacity(0);
            heat.setTemperature(temperature);
        }
    }
}
