package com.neckitwin.testmod.common.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileMechanism extends TileEntity implements IInventory {
    private ItemStack[] inventory;
    private int timer = 0;

    public TileMechanism() {
        inventory = new ItemStack[getSizeInventory()];
    }

    @Override
    public int getSizeInventory() {
        return (4);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public String getInventoryName() {
        return "Mechanism";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (inventory[slot] != null) {
            ItemStack itemstack;

            if (inventory[slot].stackSize <= amount) {
                itemstack = inventory[slot];
                inventory[slot] = null;
                markDirty();
                return itemstack;
            } else {
                itemstack = inventory[slot].splitStack(amount);

                if (inventory[slot].stackSize == 0) {
                    inventory[slot] = null;
                }

                markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        // Код должен выполнятся на северной стороне
        if (!worldObj.isRemote) {


            // Проверяем, есть ли предметы в входных слотах
            boolean hasItemsInInputSlots = true;
            for (int i = 0; i < 3; i++) {
                if (inventory[i] == null) {
                    hasItemsInInputSlots = false;
                    break;
                }
            }

            // Если во всех входных слотах есть предметы и таймер равен 0, начинаем отсчет
            if (hasItemsInInputSlots && ((inventory[0] != null && inventory[0].getItem() == ItemBlock.getItemFromBlock(Blocks.beacon)) && (inventory[1] != null && inventory[1].getItem() == Items.skull && inventory[1].getItemDamage() == 1) && (inventory[2] != null && inventory[2].getItem() == ItemBlock.getItemFromBlock(Blocks.ender_chest))) && timer <= 0) {
                timer = 100; // 5 секунд в тиках (1 секунда = 20 тиков)
            }

            // Если в любом из входных слотов нет предметов и таймер больше 0, останавливаем и сбрасываем таймер
            if (!hasItemsInInputSlots && timer > 0) {
                timer = 0;
            }

            if (timer > 0) {
                timer--;
            }

            // Если таймер достиг 0 и во всех входных слотах есть предметы, перемещаем предметы
            if (timer == 0 && hasItemsInInputSlots && ((inventory[0] != null && inventory[0].getItem() == ItemBlock.getItemFromBlock(Blocks.beacon)) && (inventory[1] != null && inventory[1].getItem() == Items.skull && inventory[1].getItemDamage() == 1) && (inventory[2] != null && inventory[2].getItem() == ItemBlock.getItemFromBlock(Blocks.ender_chest)))) {
                for (int i = 0; i < 3; i++) {
                    decrStackSize(i, 1); // Удаляем по одному предмету из каждого входного слота
                }
                if (inventory[3] == null) {
                    inventory[3] = new ItemStack(Blocks.end_portal_frame, 1); // Добавляем предмет в выходной слот
                    timer = 100; // Начинаем новый отсчет таймера
                } else {
                    if (inventory[3].getItem() == ItemBlock.getItemFromBlock(Blocks.end_portal_frame) && inventory[3].stackSize < 64) {
                        inventory[3].stackSize++;
                    }
                }
                markDirty();
            }
        }
    }

    public int getTimer() {
        return this.timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack itemstack = inventory[slot];
            inventory[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList inventoryList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            // Проверьте на наличие предмета
            if (inventory[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                inventory[i].writeToNBT(itemTag);
                inventoryList.appendTag(itemTag);
            } else {
                // Если слот пустой, добавьте пустой тег
                inventoryList.appendTag(new NBTTagCompound());
            }
        }
        compound.setTag("Inventory", inventoryList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        NBTTagList inventoryList = compound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < inventoryList.tagCount(); i++) {
            NBTTagCompound itemTag = inventoryList.getCompoundTagAt(i);
            inventory[i] = ItemStack.loadItemStackFromNBT(itemTag);
        }
    }

    // С какими предметами можно взаимодействовать контейнеру
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 3) {
            return false;
        }
        return true;
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public void openInventory() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }
}
