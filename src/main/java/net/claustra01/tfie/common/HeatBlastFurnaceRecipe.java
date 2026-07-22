package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import blusunrize.immersiveengineering.common.register.IEMultiblockLogic;
import malte0811.dualcodecs.DualCodecs;
import malte0811.dualcodecs.DualCompositeMapCodecs;
import malte0811.dualcodecs.DualMapCodec;
import net.claustra01.tfie.TFIE;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class HeatBlastFurnaceRecipe extends BlastFurnaceRecipe {
    public static final ResourceLocation ID = TFIE.id("blast_furnace");
    public static final Serializer SERIALIZER = new Serializer();

    private final float outputTemperature;

    public HeatBlastFurnaceRecipe(
        TagOutput output,
        IngredientWithSize input,
        int time,
        TagOutput slag,
        float outputTemperature
    ) {
        super(output, input, time, slag);
        this.outputTemperature = outputTemperature;
        HeatRecipeSupport.setDisplayTemperature(output.get(), outputTemperature);
    }

    public float getOutputTemperature() {
        return outputTemperature;
    }

    @Override
    protected IERecipeSerializer<BlastFurnaceRecipe> getIESerializer() {
        return SERIALIZER;
    }

    public static final class Serializer extends IERecipeSerializer<BlastFurnaceRecipe> {
        private static final DualMapCodec<RegistryFriendlyByteBuf, BlastFurnaceRecipe> CODECS =
            DualCompositeMapCodecs.composite(
                TagOutput.CODECS.fieldOf("result"), recipe -> recipe.output,
                IngredientWithSize.CODECS.fieldOf("input"), recipe -> recipe.input,
                DualCodecs.INT.optionalFieldOf("time", 200), recipe -> recipe.time,
                optionalItemOutput("slag"), recipe -> recipe.slag,
                HeatRecipeSupport.TEMPERATURE_CODECS.fieldOf("output_temperature"),
                recipe -> ((HeatBlastFurnaceRecipe) recipe).outputTemperature,
                HeatBlastFurnaceRecipe::new);

        @Override
        protected DualMapCodec<RegistryFriendlyByteBuf, BlastFurnaceRecipe> codecs() {
            return CODECS;
        }

        @Override
        public ItemStack getIcon() {
            return IEMultiblockLogic.BLAST_FURNACE.iconStack();
        }
    }
}
