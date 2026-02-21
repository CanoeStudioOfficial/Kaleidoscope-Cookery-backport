package com.github.ysbbbbbb.kaleidoscopecookery.client.render;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ScarecrowModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.layer.ScarecrowHandLayer;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.layer.ScarecrowParrotOnShoulderLayer;
import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ScarecrowEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ScarecrowRender extends RenderLiving<ScarecrowEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(KaleidoscopeCookery.MOD_ID, "textures/entity/scarecrow.png");

    public ScarecrowRender(RenderManager renderManagerIn) {
        super(renderManagerIn, new ScarecrowModel(), 0.0F);
        this.addLayer(new LayerCustomHead(this.getMainModel().getHead()));
        this.addLayer(new ScarecrowHandLayer(this));
        this.addLayer(new ScarecrowParrotOnShoulderLayer(this));
    }

    @Override
    protected void applyRotations(ScarecrowEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
        float time = (float) (entityLiving.ticksExisted - entityLiving.lastHit) + partialTicks;
        if (time < 5.0F) {
            GlStateManager.rotate(MathHelper.sin(time / 1.5F * (float) Math.PI) * 3.0F, 0.0F, 1.0F, 0.0F);
        }
    }

    @Override
    protected boolean canRenderName(ScarecrowEntity entity) {
        return entity.hasCustomName() && entity.getAlwaysRenderNameTagForRender();
    }

    @Override
    protected ResourceLocation getEntityTexture(ScarecrowEntity entity) {
        return TEXTURE;
    }

    @Override
    public ScarecrowModel getMainModel() {
        return (ScarecrowModel) super.getMainModel();
    }
}
