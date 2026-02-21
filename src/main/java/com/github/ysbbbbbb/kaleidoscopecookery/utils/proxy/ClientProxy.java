package com.github.ysbbbbbb.kaleidoscopecookery.utils.proxy;

import com.github.ysbbbbbb.kaleidoscopecookery.client.render.ScarecrowRender;
import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ModEntities;
import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ScarecrowEntity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        registerEntityRenderers();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    private void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ScarecrowEntity.class, ScarecrowRender::new);
    }
}
