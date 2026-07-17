package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.api.IETags;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
import blusunrize.immersiveengineering.common.entities.ChemthrowerShotEntity;
import java.util.List;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blocks.crop.DeadCropBlock;
import net.dries007.tfc.common.blocks.crop.ICropBlock;
import net.dries007.tfc.common.blocks.crop.WildCropBlock;
import net.dries007.tfc.common.blocks.plant.fruit.SeasonalPlantBlock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public final class HerbicideEffects {
    private HerbicideEffects() {}

    public static void register() {
        ChemthrowerHandler.registerEffect(IETags.fluidHerbicide, new ChemthrowerHandler.ChemthrowerEffect() {
            @Override
            public void applyToEntity(net.minecraft.world.entity.LivingEntity target,
                                      net.minecraft.world.entity.player.Player shooter,
                                      net.minecraft.world.entity.Entity projectile,
                                      net.minecraft.world.item.ItemStack thrower,
                                      net.minecraft.world.level.material.Fluid fluid) {}

            @Override
            public void applyToBlock(Level level, net.minecraft.world.phys.HitResult hitResult,
                                     net.minecraft.world.entity.player.Player shooter,
                                     net.minecraft.world.entity.Entity projectile,
                                     net.minecraft.world.item.ItemStack thrower,
                                     net.minecraft.world.level.material.Fluid fluid) {
                if (!(hitResult instanceof BlockHitResult result)) return;

                BlockPos pos = result.getBlockPos();
                BlockState hit = level.getBlockState(pos);
                BlockPos above = pos.above();
                BlockState stateAbove = level.getBlockState(above);
                Block blockAbove = stateAbove.getBlock();

                for (SoilBlockType.Variant soil : SoilBlockType.Variant.values()) {
                    if (hit.is(soil.getBlock(SoilBlockType.GRASS).get())
                        || hit.is(soil.getBlock(SoilBlockType.FARMLAND).get())) {
                        killPlant(level, above, stateAbove, blockAbove, true);
                        level.setBlockAndUpdate(pos, soil.getBlock(SoilBlockType.DIRT).get().defaultBlockState());
                    } else if (hit.is(soil.getBlock(SoilBlockType.CLAY_GRASS).get())) {
                        killPlant(level, above, stateAbove, blockAbove, false);
                        level.setBlockAndUpdate(pos, soil.getBlock(SoilBlockType.CLAY).get().defaultBlockState());
                    }
                }

                if (hit.is(BlockTags.LEAVES)) {
                    level.removeBlock(pos, false);
                } else if (hit.getBlock() instanceof SnowyDirtBlock || hit.getBlock() instanceof FarmBlock) {
                    level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                    if (blockAbove instanceof BushBlock) level.removeBlock(above, false);
                }

                AABB area = new AABB(pos).inflate(0.25D);
                List<ChemthrowerShotEntity> shots = level.getEntitiesOfClass(ChemthrowerShotEntity.class, area);
                shots.forEach(ChemthrowerShotEntity::discard);
            }
        });
    }

    private static void killPlant(Level level, BlockPos pos, BlockState state, Block block, boolean crops) {
        if (crops && block instanceof ICropBlock crop && level.getBlockEntity(pos) instanceof CropBlockEntity cropEntity) {
            crop.die(level, pos, state, cropEntity.getGrowth() >= 1F);
            level.destroyBlock(pos, true);
        } else if (block instanceof DeadCropBlock || block instanceof WildCropBlock) {
            level.destroyBlock(pos, true);
        } else if (block instanceof BushBlock && !(block instanceof SeasonalPlantBlock)) {
            level.removeBlock(pos, false);
        }
    }
}
