package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.api.crafting.StackWithChance;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import blusunrize.immersiveengineering.api.crafting.TagOutputList;
import blusunrize.immersiveengineering.common.register.IEMultiblockLogic;
import malte0811.dualcodecs.DualCodecs;
import malte0811.dualcodecs.DualCompositeMapCodecs;
import malte0811.dualcodecs.DualMapCodec;
import net.claustra01.tfie.TFIE;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class HeatArcFurnaceRecipe extends ArcFurnaceRecipe {
    public static final ResourceLocation ID = TFIE.id("arc_furnace");
    public static final Serializer SERIALIZER = new Serializer();

    private final float outputTemperature;

    public HeatArcFurnaceRecipe(
        TagOutputList output,
        TagOutput slag,
        List<StackWithChance> secondaryOutputs,
        int time,
        int energy,
        IngredientWithSize input,
        List<IngredientWithSize> additives,
        String specialRecipeType,
        float outputTemperature
    ) {
        super(output, slag, secondaryOutputs, time, energy, input, additives, specialRecipeType);
        this.outputTemperature = outputTemperature;
        for (ItemStack stack : output.get()) {
            HeatRecipeSupport.setDisplayTemperature(stack, outputTemperature);
        }
    }

    @Override
    public NonNullList<ItemStack> generateActualOutput(
        ItemStack input,
        NonNullList<ItemStack> additives,
        long seed
    ) {
        final NonNullList<ItemStack> outputs = super.generateActualOutput(input, additives, seed);
        final int mainOutputCount = Math.min(output.get().size(), outputs.size());
        for (int index = 0; index < mainOutputCount; index++) {
            HeatRecipeSupport.setTemperature(outputs.get(index), outputTemperature);
        }
        return outputs;
    }

    @Override
    protected IERecipeSerializer<ArcFurnaceRecipe> getIESerializer() {
        return SERIALIZER;
    }

    public static final class Serializer extends IERecipeSerializer<ArcFurnaceRecipe> {
        private static final DualMapCodec<RegistryFriendlyByteBuf, ArcFurnaceRecipe> CODECS =
            DualCompositeMapCodecs.composite(
                TagOutputList.CODEC.fieldOf("results"), recipe -> recipe.output,
                TagOutput.CODECS.optionalFieldOf("slag", TagOutput.EMPTY), recipe -> recipe.slag,
                CHANCE_LIST_CODECS.optionalFieldOf("secondaries", List.of()),
                recipe -> recipe.secondaryOutputs,
                DualCodecs.INT.fieldOf("time"), MultiblockRecipe::getBaseTime,
                DualCodecs.INT.fieldOf("energy"), MultiblockRecipe::getBaseEnergy,
                IngredientWithSize.CODECS.fieldOf("input"), recipe -> recipe.input,
                IngredientWithSize.CODECS.listOf().fieldOf("additives"), recipe -> recipe.additives,
                DualCodecs.STRING.optionalFieldOf("specialRecipeType", ""),
                recipe -> recipe.specialRecipeType,
                HeatRecipeSupport.TEMPERATURE_CODECS.fieldOf("output_temperature"),
                recipe -> ((HeatArcFurnaceRecipe) recipe).outputTemperature,
                HeatArcFurnaceRecipe::new);

        @Override
        protected DualMapCodec<RegistryFriendlyByteBuf, ArcFurnaceRecipe> codecs() {
            return CODECS;
        }

        @Override
        public ItemStack getIcon() {
            return IEMultiblockLogic.ARC_FURNACE.iconStack();
        }
    }
}
