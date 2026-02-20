package com.github.ysbbbbbb.kaleidoscopecookery.block;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.tileentity.TileEntityPot;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockPot extends BlockHorizontal {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool HAS_OIL = PropertyBool.create("has_oil");
    public static final PropertyBool SHOW_OIL = PropertyBool.create("show_oil");

    public BlockPot() {
        super(Material.IRON);
        this.setHardness(1.5F);
        this.setResistance(6.0F);
        this.setCreativeTab(ModBlocks.CREATIVE_TAB);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(HAS_OIL, false)
                .withProperty(SHOW_OIL, false));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityPot();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityPot) {
            IBlockState belowState = world.getBlockState(pos.down());
            boolean needsBase = !belowState.isSideSolid(world, pos.down(), EnumFacing.UP);
            ((TileEntityPot) tileEntity).setHasBase(needsBase);
        }
        
        return state;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.OFF_HAND) {
            return false;
        }
        
        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityPot)) {
            return false;
        }
        
        TileEntityPot pot = (TileEntityPot) tileEntity;
        ItemStack heldItem = player.getHeldItem(hand);
        
        if (!pot.hasHeatSource(world)) {
            if (!world.isRemote) {
                player.sendStatusMessage(new TextComponentTranslation("tip.kaleidoscopecookery.pot.need_lit_stove"), true);
            }
            return true;
        }
        
        if (!state.getValue(HAS_OIL)) {
            if (heldItem.getItem().getRegistryName() != null && 
                heldItem.getItem().getRegistryName().toString().contains("oil")) {
                world.setBlockState(pos, state.withProperty(HAS_OIL, true));
                heldItem.shrink(1);
                return true;
            }
            if (!world.isRemote) {
                player.sendStatusMessage(new TextComponentTranslation("tip.kaleidoscopecookery.pot.need_oil"), true);
            }
            return true;
        }
        
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        boolean hasOil = (meta & 4) != 0;
        boolean showOil = (meta & 8) != 0;
        return this.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(HAS_OIL, hasOil)
                .withProperty(SHOW_OIL, showOil);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(HAS_OIL)) meta |= 4;
        if (state.getValue(SHOW_OIL)) meta |= 8;
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, HAS_OIL, SHOW_OIL);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, net.minecraft.client.util.ITooltipFlag advanced) {
        tooltip.add(net.minecraft.client.resources.I18n.format("tooltip.kaleidoscopecookery.pot"));
    }
}
