package com.github.ysbbbbbb.kaleidoscopecookery.block;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.tileentity.TileEntityStove;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockStove extends Block {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool LIT = PropertyBool.create("lit");

    public BlockStove() {
        super(Material.ROCK);
        this.setHardness(1.5F);
        this.setResistance(6.0F);
        this.setCreativeTab(ModBlocks.CREATIVE_TAB);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(LIT, false));
        this.setTickRandomly(true);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityStove();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);
        
        if (!state.getValue(LIT) && isIgnitionItem(heldItem)) {
            world.setBlockState(pos, state.withProperty(LIT, true));
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
            
            if (heldItem.getItem() == Items.FIRE_CHARGE) {
                heldItem.shrink(1);
            } else {
                heldItem.damageItem(1, player);
            }
            return true;
        }
        
        if (state.getValue(LIT) && isExtinguishItem(heldItem)) {
            world.setBlockState(pos, state.withProperty(LIT, false));
            world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            heldItem.damageItem(1, player);
            return true;
        }
        
        return false;
    }

    private boolean isIgnitionItem(ItemStack stack) {
        return stack.getItem() == Items.FLINT_AND_STEEL || stack.getItem() == Items.FIRE_CHARGE;
    }

    private boolean isExtinguishItem(ItemStack stack) {
        return !stack.isEmpty();
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (state.getValue(LIT) && world.isRainingAt(pos.up())) {
            world.setBlockState(pos, state.withProperty(LIT, false));
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT)) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;

            if (rand.nextInt(10) == 0) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    x + rand.nextDouble() / 3 * (rand.nextBoolean() ? 1 : -1),
                    y + 0.5 + rand.nextDouble() / 3,
                    z + rand.nextDouble() / 3 * (rand.nextBoolean() ? 1 : -1),
                    0, 0.02, 0);

            EnumFacing direction = state.getValue(FACING);
            EnumFacing.Axis axis = direction.getAxis();
            double offsetRandom = rand.nextDouble() * 0.6 - 0.3;
            double xOffset = axis == EnumFacing.Axis.X ? (double) direction.getXOffset() * 0.52 : offsetRandom;
            double yOffset = 0.25 + rand.nextDouble() * 6.0 / 16.0;
            double zOffset = axis == EnumFacing.Axis.Z ? (double) direction.getZOffset() * 0.52 : offsetRandom;
            
            world.spawnParticle(EnumParticleTypes.FLAME,
                    x + xOffset,
                    pos.getY() + yOffset,
                    z + zOffset,
                    0, 0, 0);
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(LIT) ? 13 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        boolean lit = (meta & 4) != 0;
        return this.getDefaultState().withProperty(FACING, facing).withProperty(LIT, lit);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(LIT)) {
            meta |= 4;
        }
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, LIT);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }
}
