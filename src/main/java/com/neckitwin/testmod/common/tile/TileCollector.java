package com.neckitwin.testmod.common.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TileCollector extends TileEntity {
    private int RADIUS = 5;

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

                            // Ищем такой же предмет в инвентаре
                            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                                ItemStack existingStack = inventory.getStackInSlot(i);

                                if (existingStack != null && existingStack.getItem() == stack.getItem() && existingStack.getItemDamage() == stack.getItemDamage()) {
                                    int spaceLeft = existingStack.getMaxStackSize() - existingStack.stackSize;

                                    if (spaceLeft >= stack.stackSize) {
                                        existingStack.stackSize += stack.stackSize;
                                        entityItem.setDead();
                                        return;
                                    }
                                }
                            }

                            // Пробуем создать в пустом слоте
                            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                                if (inventory.isItemValidForSlot(i, stack)) {
                                    ItemStack existingStack = inventory.getStackInSlot(i);

                                    if (existingStack == null) {
                                        int spaceLeft = Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize());

                                        if (spaceLeft >= stack.stackSize) {
                                            ItemStack remainingStack = stack.copy();
                                            inventory.setInventorySlotContents(i, remainingStack);
                                            entityItem.setDead();
                                            return;
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

    public void handleInputStack(EntityPlayer player, ItemStack stack) {
        if (RADIUS != 0) {
            if (RADIUS < 8) {
                RADIUS++;
            } else if (RADIUS >= 8) {
                RADIUS = 1;
            }
            player.addChatComponentMessage(new ChatComponentText("Радиус: " + RADIUS + " блоков"));
        } else {
            player.addChatComponentMessage(new ChatComponentText("Коллектор выключен"));
        }
    }

    public void handleRedstone(boolean blockIndirectlyGettingPowered) {
        if (blockIndirectlyGettingPowered) {
            RADIUS = 0;
        }
        if (RADIUS == 0 && !blockIndirectlyGettingPowered) {
            RADIUS = 1;
        }
    }
}