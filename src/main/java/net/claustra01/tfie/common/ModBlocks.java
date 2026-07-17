package net.claustra01.tfie.common;

import net.claustra01.tfie.TFIE;
import net.dries007.tfc.common.blocks.TFCBlocks.Id;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, TFIE.MOD_ID);

    public static final Id<Block> HEMP = registerWithoutItem("crop/hemp", HempCrop::createCrop);
    public static final Id<Block> DEAD_HEMP = registerWithoutItem("dead_crop/hemp", HempCrop::createDeadCrop);
    public static final Id<Block> WILD_HEMP = new Id<>(RegistrationHelpers.registerBlock(
        BLOCKS,
        ModItems.ITEMS,
        "wild_crop/hemp",
        HempCrop::createWildCrop,
        block -> new BlockItem(block, new Item.Properties())
    ));

    private static <T extends Block> Id<T> registerWithoutItem(String name, java.util.function.Supplier<T> factory) {
        return new Id<>(RegistrationHelpers.registerBlock(BLOCKS, ModItems.ITEMS, name, factory, null));
    }

    private ModBlocks() {}
}
