package com.neckitwin.testmod.common.handler;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.entity.EntityNecroSkeleton;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

public class ModEntities {
    public static void init() {
        registerEntity("NecroSkeleton", EntityNecroSkeleton.class, 1, 80, 3);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int trackingRange, int updateFrequency) {
        EntityRegistry.registerModEntity(entity, name, id, TestMod.instance, trackingRange, updateFrequency, true);
    }
}