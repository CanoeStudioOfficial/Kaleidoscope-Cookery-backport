package com.github.ysbbbbbb.kaleidoscopecookery.tileentity;

import com.github.ysbbbbbb.kaleidoscopecookery.block.BlockStove;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityPot extends TileEntityBase {
    private boolean hasBase;
    
    public boolean hasHeatSource(World world) {
        BlockPos belowPos = pos.down();
        IBlockState belowState = world.getBlockState(belowPos);
        
        if (belowState.getBlock() instanceof BlockStove) {
            return belowState.getValue(BlockStove.LIT);
        }
        
        return false;
    }
    
    public boolean hasBase() {
        return hasBase;
    }
    
    public void setHasBase(boolean hasBase) {
        this.hasBase = hasBase;
        this.markDirty();
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("HasBase", hasBase);
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        hasBase = compound.getBoolean("HasBase");
    }
}
