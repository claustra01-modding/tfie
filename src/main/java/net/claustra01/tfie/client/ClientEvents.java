package net.claustra01.tfie.client;

import blusunrize.immersiveengineering.api.ManualHelper;
import net.claustra01.tfie.common.ModBlocks;
import net.claustra01.tfie.config.TFIEConfig;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientEvents {
    public static void init(IEventBus modBus) {
        modBus.addListener(ClientEvents::clientSetup);
    }

    @SuppressWarnings("deprecation")
    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            final RenderType cutout = RenderType.cutout();
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.HEMP.get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DEAD_HEMP.get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.WILD_HEMP.get(), cutout);
            ManualHelper.addConfigGetter(key -> switch (key) {
                case "crucibleExternalHeaterFEPerTick" ->
                    TFIEConfig.CRUCIBLE_EXTERNAL_HEATER_FE_PER_TICK.get();
                case "crucibleExternalHeaterTemperature" ->
                    TFIEConfig.CRUCIBLE_EXTERNAL_HEATER_TEMPERATURE.get();
                default -> -1;
            });
        });
    }

    private ClientEvents() {}
}
