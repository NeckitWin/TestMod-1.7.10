package com.neckitwin.testmod.common.item;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.entity.EntityNecroSkeleton;
import com.neckitwin.testmod.common.handler.ModTab;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNecroStaff extends Item {
    public ItemNecroStaff() {
        setUnlocalizedName("NecroStaff");
        setTextureName(TestMod.MOD_ID + ":NecroStaff");
        setCreativeTab(ModTab.INSTANCE);
    }

    // При нажатии пкм предметом спавним рядом с игроком обычного скелета в мир
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            for (int i = 1; i <= 3; i++) {
                EntityNecroSkeleton skeleton = new EntityNecroSkeleton(world);
                double angle = 2 * Math.PI * i / 3;
                double radius = 2.0;
                skeleton.setPosition(player.posX + radius * Math.cos(angle), player.posY, player.posZ + radius * Math.sin(angle));
                skeleton.setOwner(player);
                world.spawnEntityInWorld(skeleton);
            }
        }
        return itemStack;
    }
}