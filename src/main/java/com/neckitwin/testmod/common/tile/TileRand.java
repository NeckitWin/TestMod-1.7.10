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
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }
    // Создаём метод всех предметов майнкрафт и берём случайный предмет из них
    public static Item[] items = {
            Items.diamond,
            Items.iron_ingot,
            Items.gold_ingot,
            Items.apple,
            Items.bread,
            Items.egg,
            Items.feather,
            Items.gunpowder,
            Items.leather,
            Items.milk_bucket,
            Items.porkchop,
            Items.reeds,
            Items.sugar,
            Items.wheat,
            Items.wheat_seeds,
            };

    public void spawnItem(World world, int x, int y, int z) {
        if (!world.isRemote){
            ItemStack spawnDiamond = new ItemStack(items[new Random().nextInt(items.length)]);
            EntityItem diamondEntity = new EntityItem(world, x, y+1.5, z, spawnDiamond);
            world.spawnEntityInWorld(diamondEntity);
        }
    }
}