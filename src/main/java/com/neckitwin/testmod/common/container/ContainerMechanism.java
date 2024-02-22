package com.neckitwin.testmod.common.container;

import com.neckitwin.testmod.common.slots.SlotOut;
import com.neckitwin.testmod.common.tile.TileMechanism;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMechanism extends Container {
    private TileMechanism tile;
    private int lastTimerValue = 0;

    public ContainerMechanism(InventoryPlayer player, TileMechanism tile) {
        this.tile = tile;
        int i = 0;

        this.addSlotToContainer(new Slot(tile, 0, 73, 36));
        this.addSlotToContainer(new Slot(tile, 1, 73, 63));
        this.addSlotToContainer(new Slot(tile, 2, 73, 90));
        this.addSlotToContainer(new SlotOut(tile, 3, 173, 63));

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 48 + j * 18, 135 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player, i, 48 + i * 18, 193));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (lastTimerValue != tile.getTimer()) {
            for (Object crafter : this.crafters) {
                ICrafting icrafting = (ICrafting) crafter;
                icrafting.sendProgressBarUpdate(this, 0, tile.getTimer());
            }
            lastTimerValue = tile.getTimer();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            tile.setTimer(data);
        }
    }

    public ItemStack transferStackInSlot(final EntityPlayer player, final int slotIndex) {
        ItemStack itemstack = null;
        final Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (slotIndex < 4) {
                if (!this.mergeItemStack(itemstack2, 4, 39, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack2, 0, 4, false)) {
                return null;
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean mergeItemStack(final ItemStack stack, final int startIndex, final int endIndex, final boolean reverse) {
        boolean flag1 = false;
        int k = startIndex;
        if (reverse)
            k = endIndex - 1;

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && ((!reverse && k < endIndex) || (reverse && k >= startIndex))) {
                final Slot slot = (Slot) this.inventorySlots.get(k);
                final ItemStack itemstack1 = slot.getStack();
                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {
                    final int l = itemstack1.stackSize + stack.stackSize;
                    final int maxStackSize = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    if (l <= maxStackSize) {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < maxStackSize) {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }
                if (reverse)
                    --k;
                else
                    ++k;
            }
        }
        if (stack.stackSize > 0) {
            if (reverse)
                k = endIndex - 1;
            else
                k = startIndex;
            while ((!reverse && k < endIndex) || (reverse && k >= startIndex)) {
                final Slot slot = (Slot) this.inventorySlots.get(k);
                final ItemStack itemstack1 = slot.getStack();
                if (itemstack1 == null && slot.isItemValid(stack)) {
                    final int maxStackSize2 = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    if (stack.stackSize > maxStackSize2) {
                        slot.putStack(stack.splitStack(maxStackSize2));
                        slot.onSlotChanged();
                    } else {
                        slot.putStack(stack.copy());
                        slot.onSlotChanged();
                        stack.stackSize = 0;
                    }
                    flag1 = true;
                    break;
                }
                if (reverse)
                    --k;
                else
                    ++k;
            }
        }
        return flag1;
    }


}
