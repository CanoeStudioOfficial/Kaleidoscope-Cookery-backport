package com.github.ysbbbbbb.kaleidoscopecookery.content.item.scarecrow;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ScarecrowEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.content.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import static com.github.ysbbbbbb.kaleidoscopecookery.content.tab.CreativeTab.CREATIVE_TABS;

public class Scarecrow extends Item {

    public Scarecrow(String name) {
        setTranslationKey(KaleidoscopeCookery.MOD_ID + "." + name.toLowerCase());
        setRegistryName(name.toLowerCase());
        setMaxStackSize(1);
        setCreativeTab(CREATIVE_TABS);
        ModItems.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing == EnumFacing.DOWN) {
            return EnumActionResult.FAIL;
        }

        ItemStack stack = player.getHeldItem(hand);
        BlockPos placePos = pos.offset(facing);

        double x = placePos.getX() + 0.5;
        double y = placePos.getY();
        double z = placePos.getZ() + 0.5;

        AxisAlignedBB aabb = new AxisAlignedBB(x - 0.25, y, z - 0.25, x + 0.25, y + 2.375, z + 0.25);

        if (worldIn.getEntitiesWithinAABBExcludingEntity(null, aabb).isEmpty()) {
            if (!worldIn.isRemote) {
                ScarecrowEntity scarecrow = new ScarecrowEntity(worldIn, x, y, z);
                float rotation = MathHelper.floor((MathHelper.wrapDegrees(player.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                scarecrow.rotationYaw = rotation;
                scarecrow.rotationYawHead = rotation;
                scarecrow.renderYawOffset = rotation;
                scarecrow.prevRotationYaw = rotation;
                scarecrow.prevRotationYawHead = rotation;
                scarecrow.prevRenderYawOffset = rotation;

                if (stack.hasDisplayName()) {
                    scarecrow.setCustomNameTag(stack.getDisplayName());
                }

                worldIn.spawnEntity(scarecrow);
                worldIn.playSound(null, scarecrow.posX, scarecrow.posY, scarecrow.posZ, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);

                if (player instanceof EntityPlayerMP) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger((EntityPlayerMP) player, scarecrow);
                }
            }

            stack.shrink(1);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, net.minecraft.client.util.ITooltipFlag flagIn) {
        tooltip.add(net.minecraft.client.resources.I18n.format("tooltip.kaleidoscope_cookery.scarecrow"));
    }
}
