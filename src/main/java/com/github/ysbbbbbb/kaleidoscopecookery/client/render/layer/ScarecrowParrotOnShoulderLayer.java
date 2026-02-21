package com.github.ysbbbbbb.kaleidoscopecookery.client.render.layer;

import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ScarecrowModel;
import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ScarecrowEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderParrot;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ScarecrowParrotOnShoulderLayer implements LayerRenderer<ScarecrowEntity> {
    private final ModelParrot model = new ModelParrot();

    public ScarecrowParrotOnShoulderLayer() {
    }

    @Override
    public void doRenderLayer(ScarecrowEntity scarecrow, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        NBTTagCompound tag = scarecrow.getShoulderEntity();
        if (tag.isEmpty()) {
            return;
        }

        String entityId = tag.getString("id");
        if (!entityId.equals("minecraft:parrot") && !entityId.equals("parrot")) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.625F, -1.675F, 0.0625F);

        int variant = tag.getInteger("Variant");
        ResourceLocation texture = getParrotTexture(variant);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        this.model.render(scarecrow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GlStateManager.popMatrix();
    }

    private ResourceLocation getParrotTexture(int variant) {
        int index = variant % 5;
        return RenderParrot.PARROT_TEXTURES[index];
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
