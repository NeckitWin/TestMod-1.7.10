package com.neckitwin.testmod.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileBooster extends TileEntity implements IInventory {
    private ItemStack[] inventory;

    public TileBooster() {
        inventory = new ItemStack[getSizeInventory()];
    }

    @Override
    public int getSizeInventory() {
        return (6);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public String getInventoryName() {
        return "Booster";
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

            markDirty();
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
        if (slot == 6) {
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
