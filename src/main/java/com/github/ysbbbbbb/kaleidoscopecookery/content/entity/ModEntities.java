package com.github.ysbbbbbb.kaleidoscopecookery.content.entity;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void init() {
        registerEntity("scarecrow", ScarecrowEntity.class, 0, 64, 1, true);
    }

    private static void registerEntity(String name, Class<? extends Entity> entityClass, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        ResourceLocation registryName = new ResourceLocation(KaleidoscopeCookery.MOD_ID, name);
        EntityRegistry.registerModEntity(registryName, entityClass, name, id, KaleidoscopeCookery.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}
