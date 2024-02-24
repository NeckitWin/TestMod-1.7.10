package com.neckitwin.testmod.common.slots;

import com.neckitwin.testmod.common.handler.ModItems;
import com.neckitwin.testmod.common.item.ItemBoosterCard;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBooster extends Slot {
    public SlotBooster(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == ModItems.ITEM_BOOSTER_CARD;
    }
}
