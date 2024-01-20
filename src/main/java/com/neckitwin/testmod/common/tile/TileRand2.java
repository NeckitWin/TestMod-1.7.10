package com.neckitwin.testmod.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileRand2 extends TileEntity {
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }
    public void spawnItem(World world, int x, int y, int z) {
        if (!world.isRemote){
            if (world.isDaytime()) {
                ItemStack itemApple = new ItemStack(Items.apple);
                EntityItem spawnApple = new EntityItem(world, x, y+1.5, z, itemApple);
                world.spawnEntityInWorld(spawnApple);
            } else {
                ItemStack itemDiamond = new ItemStack(Items.diamond);
                EntityItem spawnDiamond = new EntityItem(world, x, y+1.5, z, itemDiamond);
                world.spawnEntityInWorld(spawnDiamond);
            }
        }
    }
}