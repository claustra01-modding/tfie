package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.api.crafting.ClocheRecipe;
import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import blusunrize.immersiveengineering.mixin.accessors.CropBlockAccess;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import java.util.Collection;
import malte0811.dualcodecs.DualCodecs;
import malte0811.dualcodecs.DualCompositeMapCodecs;
import malte0811.dualcodecs.DualMapCodec;
import net.claustra01.tfie.TFIE;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.crop.DoubleCropBlock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.joml.Vector3f;

public final class ClocheRenderFunctions {
    private ClocheRenderFunctions() {}

    public static void register() {
        ClocheRenderFunction.RENDER_FUNCTION_FACTORIES.put(
            TFIE.id("doublecroptfc"), DoubleCrop.CODEC);
    }

    public static void initializeSoils() {
        for (SoilBlockType.Variant soil : SoilBlockType.Variant.values()) {
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                TerraFirmaCraft.MOD_ID, "block/farmland/" + soil.name().toLowerCase());
            ClocheRecipe.registerSoilTexture(
                Ingredient.of(TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(soil)), texture);
        }
    }

    public static final class DoubleCrop implements ClocheRenderFunction {
        public static final DualMapCodec<? super RegistryFriendlyByteBuf, DoubleCrop> CODEC =
            DualCompositeMapCodecs.composite(
                DualCodecs.registryEntry(BuiltInRegistries.BLOCK).fieldOf("block"), value -> value.block,
                DualCodecs.INT.fieldOf("doublingAge"), value -> value.doublingAge,
                DoubleCrop::new);

        private final Block block;
        private final IntegerProperty ageProperty;
        private final int maxAge;
        private final int doublingAge;

        public DoubleCrop(Block block, int doublingAge) {
            this.block = block;
            Pair<IntegerProperty, Integer> age = findAge(block);
            this.ageProperty = age.getFirst();
            this.maxAge = age.getSecond();
            this.doublingAge = doublingAge;
        }

        @Override
        public float getScale(ItemStack seed, float growth) {
            return 0.6875F;
        }

        @Override
        public Collection<Pair<BlockState, Transformation>> getBlocks(ItemStack stack, float growth) {
            int age = Math.min(maxAge, Math.round(maxAge * growth));
            BlockState state = block.defaultBlockState().setValue(ageProperty, age);
            if (age >= doublingAge) {
                return ImmutableList.of(
                    Pair.of(state, new Transformation(null)),
                    Pair.of(state.setValue(DoubleCropBlock.PART, DoubleCropBlock.Part.TOP),
                        new Transformation(new Vector3f(0, 1, 0), null, null, null)));
            }
            return ImmutableList.of(Pair.of(state, new Transformation(null)));
        }

        @Override
        public DualMapCodec<? super RegistryFriendlyByteBuf, ? extends ClocheRenderFunction> codec() {
            return CODEC;
        }

        private static Pair<IntegerProperty, Integer> findAge(Block block) {
            if (block instanceof CropBlock crop) {
                return Pair.of(((CropBlockAccess) crop).invokeGetAgeProperty(), crop.getMaxAge());
            }
            for (Property<?> property : block.defaultBlockState().getProperties()) {
                if ("age".equals(property.getName()) && property instanceof IntegerProperty ageProperty) {
                    int max = ageProperty.getPossibleValues().stream().max(Integer::compareTo).orElse(-1);
                    if (max > 0) return Pair.of(ageProperty, max);
                }
            }
            throw new IllegalArgumentException(block.getDescriptionId() + " is not a crop block");
        }
    }
}
