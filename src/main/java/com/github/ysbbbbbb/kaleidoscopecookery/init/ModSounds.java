package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModSounds {
    public static SoundEvent BLOCK_STOVE_CRACKLE;
    public static SoundEvent ITEM_KITCHEN_KNIFE_CUT;
    public static SoundEvent BLOCK_POT_BOIL;
    public static SoundEvent BLOCK_STOCKPOT_BOIL;

    public static void preInit() {
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(KaleidoscopeCookery.MOD_ID, name);
        SoundEvent sound = new SoundEvent(location);
        sound.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(sound);
        return sound;
    }
}
