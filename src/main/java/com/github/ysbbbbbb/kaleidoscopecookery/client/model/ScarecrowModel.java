package com.github.ysbbbbbb.kaleidoscopecookery.client.model;

import com.github.ysbbbbbb.kaleidoscopecookery.content.entity.ScarecrowEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class ScarecrowModel extends ModelBase {
    private final ModelRenderer group;
    private final ModelRenderer head;
    private final ModelRenderer hat;
    private final ModelRenderer headwear_r1;
    private final ModelRenderer leftArm;
    private final ModelRenderer rightArm;

    public ScarecrowModel() {
        textureWidth = 128;
        textureHeight = 128;

        group = new ModelRenderer(this);
        group.setRotationPoint(0.0F, 23.0278F, 0.0278F);
        group.cubeList.add(new ModelBox(group, 85, 82, -4.0F, -26.0278F, -2.0278F, 8, 10, 4, 0.0F, false));
        group.cubeList.add(new ModelBox(group, 54, 69, -4.0F, -17.0278F, -2.0278F, 8, 0, 4, 0.0F, false));
        group.cubeList.add(new ModelBox(group, 73, 60, 8.0F, -26.0278F, -2.0278F, 0, 4, 4, 0.0F, false));
        group.cubeList.add(new ModelBox(group, 73, 60, -8.0F, -26.0278F, -2.0278F, 0, 4, 4, 0.0F, true));
        group.cubeList.add(new ModelBox(group, 10, 107, -14.0F, -25.7778F, -1.0278F, 28, 2, 2, 0.0F, false));
        group.cubeList.add(new ModelBox(group, 101, 65, 4.0F, -26.0278F, -2.0278F, 5, 4, 4, 0.0F, false));
        group.cubeList.add(new ModelBox(group, 82, 65, -9.0F, -26.0278F, -2.0278F, 5, 4, 4, 0.0F, false));
        group.cubeList.add(new ModelBox(group, 28, 70, -1.0F, -18.0278F, -1.0278F, 2, 19, 2, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -2.8F, 0.1F);
        group.addChild(head);
        head.cubeList.add(new ModelBox(head, 13, 3, -4.5F, -32.2278F, -4.3778F, 9, 9, 9, 0.0F, false));

        hat = new ModelRenderer(this);
        hat.setRotationPoint(0.1198F, -30.4846F, 0.5972F);
        head.addChild(hat);

        headwear_r1 = new ModelRenderer(this);
        headwear_r1.setRotationPoint(0.0F, -0.25F, -0.5F);
        hat.addChild(headwear_r1);
        headwear_r1.cubeList.add(new ModelBox(headwear_r1, 66, 8, -5.0F, -3.775F, -5.025F, 10, 5, 10, 0.0F, false));
        headwear_r1.cubeList.add(new ModelBox(headwear_r1, 77, 25, -8.0F, 1.275F, -7.975F, 16, 0, 16, 0.0F, false));

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-12.0F, -24.8F, -0.05F);
        group.addChild(rightArm);
        rightArm.cubeList.add(new ModelBox(rightArm, 0, 0, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F, true));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(12.0F, -24.8F, -0.05F);
        group.addChild(leftArm);
        leftArm.cubeList.add(new ModelBox(leftArm, 0, 0, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        group.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        if (entityIn instanceof ScarecrowEntity) {
            ScarecrowEntity scarecrow = (ScarecrowEntity) entityIn;
            ItemStack headStack = scarecrow.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            head.showModel = headStack.isEmpty();
        }
    }

    public void translateHand(EnumHandSide side, float scale) {
        if (side == EnumHandSide.LEFT) {
            GlStateManager.translate(leftArm.offsetX * scale, leftArm.offsetY * scale, leftArm.offsetZ * scale);
            leftArm.postRender(scale);
        } else {
            GlStateManager.translate(rightArm.offsetX * scale, rightArm.offsetY * scale, rightArm.offsetZ * scale);
            rightArm.postRender(scale);
        }
    }

    public ModelRenderer getHead() {
        return head;
    }

    public ModelRenderer getRightArm() {
        return rightArm;
    }

    public ModelRenderer getLeftArm() {
        return leftArm;
    }
}
