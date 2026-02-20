package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {
    public static Item STOVE;
    public static Item POT;
    public static Item STOCKPOT;
    public static Item STOCKPOT_LID;
    public static Item CHOPPING_BOARD;
    public static Item KITCHENWARE_RACKS;
    public static Item SHAWARMA_SPIT;
    public static Item MILLSTONE;
    public static Item STEAMER;
    public static Item OIL;
    public static Item OIL_POT;
    public static Item OIL_BLOCK;
    public static Item ENAMEL_BASIN;
    public static Item CHILI_RISTRA;
    public static Item STRAW_BLOCK;
    public static Item FRUIT_BASKET;
    public static Item SCARECROW;
    public static Item RECIPE_ITEM;

    public static Item IRON_KITCHEN_KNIFE;
    public static Item GOLD_KITCHEN_KNIFE;
    public static Item DIAMOND_KITCHEN_KNIFE;
    public static Item KITCHEN_SHOVEL;
    public static Item SICKLE;

    public static Item STRAW_HAT;
    public static Item STRAW_HAT_FLOWER;
    public static Item FARMER_CHEST_PLATE;
    public static Item FARMER_LEGGINGS;
    public static Item FARMER_BOOTS;

    public static Item TOMATO_SEED;
    public static Item CHILI_SEED;
    public static Item LETTUCE_SEED;
    public static Item RICE;
    public static Item WILD_RICE;

    public static Item TOMATO;
    public static Item RED_CHILI;
    public static Item GREEN_CHILI;
    public static Item LETTUCE;
    public static Item RICE_PANICLE;
    public static Item CATERPILLAR;

    public static Item FLOUR;
    public static Item RAW_DOUGH;
    public static Item RAW_NOODLES;

    public static Item FRIED_EGG;
    public static Item DONKEY_BURGER;
    public static Item BAOZI;
    public static Item DUMPLING;
    public static Item SAMSA;
    public static Item MANTOU;
    public static Item MEAT_PIE;

    public static Item COOKED_RICE;
    public static Item BEEF_NOODLE;
    public static Item HUI_NOODLE;
    public static Item UDON_NOODLE;

    public static Item SASHIMI;
    public static Item RAW_LAMB_CHOPS;
    public static Item RAW_COW_OFFAL;
    public static Item RAW_PORK_BELLY;
    public static Item RAW_DONKEY_MEAT;
    public static Item RAW_CUT_SMALL_MEATS;
    public static Item RAW_MEATBALL;

    public static Item HONEY;
    public static Item EGG;

    public static void preInit() {
        STOVE = registerItemBlock(ModBlocks.STOVE);
        POT = registerItemBlock(ModBlocks.POT);
        
        TOMATO_SEED = registerItem(new Item(), "tomato_seed");
        CHILI_SEED = registerItem(new Item(), "chili_seed");
        LETTUCE_SEED = registerItem(new Item(), "lettuce_seed");
        RICE = registerItem(new Item(), "rice");
        WILD_RICE = registerItem(new Item(), "wild_rice");
        
        TOMATO = registerItem(new ItemFood(2, 0.5F, false), "tomato");
        RED_CHILI = registerItem(new ItemFood(1, 0.0F, false), "red_chili");
        GREEN_CHILI = registerItem(new ItemFood(1, 0.0F, false), "green_chili");
        LETTUCE = registerItem(new ItemFood(2, 0.0F, false), "lettuce");
        RICE_PANICLE = registerItem(new Item(), "rice_panicle");
        CATERPILLAR = registerItem(new ItemFood(18, 0.2F, false), "caterpillar");
        
        FLOUR = registerItem(new Item(), "flour");
        RAW_DOUGH = registerItem(new Item(), "raw_dough");
        RAW_NOODLES = registerItem(new Item(), "raw_noodles");
        
        FRIED_EGG = registerItem(new ItemFood(4, 0.5F, false), "fried_egg");
        DONKEY_BURGER = registerItem(new ItemFood(12, 0.8F, false), "donkey_burger");
        BAOZI = registerItem(new ItemFood(8, 1.0F, false), "baozi");
        DUMPLING = registerItem(new ItemFood(8, 1.0F, false), "dumpling");
        SAMSA = registerItem(new ItemFood(8, 1.0F, false), "samsa");
        MANTOU = registerItem(new ItemFood(6, 0.9F, false), "mantou");
        MEAT_PIE = registerItem(new ItemFood(8, 1.0F, false), "meat_pie");
        
        COOKED_RICE = registerItem(new ItemFood(6, 1.0F, false), "cooked_rice");
        BEEF_NOODLE = registerItem(new ItemFood(14, 0.643F, false), "beef_noodle");
        HUI_NOODLE = registerItem(new ItemFood(14, 0.643F, false), "hui_noodle");
        UDON_NOODLE = registerItem(new ItemFood(14, 0.643F, false), "udon_noodle");
        
        SASHIMI = registerItem(new ItemFood(1, 0.5F, false), "sashimi");
        RAW_LAMB_CHOPS = registerItem(new ItemFood(1, 0.5F, false), "raw_lamb_chops");
        RAW_COW_OFFAL = registerItem(new ItemFood(2, 0.3F, false), "raw_cow_offal");
        RAW_PORK_BELLY = registerItem(new ItemFood(2, 0.3F, false), "raw_pork_belly");
        RAW_DONKEY_MEAT = registerItem(new ItemFood(2, 0.3F, true), "raw_donkey_meat");
        RAW_CUT_SMALL_MEATS = registerItem(new ItemFood(2, 0.3F, true), "raw_cut_small_meats");
        RAW_MEATBALL = registerItem(new ItemFood(4, 0.3F, true), "raw_meatball");
        
        HONEY = registerItem(new Item(), "honey");
        EGG = registerItem(new Item(), "egg");
        
        SICKLE = registerItem(new Item(), "sickle");
        KITCHEN_SHOVEL = registerItem(new Item(), "kitchen_shovel");
    }

    private static Item registerItemBlock(Block block) {
        if (block == null) return null;
        Item item = new ItemBlock(block).setRegistryName(block.getRegistryName());
        item.setTranslationKey(block.getTranslationKey());
        item.setCreativeTab(ModBlocks.CREATIVE_TAB);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }

    private static Item registerItem(Item item, String name) {
        item.setRegistryName(KaleidoscopeCookery.MOD_ID, name);
        item.setTranslationKey(KaleidoscopeCookery.MOD_ID + "." + name);
        item.setCreativeTab(ModBlocks.CREATIVE_TAB);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
