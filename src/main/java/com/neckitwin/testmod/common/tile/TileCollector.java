package com.neckitwin.testmod.common.tile;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.network.RadiusPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TileCollector extends TileEntity {
    public int RADIUS = 3; // Задаем радиус для поиска объектов вокруг тайла

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) { // Проверяем, что код выполняется на стороне сервера
            double x = (double) this.xCoord + 0.5D; // Получаем центр блока по оси X
            double y = (double) this.yCoord + 0.5D; // Получаем центр блока по оси Y
            double z = (double) this.zCoord + 0.5D; // Получаем центр блока по оси Z
            // Получаем список сущностей типа EntityItem в пределах заданного объема
            List<Entity> entities = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - RADIUS, y - RADIUS, z - RADIUS, x + RADIUS, y + RADIUS, z + RADIUS));

            for (Entity entity : entities) { // Итерируем по списку найденных сущностей
                if (entity instanceof EntityItem) { // Проверяем, является ли сущность объектом EntityItem
                    EntityItem entityItem = (EntityItem) entity; // Приводим сущность к типу EntityItem
                    ItemStack stack = entityItem.getEntityItem().copy(); // Создаем копию ItemStack из EntityItem

                    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) { // Итерируем по всем возможным направлениям
                        int dx = xCoord + direction.offsetX; // Вычисляем координату X для соседнего блока
                        int dy = yCoord + direction.offsetY; // Вычисляем координату Y для соседнего блока
                        int dz = zCoord + direction.offsetZ; // Вычисляем координату Z для соседнего блока
                        TileEntity tileEntity = this.worldObj.getTileEntity(dx, dy, dz); // Получаем тайловую сущность соседнего блока

                        if (tileEntity instanceof IInventory) { // Проверяем, является ли тайловая сущность соседнего блока инвентарем
                            IInventory inventory = (IInventory) tileEntity; // Приводим тайловую сущность к интерфейсу IInventory

                            // Ищем такой же предмет в инвентаре
                            for (int i = 0; i < inventory.getSizeInventory(); i++) { // Итерируем по всем слотам инвентаря
                                ItemStack existingStack = inventory.getStackInSlot(i); // Получаем ItemStack из текущего слота

                                if (existingStack != null && existingStack.getItem() == stack.getItem() && existingStack.getItemDamage() == stack.getItemDamage()) {
                                    int spaceLeft = existingStack.getMaxStackSize() - existingStack.stackSize;

                                    if (spaceLeft >= stack.stackSize) {
                                        existingStack.stackSize += stack.stackSize; // Увеличиваем количество предметов в существующем стаке
                                        entityItem.setDead(); // Удаляем сущность EntityItem
                                        return; // Завершаем выполнение метода
                                    }
                                }
                            }

                            // Пробуем создать в пустом слоте
                            for (int i = 0; i < inventory.getSizeInventory(); i++) { // Итерируем по всем слотам инвентаря
                                if (inventory.isItemValidForSlot(i, stack)) { // Проверяем, подходит ли предмет для данного слота
                                    ItemStack existingStack = inventory.getStackInSlot(i); // Получаем ItemStack из текущего слота

                                    if (existingStack == null) {
                                        int spaceLeft = Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize());

                                        if (spaceLeft >= stack.stackSize) {
                                            ItemStack remainingStack = stack.copy(); // Создаем копию ItemStack
                                            inventory.setInventorySlotContents(i, remainingStack); // Устанавливаем ItemStack в слот инвентаря
                                            entityItem.setDead(); // Удаляем сущность EntityItem
                                            return; // Завершаем выполнение метода
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
            TestMod.NETWORK.sendTo(new RadiusPacket(RADIUS, xCoord, yCoord, zCoord), (EntityPlayerMP) player);
        } else {
            player.addChatComponentMessage(new ChatComponentText("Коллектор выключен"));
        }
        particles();
    }

    public void particles() {
        if (this.worldObj.isRemote) { // Сторона клиента
            double x = (double) this.xCoord + 0.5D;
            double y = (double) this.yCoord + 0.5D;
            double z = (double) this.zCoord + 0.5D;

            for (int dx = -RADIUS; dx <= RADIUS; dx++) {
                for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                    // Проверяем, находимся ли мы на внешнем кольце
                    if (Math.abs(dx) == RADIUS || Math.abs(dz) == RADIUS) {
                        double particleX = x + dx;
                        double particleZ = z + dz;
                        // Спавним партиклы за одну итерацию
                        for (int i = 0; i < 50; i++) {
                            this.worldObj.spawnParticle("portal", particleX, y, particleZ, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
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