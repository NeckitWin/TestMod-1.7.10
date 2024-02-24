package com.neckitwin.testmod.common.tile;

import net.minecraft.block.Block;
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

public class TileBooster extends TileEntity implements IInventory {
    private ItemStack[] inventory;
    private int timer = 0;

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
        if (!worldObj.isRemote) {
            boolean hasItemsInInputSlots = true;
            for (int i = 0; i < 2; i++) {
                if (inventory[i] == null) {
                    hasItemsInInputSlots = false;
                    break;
                }
            }
            // проверка, что в слоте 0 есть алмаз
            if (hasItemsInInputSlots && (inventory[0].getItem() == Items.diamond && inventory[1].getItem() == Items.golden_apple)) {
                if (timer == 0) {
                    if (inventory[3] != null && inventory[4] != null && inventory[5] != null) {
                        timer = 10;
                    }else if(inventory[3] != null && inventory[4] != null || inventory[3] != null && inventory[5] != null || inventory[4] != null && inventory[5] != null){
                        timer = 25;
                    } else if (inventory[3] != null || inventory[4] != null || inventory[5] != null) {
                        timer = 50;
                    } else {
                        timer = 100;
                    }
                }
            }

            if (!hasItemsInInputSlots && timer > 0) {
                timer = 0;
            }

            if (timer > 0) {
                timer--;
            }

            if (timer == 0 && hasItemsInInputSlots && (inventory[0].getItem() == Items.diamond && inventory[1].getItem() == Items.golden_apple)) {
                for (int i = 0; i < 2; i++) {
                    decrStackSize(i, 1);
                }
                if (inventory[2] == null) {
                    inventory[2] = new ItemStack(Blocks.bedrock, 1);
                } else {
                    if (inventory[2].getItem() == ItemBlock.getItemFromBlock(Blocks.bedrock) && inventory[2].stackSize < 64) {
                        inventory[2].stackSize++;
                    }
                }
            }
            markDirty();
        }
    }

    public int getTimer() {
        return this.timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList inventoryList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                inventory[i].writeToNBT(itemTag);
                inventoryList.appendTag(itemTag);
            } else {
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
