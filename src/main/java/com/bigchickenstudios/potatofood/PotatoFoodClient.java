package com.bigchickenstudios.potatofood;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class PotatoFoodClient {

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(PotatoFoodClient::setup);
    }

    private static void setup(FMLClientSetupEvent event) {

        PotatoFood.Blocks.RICE.ifPresent((b) -> ItemBlockRenderTypes.setRenderLayer(b, RenderType.cutout()));
    }
}
