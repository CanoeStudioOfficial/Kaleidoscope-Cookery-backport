package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.content.item.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.content.item.scarecrow.Scarecrow;
import com.github.ysbbbbbb.kaleidoscopecookery.utils.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class KaleidoscopeCookery {

    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    
    @Mod.Instance
    public static KaleidoscopeCookery instance;
    
    @SidedProxy(clientSide = "com.github.ysbbbbbb.kaleidoscopecookery.utils.proxy.ClientProxy", serverSide = "com.github.ysbbbbbb.kaleidoscopecookery.utils.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) { 
        proxy.preInit(event); 
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) { 
        proxy.init(event); 
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) { 
        proxy.postInit(event); 
    }
}
