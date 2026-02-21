package com.github.ysbbbbbb.kaleidoscopecookery.content;


import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.content.item.ModItems;
import net.minecraft.item.ItemFood;

import static com.github.ysbbbbbb.kaleidoscopecookery.content.tab.CreativeTab.CREATIVE_TABS;


public class ItemFoodCreator extends ItemFood  {
    public ItemFoodCreator(String name, int hunger, float saturationModifier)
    {
        super(hunger, saturationModifier, false);
        setTranslationKey(KaleidoscopeCookery.MOD_ID + "." + name.toLowerCase());
        setRegistryName(name.toLowerCase());
        setNoRepair();
        setCreativeTab(CREATIVE_TABS);

        ModItems.ITEMS.add(this);
    }


}