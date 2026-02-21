package com.github.ysbbbbbb.kaleidoscopecookery.client.render.layer;

import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ScarecrowModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.ScarecrowRender;
import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ScarecrowEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class ScarecrowHandLayer implements LayerRenderer<ScarecrowEntity> {
    private final ScarecrowRender renderer;

    public ScarecrowHandLayer(ScarecrowRender renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(ScarecrowEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack mainHand = entitylivingbaseIn.getHeldItemMainhand();
        ItemStack offHand = entitylivingbaseIn.getHeldItemOffhand();

        if (!mainHand.isEmpty()) {
            renderItem(entitylivingbaseIn, mainHand, EnumHandSide.RIGHT, scale);
        }
        if (!offHand.isEmpty()) {
            renderItem(entitylivingbaseIn, offHand, EnumHandSide.LEFT, scale);
        }
    }

    private void renderItem(ScarecrowEntity entity, ItemStack stack, EnumHandSide handSide, float scale) {
        GlStateManager.pushMatrix();
        ScarecrowModel model = this.renderer.getMainModel();
        model.translateHand(handSide, scale);

        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

        if (handSide == EnumHandSide.LEFT) {
            GlStateManager.translate(-0.375F, 0.375F, -2.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(
                    net.minecraft.block.Block.getBlockFromItem(stack.getItem()).getDefaultState(), 1.0F);
        } else {
            GlStateManager.translate(0.125F, 0.0F, -1.375F);
            GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(85.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
        }

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
