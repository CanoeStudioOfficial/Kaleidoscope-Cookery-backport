package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.tileentity.TileEntityPot;
import com.github.ysbbbbbb.kaleidoscopecookery.tileentity.TileEntityStockpot;
import com.github.ysbbbbbb.kaleidoscopecookery.tileentity.TileEntityStove;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
    public static void preInit() {
        GameRegistry.registerTileEntity(TileEntityStove.class, KaleidoscopeCookery.MOD_ID + ":stove");
        GameRegistry.registerTileEntity(TileEntityPot.class, KaleidoscopeCookery.MOD_ID + ":pot");
        GameRegistry.registerTileEntity(TileEntityStockpot.class, KaleidoscopeCookery.MOD_ID + ":stockpot");
    }
}
