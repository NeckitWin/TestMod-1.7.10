package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import com.neckitwin.testmod.common.tile.TileCollector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class BlockCollector extends BlockContainer {
    public BlockCollector() {
        super(Material.wood);
        setBlockName("Collector");
        setBlockTextureName(TestMod.MOD_ID + ":Collector");
        setCreativeTab(ModTab.INSTANCE);
        setHardness(1.0F);
    }

    // Привязываем тайл
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCollector();
    }

    // Если получил красный сигнал
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileCollector) {
            TileCollector radius = (TileCollector) tile;
            radius.handleRedstone(world.isBlockIndirectlyGettingPowered(x, y, z));
        }
    }

    // При нажатии по блоку
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            // Получаем тайл
            TileEntity tile = world.getTileEntity(x, y, z);
            // Проверяем, что это нужный нам тайл
            if (tile instanceof TileCollector) {
                TileCollector radius = (TileCollector) tile;
                // Берём метод активации
                radius.handleInputStack(activator, activator.getHeldItem());
            }
        }
        return true;
    }
}