package com.github.ysbbbbbb.kaleidoscopecookery.content.tab;


import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.content.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
public class CreativeTab {
    public static final CreativeTabs CREATIVE_TABS = new CreativeTabs(KaleidoscopeCookery.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.Glow_Berries, 1);
        }
    };
}
