package com.neckitwin.testmod.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;

public class EntityNecroSkeleton extends EntitySkeleton {
    private EntityLivingBase owner;

    public EntityNecroSkeleton(World world) {
        super(world);
    }

    public void setOwner(EntityLivingBase owner) {
        this.owner = owner;
    }

    public EntityLivingBase getOwner() {
        return this.owner;
    }

    @Override
    public void setAttackTarget(EntityLivingBase target) {
        if (target != this.getOwner()) {
            super.setAttackTarget(target);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.owner != null && this.owner.isDead) {
            this.setDead();
        }
    }
}