package com.neckitwin.testmod.client.renderer.entity;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.entity.EntityNecroSkeleton;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNecroSkeleton extends RenderSkeleton {
    private static final ResourceLocation NECRO_SKELETON_TEXTURES = new ResourceLocation(TestMod.MOD_ID, "textures/entity/necro_skeleton.png");

    public RenderNecroSkeleton() {
        super();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return NECRO_SKELETON_TEXTURES;
    }
}