package net.claustra01.tfie.common;

import net.claustra01.tfie.TFIE;
import net.dries007.tfc.common.items.TFCItems.ItemId;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, TFIE.MOD_ID);

    public static final ItemId TREATED_WOOD_LUMBER = new ItemId(
        ITEMS.register("treated_wood_lumber", () -> new Item(new Item.Properties()))
    );

    private ModItems() {}
}
