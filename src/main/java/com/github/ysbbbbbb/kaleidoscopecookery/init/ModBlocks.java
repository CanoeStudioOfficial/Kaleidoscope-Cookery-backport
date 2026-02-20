package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.BlockCropBase;
import com.github.ysbbbbbb.kaleidoscopecookery.block.BlockPot;
import com.github.ysbbbbbb.kaleidoscopecookery.block.BlockStove;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {
    public static Block STOVE;
    public static Block POT;
    public static Block STOCKPOT;
    public static Block CHOPPING_BOARD;
    public static Block ENAMEL_BASIN;
    public static Block KITCHENWARE_RACKS;
    public static Block MILLSTONE;
    public static Block STEAMER;
    public static Block SHAWARMA_SPIT;
    public static Block OIL_POT;
    public static Block OIL_BLOCK;
    public static Block RECIPE_BLOCK;
    public static Block FRUIT_BASKET;
    public static Block CHILI_RISTRA;
    public static Block STRAW_BLOCK;
    
    public static BlockCropBase TOMATO_CROP;
    public static BlockCropBase CHILI_CROP;
    public static BlockCropBase LETTUCE_CROP;
    public static BlockCropBase RICE_CROP;

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(KaleidoscopeCookery.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.STOVE);
        }
    };

    public static void preInit() {
        STOVE = registerBlock(new BlockStove(), "stove");
        POT = registerBlock(new BlockPot(), "pot");
        
        TOMATO_CROP = new BlockCropBase("tomato_crop", ModItems.TOMATO, ModItems.TOMATO_SEED);
        CHILI_CROP = new BlockCropBase("chili_crop", ModItems.RED_CHILI, ModItems.CHILI_SEED);
        LETTUCE_CROP = new BlockCropBase("lettuce_crop", ModItems.LETTUCE, ModItems.LETTUCE_SEED);
        RICE_CROP = new BlockCropBase("rice_crop", ModItems.RICE_PANICLE, ModItems.RICE);
        
        ForgeRegistries.BLOCKS.register(TOMATO_CROP);
        ForgeRegistries.BLOCKS.register(CHILI_CROP);
        ForgeRegistries.BLOCKS.register(LETTUCE_CROP);
        ForgeRegistries.BLOCKS.register(RICE_CROP);
    }

    private static Block registerBlock(Block block, String name) {
        block.setRegistryName(KaleidoscopeCookery.MOD_ID, name);
        block.setTranslationKey(KaleidoscopeCookery.MOD_ID + "." + name);
        block.setCreativeTab(CREATIVE_TAB);
        ForgeRegistries.BLOCKS.register(block);
        return block;
    }
}
