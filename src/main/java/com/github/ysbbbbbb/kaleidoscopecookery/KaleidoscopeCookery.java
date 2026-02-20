package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.init.*;
import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.CommonRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class KaleidoscopeCookery {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Mod.Instance(MOD_ID)
    public static KaleidoscopeCookery INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Pre-initializing {}", MOD_NAME);
        ModBlocks.preInit();
        ModItems.preInit();
        ModEntities.preInit();
        ModTileEntities.preInit();
        ModEffects.preInit();
        ModSounds.preInit();
        ModRecipes.preInit();
        CommonRegistry.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initializing {}", MOD_NAME);
        MinecraftForge.EVENT_BUS.register(this);
        ModRecipes.init();
        CommonRegistry.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("Post-initializing {}", MOD_NAME);
        CommonRegistry.postInit();
    }
}
