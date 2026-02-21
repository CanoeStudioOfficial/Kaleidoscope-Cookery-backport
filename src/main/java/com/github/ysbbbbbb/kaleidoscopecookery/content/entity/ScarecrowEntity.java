package com.github.ysbbbbbb.kaleidoscopecookery.content.entity;

import com.github.ysbbbbbb.kaleidoscopecookery.content.item.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ScarecrowEntity extends EntityLiving {
    private static final DataParameter<NBTTagCompound> SHOULDER_ENTITY = EntityDataManager.createKey(ScarecrowEntity.class, DataSerializers.COMPOUND_TAG);

    public long lastHit;
    private int cooldown;
    private long timeEntitySatOnShoulder;

    public ScarecrowEntity(World world) {
        super(world);
        this.setSize(0.5F, 2.375F);
    }

    public ScarecrowEntity(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SHOULDER_ENTITY, new NBTTagCompound());
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.cooldown > 0) {
            this.cooldown--;
        }
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        ItemStack itemInHand = player.getHeldItem(hand);
        if (itemInHand.getItem() == Items.NAME_TAG) {
            return EnumActionResult.PASS;
        }
        if (player.isSpectator()) {
            return EnumActionResult.SUCCESS;
        }
        if (this.world.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        if (hand == EnumHand.OFF_HAND) {
            return EnumActionResult.PASS;
        }
        if (this.cooldown > 0) {
            return EnumActionResult.PASS;
        }
        if (isClickHand(vec)) {
            return handleHandItems(player, itemInHand);
        }
        if (isClickHead(vec)) {
            return handleHeadItems(player, itemInHand);
        }
        return EnumActionResult.PASS;
    }

    private EnumActionResult handleHeadItems(EntityPlayer player, ItemStack itemInHand) {
        this.cooldown = 5;
        ItemStack headItem = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (itemInHand.isEmpty() && !headItem.isEmpty()) {
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
            ItemHandlerHelper.giveItemToPlayer(player, headItem);
            return EnumActionResult.SUCCESS;
        }

        if (!(itemInHand.getItem() instanceof ItemBlock)) {
            return EnumActionResult.PASS;
        }

        ItemBlock blockItem = (ItemBlock) itemInHand.getItem();
        if (!(blockItem.getBlock() instanceof BlockSkull)) {
            return EnumActionResult.PASS;
        }

        if (player.capabilities.isCreativeMode && headItem.isEmpty()) {
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(itemInHand.getItem(), 1, itemInHand.getMetadata()));
            this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_WOOD_PLACE, this.getSoundCategory(), 1.0F, 1.0F);
            return EnumActionResult.SUCCESS;
        }
        if (!itemInHand.isEmpty() && itemInHand.getCount() > 1) {
            if (headItem.isEmpty()) {
                ItemStack copyStack = itemInHand.splitStack(1);
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, copyStack);
                this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_WOOD_PLACE, this.getSoundCategory(), 1.0F, 1.0F);
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.PASS;
        }
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, itemInHand);
        this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_WOOD_PLACE, this.getSoundCategory(), 1.0F, 1.0F);
        player.setHeldItem(EnumHand.MAIN_HAND, headItem);
        return EnumActionResult.SUCCESS;
    }

    private EnumActionResult handleHandItems(EntityPlayer player, ItemStack itemInHand) {
        this.cooldown = 5;
        if (itemInHand.isEmpty()) {
            ItemStack mainhand = this.getHeldItemMainhand();
            ItemStack offhand = this.getHeldItemOffhand();
            if (!mainhand.isEmpty()) {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                ItemHandlerHelper.giveItemToPlayer(player, mainhand);
                return EnumActionResult.SUCCESS;
            }
            if (!offhand.isEmpty()) {
                this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
                ItemHandlerHelper.giveItemToPlayer(player, offhand);
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.PASS;
        }
        if (itemInHand.getItem().isDamageable()) {
            if (swapHand(EnumHand.MAIN_HAND, player, itemInHand)) {
                this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_WOOD_PLACE, this.getSoundCategory(), 1.0F, 1.0F);
                return EnumActionResult.SUCCESS;
            }
        }
        if (itemInHand.getItem() instanceof ItemBlock) {
            if (swapHand(EnumHand.OFF_HAND, player, itemInHand)) {
                this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_WOOD_PLACE, this.getSoundCategory(), 1.0F, 1.0F);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    private boolean swapHand(EnumHand hand, EntityPlayer player, ItemStack itemInHand) {
        ItemStack scarecrowStack = this.getHeldItem(hand);
        if (player.capabilities.isCreativeMode && scarecrowStack.isEmpty() && !itemInHand.isEmpty()) {
            this.setHeldItem(hand, new ItemStack(itemInHand.getItem(), 1, itemInHand.getMetadata()));
            return true;
        }
        if (!itemInHand.isEmpty() && itemInHand.getCount() > 1) {
            if (scarecrowStack.isEmpty()) {
                this.setHeldItem(hand, itemInHand.splitStack(1));
                return true;
            }
            return false;
        }
        this.setHeldItem(hand, itemInHand);
        player.setHeldItem(EnumHand.MAIN_HAND, scarecrowStack);
        return true;
    }

    private boolean isClickHand(Vec3d vector) {
        return 17 / 16.0 <= vector.y && vector.y <= 27 / 17.0;
    }

    private boolean isClickHead(Vec3d vector) {
        return 27 / 17.0 < vector.y;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.world.isRemote || this.isDead) {
            return false;
        }

        if (source.isDamageAbsolute()) {
            this.setDead();
            return false;
        }

        if (this.isEntityInvulnerable(source)) {
            return false;
        }

        if (source.isExplosion()) {
            this.brokenByAnything(source);
            this.setDead();
            return false;
        }

        Entity entity = source.getTrueSource();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (!player.capabilities.allowEdit) {
                return false;
            }
        }

        if (source.isCreativePlayer()) {
            this.playBrokenSound();
            this.showBreakingParticles();
            this.setDead();
            return false;
        }

        long gameTime = this.world.getTotalWorldTime();
        if (gameTime - this.lastHit > 5) {
            this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_PLAYER_HURT, this.getSoundCategory(), 0.3F, 1.0F);
            this.world.setEntityState(this, (byte) 32);
            this.lastHit = gameTime;
            if (!this.getShoulderEntity().isEmpty()) {
                this.removeEntitiesOnShoulder();
            }
        } else {
            this.brokenByPlayer(source);
            this.showBreakingParticles();
            this.setDead();
        }

        return true;
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 32) {
            if (this.world.isRemote) {
                this.lastHit = this.world.getTotalWorldTime();
            }
        } else {
            super.handleStatusUpdate(id);
        }
    }

    private void brokenByPlayer(DamageSource source) {
        ItemStack stack = new ItemStack(ModItems.SCARECROW);
        if (this.hasCustomName()) {
            stack.setStackDisplayName(this.getCustomNameTag());
        }
        Block.spawnAsEntity(this.world, this.getPosition(), stack);
        this.brokenByAnything(source);
    }

    private void brokenByAnything(DamageSource source) {
        this.playBrokenSound();
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            ItemStack stack = this.getItemStackFromSlot(slot);
            if (!stack.isEmpty()) {
                Block.spawnAsEntity(this.world, this.getPosition().up(), stack);
                this.setItemStackToSlot(slot, ItemStack.EMPTY);
            }
        }
    }

    private void playBrokenSound() {
        this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_PLAYER_DEATH, this.getSoundCategory(), 1.0F, 1.0F);
    }

    private void showBreakingParticles() {
        IBlockState state = Blocks.PLANKS.getDefaultState();
        for (int i = 0; i < 10; ++i) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.BLOCK_CRACK,
                    this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                    this.posY + this.height * 0.66,
                    this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                    (this.rand.nextDouble() - 0.5) * 0.1,
                    this.rand.nextDouble() * 0.1,
                    (this.rand.nextDouble() - 0.5) * 0.1,
                    Block.getStateId(state));
        }
    }

    private boolean setEntityOnShoulder(NBTTagCompound tag) {
        if (this.canEntityOnShoulder()) {
            this.setShoulderEntity(tag);
            this.timeEntitySatOnShoulder = this.world.getTotalWorldTime();
            return true;
        }
        return false;
    }

    private void removeEntitiesOnShoulder() {
        if (this.timeEntitySatOnShoulder + 20 < this.world.getTotalWorldTime()) {
            this.respawnEntityOnShoulder(this.getShoulderEntity());
            this.setShoulderEntity(new NBTTagCompound());
        }
    }

    private void respawnEntityOnShoulder(NBTTagCompound tag) {
        if (!this.world.isRemote && !tag.isEmpty()) {
            Entity entity = EntityList.createEntityFromNBT(tag, this.world);
            if (entity != null) {
                entity.setPosition(this.posX, this.posY + 1.675, this.posZ);
                this.world.spawnEntity(entity);
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (!this.getShoulderEntity().isEmpty()) {
            compound.setTag("ShoulderEntity", this.getShoulderEntity());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("ShoulderEntity", Constants.NBT.TAG_COMPOUND)) {
            this.setShoulderEntity(compound.getCompoundTag("ShoulderEntity"));
        }
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
        for (Entity entity : list) {
            if (entity instanceof EntityMinecart && ((EntityMinecart) entity).canBeRidden()) {
                if (this.getDistanceSq(entity) <= 0.2) {
                    entity.addVelocity(this.motionX, this.motionY, this.motionZ);
                    return;
                }
            }
        }

        if (this.canEntityOnShoulder()) {
            AxisAlignedBB searchBox = this.getEntityBoundingBox().grow(2);
            List<EntityParrot> parrots = this.world.getEntitiesWithinAABB(EntityParrot.class, searchBox);
            for (EntityParrot parrot : parrots) {
                if (this.getDistanceSq(parrot) <= 1.5 && !parrot.isSitting() && parrot.canSitOnShoulder()) {
                    if (this.setEntityOnShoulder(parrot)) {
                        return;
                    }
                }
            }
        }
    }

    private boolean setEntityOnShoulder(EntityParrot parrot) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("id", EntityList.getKey(parrot).toString());
        parrot.writeToNBT(tag);
        if (this.setEntityOnShoulder(tag)) {
            parrot.setDead();
            return true;
        }
        return false;
    }

    private boolean canEntityOnShoulder() {
        return !this.isRiding() && this.onGround && !this.isInWater() && !this.isInLava() && this.getShoulderEntity().isEmpty();
    }

    @Override
    public Iterable<ItemStack> getHeldEquipment() {
        return Lists.newArrayList(this.getHeldItemMainhand(), this.getHeldItemOffhand());
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Lists.newArrayList(
                this.getItemStackFromSlot(EntityEquipmentSlot.HEAD),
                this.getItemStackFromSlot(EntityEquipmentSlot.CHEST),
                this.getItemStackFromSlot(EntityEquipmentSlot.LEGS),
                this.getItemStackFromSlot(EntityEquipmentSlot.FEET)
        );
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        switch (slotIn.getSlotType()) {
            case HAND:
                return super.getItemStackFromSlot(slotIn);
            case ARMOR:
                return super.getItemStackFromSlot(slotIn);
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

    @Override
    public boolean isImmuneToExplosions() {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    @Override
    public void onStruckByLightning(net.minecraft.entity.effect.EntityLightningBolt lightningBolt) {
    }

    @Override
    public boolean isPotionApplicable(net.minecraft.potion.PotionEffect potioneffectIn) {
        return false;
    }

    @Override
    public boolean canBeHitWithPotion() {
        return false;
    }

    @Override
    public ItemStack getPickedResult(net.minecraft.util.math.RayTraceResult target) {
        return new ItemStack(ModItems.SCARECROW);
    }

    public NBTTagCompound getShoulderEntity() {
        return this.dataManager.get(SHOULDER_ENTITY);
    }

    public void setShoulderEntity(NBTTagCompound tag) {
        this.dataManager.set(SHOULDER_ENTITY, tag);
    }
}
