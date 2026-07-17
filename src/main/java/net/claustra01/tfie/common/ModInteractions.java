package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.common.register.IEItems;
import net.dries007.tfc.util.BlockItemPlacement;
import net.dries007.tfc.util.InteractionManager;

public final class ModInteractions {
    public static void register() {
        InteractionManager.registerBlock(new BlockItemPlacement(IEItems.Misc.HEMP_SEEDS, ModBlocks.HEMP));
    }

    private ModInteractions() {}
}
