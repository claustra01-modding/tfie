package net.claustra01.tfie.common;

import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.utils.TagUtils;
import blusunrize.immersiveengineering.common.items.DrillheadItem;
import java.util.List;
import java.util.Locale;
import net.claustra01.tfie.TFIE;
import net.dries007.tfc.common.TFCTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

public final class TFIEDrillHeadItem extends DrillheadItem {
    public static final DrillHeadPerm BLACK_STEEL = new DrillHeadPerm(
        "black_steel", ingotTag("black_steel"), 3, 1, TFCTiers.BLACK_STEEL,
        12, 8, 16000, TFIE.id("item/drill_black_steel")
    );
    public static final DrillHeadPerm BLUE_STEEL = new DrillHeadPerm(
        "blue_steel", ingotTag("blue_steel"), 5, 1, TFCTiers.BLUE_STEEL,
        15, 10, 20000, TFIE.id("item/drill_blue_steel")
    );
    public static final DrillHeadPerm RED_STEEL = new DrillHeadPerm(
        "red_steel", ingotTag("red_steel"), 5, 1, TFCTiers.RED_STEEL,
        15, 10, 20000, TFIE.id("item/drill_red_steel")
    );

    public TFIEDrillHeadItem(DrillHeadPerm properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.set(2, Component.translatable(
            Lib.DESC_FLAVOUR + "drillhead.level",
            harvestLevelName(getMiningLevel(stack))
        ).withStyle(ChatFormatting.GRAY));
    }

    private static TagKey<Item> ingotTag(String metal) {
        return TagUtils.createItemWrapper(ResourceLocation.fromNamespaceAndPath("c", "ingots/" + metal));
    }

    private static String harvestLevelName(Tier tier) {
        return tier.toString().toUpperCase(Locale.ROOT).replace('_', ' ');
    }
}
