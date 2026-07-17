package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.common.register.IEItems;
import net.claustra01.tfie.TFIE;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.DeadDoubleCropBlock;
import net.dries007.tfc.common.blocks.crop.DoubleCropBlock;
import net.dries007.tfc.common.blocks.crop.WildDoubleCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class HempCrop {
    public static final float NITROGEN = 0.6f;
    public static final float PHOSPHOROUS = 0.2f;
    public static final float POTASSIUM = 0.6f;

    private static ExtendedProperties deadProperties() {
        return ExtendedProperties.of(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .strength(0.4f)
            .sound(SoundType.CROP)
            .flammable(60, 30)
            .pushReaction(PushReaction.DESTROY);
    }

    public static Block createCrop() {
        final IntegerProperty age = TFCBlockStateProperties.getAgeProperty(4);
        return new DoubleCropBlock(
            deadProperties().blockEntity(ModBlockEntities.CROP).serverTicks(CropBlockEntity::serverTickBottomPartOnly),
            3,
            4,
            ModBlocks.DEAD_HEMP,
            IEItems.Misc.HEMP_SEEDS,
            NITROGEN,
            PHOSPHOROUS,
            POTASSIUM,
            climateRange()
        ) {
            @Override
            public IntegerProperty getAgeProperty() {
                return age;
            }
        };
    }

    public static Block createDeadCrop() {
        return new DeadDoubleCropBlock(deadProperties(), climateRange());
    }

    public static Block createWildCrop() {
        return new WildDoubleCropBlock(deadProperties().randomTicks());
    }

    public static java.util.function.Supplier<ClimateRange> climateRange() {
        return ClimateRange.MANAGER.getReference(TFIE.id("crop/hemp"));
    }

    private HempCrop() {}
}
