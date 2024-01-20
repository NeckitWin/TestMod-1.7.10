package com.neckitwin.testmod.common.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TileCollector extends TileEntity {
    private static final int RADIUS = 5;

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            double x = (double) this.xCoord + 0.5D;
            double y = (double) this.yCoord + 0.5D;
            double z = (double) this.zCoord + 0.5D;
            List<Entity> entities = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - RADIUS, y - RADIUS, z - RADIUS, x + RADIUS, y + RADIUS, z + RADIUS));

            for (Entity entity : entities) {
                if (entity instanceof EntityItem) {
                    EntityItem entityItem = (EntityItem) entity;
                    ItemStack stack = entityItem.getEntityItem().copy(); // Копируем предмет

                    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                        int dx = xCoord + direction.offsetX;
                        int dy = yCoord + direction.offsetY;
                        int dz = zCoord + direction.offsetZ;
                        TileEntity tileEntity = this.worldObj.getTileEntity(dx, dy, dz);

                        if (tileEntity instanceof IInventory) {
                            IInventory inventory = (IInventory) tileEntity;

                            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                                if (inventory.isItemValidForSlot(i, stack)) {
                                    ItemStack existingStack = inventory.getStackInSlot(i);

                                    // Проверка, есть ли слот или совпадает ли предмет
                                    if (existingStack == null || (existingStack.getItem() == stack.getItem() && existingStack.getItemDamage() == stack.getItemDamage())) {
                                        int spaceLeft = existingStack == null ? Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize()) : existingStack.getMaxStackSize() - existingStack.stackSize;

                                        // Проверка есть ли место в инвентаре
                                        if (spaceLeft >= stack.stackSize) {
                                            ItemStack remainingStack = existingStack == null ? stack : existingStack.copy();
                                            remainingStack.stackSize += stack.stackSize;
                                            inventory.setInventorySlotContents(i, remainingStack);
                                            entityItem.setDead();
                                            return; // Стоп
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}