package net.claustra01.tfie.common;

import net.claustra01.tfie.TFIE;
import net.dries007.tfc.common.TFCCreativeTabs.Id;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TFIE.MOD_ID);

    public static final Id MAIN = new Id(TABS.register("main", () -> CreativeModeTab.builder()
        .title(Component.translatable("tfie.creative_tab.main"))
        .icon(() -> new ItemStack(ModItems.TREATED_WOOD_LUMBER.get()))
        .displayItems((parameters, output) -> {
            output.accept(ModItems.TREATED_WOOD_LUMBER.get());
            output.accept(ModItems.WIRECUTTER_HEAD.get());
            output.accept(ModItems.HAMMER_HEAD.get());
            output.accept(ModItems.MOLD_SHEET.get());
            output.accept(ModItems.MOLD_BLOCK.get());
            output.accept(ModItems.DRILL_HEAD_BLACK_STEEL.get());
            output.accept(ModItems.DRILL_HEAD_BLUE_STEEL.get());
            output.accept(ModItems.DRILL_HEAD_RED_STEEL.get());
            output.accept(ModBlocks.WILD_HEMP.get());
        })
        .build()), (parameters, output) -> {
            output.accept(ModItems.TREATED_WOOD_LUMBER.get());
            output.accept(ModItems.WIRECUTTER_HEAD.get());
            output.accept(ModItems.HAMMER_HEAD.get());
            output.accept(ModItems.MOLD_SHEET.get());
            output.accept(ModItems.MOLD_BLOCK.get());
            output.accept(ModItems.DRILL_HEAD_BLACK_STEEL.get());
            output.accept(ModItems.DRILL_HEAD_BLUE_STEEL.get());
            output.accept(ModItems.DRILL_HEAD_RED_STEEL.get());
            output.accept(ModBlocks.WILD_HEMP.get());
        });

    private ModCreativeTabs() {}
}
