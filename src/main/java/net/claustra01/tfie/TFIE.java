package net.claustra01.tfie;

import blusunrize.immersiveengineering.api.tool.ExternalHeaterHandler;
import com.mojang.logging.LogUtils;
import net.claustra01.tfie.client.ClientEvents;
import net.claustra01.tfie.common.ClocheRenderFunctions;
import net.claustra01.tfie.common.CrucibleHeater;
import net.claustra01.tfie.common.EmptyRecipe;
import net.claustra01.tfie.common.HerbicideEffects;
import net.claustra01.tfie.common.ModBlockEntities;
import net.claustra01.tfie.common.ModBlocks;
import net.claustra01.tfie.common.ModCreativeTabs;
import net.claustra01.tfie.common.ModItems;
import net.claustra01.tfie.common.ModInteractions;
import net.claustra01.tfie.config.TFIEConfig;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(TFIE.MOD_ID)
public final class TFIE {
    public static final String MOD_ID = "tfie";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TFIE(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER, TFIEConfig.SPEC);
        ModItems.ITEMS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::register);
        modBus.addListener(this::registerCapabilities);
        ClocheRenderFunctions.register();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientEvents.init(modBus);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        ModInteractions.register();
        ClocheRenderFunctions.initializeSoils();
        HerbicideEffects.register();
    }

    private void register(RegisterEvent event) {
        event.register(Registries.RECIPE_TYPE, EmptyRecipe.ID, () -> EmptyRecipe.TYPE);
        event.register(Registries.RECIPE_SERIALIZER, EmptyRecipe.ID, () -> EmptyRecipe.SERIALIZER);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            ExternalHeaterHandler.CAPABILITY,
            TFCBlockEntities.CRUCIBLE.get(),
            CrucibleHeater::new);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
