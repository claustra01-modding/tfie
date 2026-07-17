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
    public static final ItemId WIRECUTTER_HEAD = new ItemId(
        ITEMS.register("tool_head/wirecutter", () -> new Item(new Item.Properties()))
    );
    public static final ItemId HAMMER_HEAD = new ItemId(
        ITEMS.register("tool_head/ie_hammer", () -> new Item(new Item.Properties()))
    );
    public static final ItemId MOLD_SHEET = new ItemId(
        ITEMS.register("mold_sheet", () -> new Item(new Item.Properties().stacksTo(1)))
    );
    public static final ItemId MOLD_BLOCK = new ItemId(
        ITEMS.register("mold_block", () -> new Item(new Item.Properties().stacksTo(1)))
    );
    public static final ItemId DRILL_HEAD_BLACK_STEEL = new ItemId(
        ITEMS.register("drillhead_black_steel", () -> new TFIEDrillHeadItem(TFIEDrillHeadItem.BLACK_STEEL))
    );
    public static final ItemId DRILL_HEAD_BLUE_STEEL = new ItemId(
        ITEMS.register("drillhead_blue_steel", () -> new TFIEDrillHeadItem(TFIEDrillHeadItem.BLUE_STEEL))
    );
    public static final ItemId DRILL_HEAD_RED_STEEL = new ItemId(
        ITEMS.register("drillhead_red_steel", () -> new TFIEDrillHeadItem(TFIEDrillHeadItem.RED_STEEL))
    );

    private ModItems() {}
}
