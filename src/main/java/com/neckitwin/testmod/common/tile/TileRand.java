package com.neckitwin.testmod.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class TileRand extends TileEntity {
    private static final int SPAWN_INTERVAL = 10;

    private int tickCounter = 0;

    @Override
    public void updateEntity() { // Метод вызывается каждый тик
        if (!worldObj.isRemote) { // На стороне сервера
            tickCounter++; // Увеличиваем счетчик тиков

            if (tickCounter >= SPAWN_INTERVAL) { // Если прошло 10 тиков
                spawnItem(worldObj, xCoord, yCoord, zCoord); // Спавним предмет
                tickCounter = 0; // Обнуляем счетчик тиков
            }
        }
    }
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("tickCounter", tickCounter);
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tickCounter = nbt.getInteger("tickCounter");
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