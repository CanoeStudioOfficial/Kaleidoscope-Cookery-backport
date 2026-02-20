package com.github.ysbbbbbb.kaleidoscopecookery.utils.proxy;

import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ModEntities;
import com.github.ysbbbbbb.kaleidoscopecookery.content.item.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        ModEntities.init();
    }

    public void init(FMLInitializationEvent event) {
        registerItems();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
    
    protected void registerItems() {
        for (net.minecraft.item.Item item : ModItems.ITEMS) {
            GameRegistry.register(item);
        }
    }
}
