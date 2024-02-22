package com.neckitwin.testmod.common.container;

import com.neckitwin.testmod.common.slots.SlotBooster;
import com.neckitwin.testmod.common.slots.SlotOut;
import com.neckitwin.testmod.common.tile.TileBooster;
import com.neckitwin.testmod.common.tile.TileSampleGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBooster extends Container {
    private TileBooster tile;

    public ContainerBooster(InventoryPlayer player, TileBooster tile) {
        tile = tile;
        int i = 0;

        this.addSlotToContainer(new Slot(tile, 0, 42, 28));
        this.addSlotToContainer(new Slot(tile, 1, 42, 64));
        this.addSlotToContainer(new SlotOut(tile, 2, 87, 46));
        // ускорители
        this.addSlotToContainer(new SlotBooster(tile, 3, 141, 24));
        this.addSlotToContainer(new SlotBooster(tile, 4, 141, 46));
        this.addSlotToContainer(new SlotBooster(tile, 5, 141, 68));


        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 15 + j * 18, 100 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player, i, 15 + i * 18, 158));
        }
    }

    public ItemStack transferStackInSlot(final EntityPlayer player, final int slotIndex) {
        ItemStack itemstack = null;
        final Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (slotIndex < 6) {
                if (!this.mergeItemStack(itemstack2, 6, 42, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack2, 0, 6, false)) {
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
    protected boolean mergeItemStack(final ItemStack stack, final int startIndex, final int endIndex, final boolean reverse) {
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
                    if (stack.stackSize <= 0)
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
