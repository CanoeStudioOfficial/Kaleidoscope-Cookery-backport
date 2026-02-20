package com.github.ysbbbbbb.kaleidoscopecookery.tileentity;

import com.github.ysbbbbbb.kaleidoscopecookery.block.BlockStove;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityPot extends TileEntityBase {
    
    public boolean hasHeatSource(World world) {
        BlockPos belowPos = pos.down();
        IBlockState belowState = world.getBlockState(belowPos);
        
        if (belowState.getBlock() instanceof BlockStove) {
            return belowState.getValue(BlockStove.LIT);
        }
        
        return false;
    }
}
