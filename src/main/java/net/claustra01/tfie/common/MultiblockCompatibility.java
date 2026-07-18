package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.api.multiblocks.BlockMatcher;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.level.block.Block;

public final class MultiblockCompatibility {
    private MultiblockCompatibility() {
    }

    public static void register() {
        final Block steelPlatedBlock = Metal.STEEL.getBlock(Metal.BlockType.BLOCK);
        BlockMatcher.addPredicate((expected, found, level, pos) -> {
            if (!expected.is(steelPlatedBlock)) {
                return BlockMatcher.Result.DEFAULT;
            }
            return found.is(steelPlatedBlock)
                ? BlockMatcher.Result.allow(3)
                : BlockMatcher.Result.deny(3);
        });
    }
}
