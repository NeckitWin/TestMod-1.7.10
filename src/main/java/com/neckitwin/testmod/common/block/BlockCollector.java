package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import com.neckitwin.testmod.common.tile.TileCollector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.BlockHopper;

public class BlockCollector extends BlockContainer {
    public BlockCollector() {
        super(Material.rock);
        setBlockName("Collector");
        setBlockTextureName(TestMod.MOD_ID+":Collector");
        setCreativeTab(ModTab.INSTANCE);
        setHardness(1.5F);
    }

    // Привязываем тайл
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCollector();
    }
}
