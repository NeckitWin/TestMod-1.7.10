package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import com.neckitwin.testmod.common.tile.TileRand;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRand extends BlockContainer {
    public BlockRand() {
        super(Material.wood);
        setBlockName("BlockRand");
        setBlockTextureName(TestMod.MOD_ID + ":BlockRand");
        setCreativeTab(ModTab.INSTANCE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileRand();
    }
}
