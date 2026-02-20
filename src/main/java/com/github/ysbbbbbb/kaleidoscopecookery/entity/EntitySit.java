package com.github.ysbbbbbb.kaleidoscopecookery.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntitySit extends Entity {
    public EntitySit(World world) {
        super(world);
        this.setSize(0.0F, 0.0F);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!this.world.isRemote && this.getPassengers().isEmpty()) {
            this.setDead();
        }
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return 0.0;
    }

    @Override
    public Vec3d getDismountPosition(EntityPlayer player) {
        return new Vec3d(this.posX, this.posY, this.posZ);
    }
}
