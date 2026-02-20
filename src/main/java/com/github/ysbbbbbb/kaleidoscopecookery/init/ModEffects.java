package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.potion.PotionBase;
import com.github.ysbbbbbb.kaleidoscopecookery.potion.PotionWarmth;
import com.github.ysbbbbbb.kaleidoscopecookery.potion.PotionVigor;
import com.github.ysbbbbbb.kaleidoscopecookery.potion.PotionFlatulence;
import com.github.ysbbbbbb.kaleidoscopecookery.potion.PotionSatiatedShield;
import com.github.ysbbbbbb.kaleidoscopecookery.potion.PotionPreservation;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModEffects {
    public static Potion WARMTH;
    public static Potion VIGOR;
    public static Potion FLATULENCE;
    public static Potion SATIATED_SHIELD;
    public static Potion PRESERVATION;
    public static Potion SULFUR;
    public static Potion MUSTARD;
    public static Potion TUNDRA_STRIDER;

    public static void preInit() {
        WARMTH = registerPotion(new PotionWarmth(), "warmth");
        VIGOR = registerPotion(new PotionVigor(), "vigor");
        FLATULENCE = registerPotion(new PotionFlatulence(), "flatulence");
        SATIATED_SHIELD = registerPotion(new PotionSatiatedShield(), "satiated_shield");
        PRESERVATION = registerPotion(new PotionPreservation(), "preservation");
    }

    private static Potion registerPotion(Potion potion, String name) {
        potion.setRegistryName(KaleidoscopeCookery.MOD_ID, name);
        potion.setPotionName("effect." + KaleidoscopeCookery.MOD_ID + "." + name);
        ForgeRegistries.POTIONS.register(potion);
        return potion;
    }
}
