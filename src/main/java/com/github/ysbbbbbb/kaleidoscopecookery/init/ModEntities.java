package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.EntitySit;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.EntityScarecrow;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.EntityThrowableBaozi;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
    private static int entityId = 0;

    public static void preInit() {
        registerEntity(EntitySit.class, "sit", 64, 20, false);
        registerEntity(EntityScarecrow.class, "scarecrow", 64, 1, true);
        registerEntity(EntityThrowableBaozi.class, "throwable_baozi", 64, 20, true);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        ResourceLocation registryName = new ResourceLocation(KaleidoscopeCookery.MOD_ID, name);
        EntityRegistry.registerModEntity(registryName, entityClass, name, entityId++, KaleidoscopeCookery.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}
