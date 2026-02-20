package com.github.ysbbbbbb.kaleidoscopecookery.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityScarecrow extends EntityArmorStand {
    public EntityScarecrow(World world) {
        super(world);
        this.setNoGravity(true);
        this.setSilent(true);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            return false;
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (source.getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) source.getTrueSource();
            if (player.isCreative()) {
                this.setDead();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);
    }

    @Nullable
    @Override
    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return null;
    }

    @Nullable
    @Override
    public SoundEvent getDeathSound() {
        return null;
    }
}
