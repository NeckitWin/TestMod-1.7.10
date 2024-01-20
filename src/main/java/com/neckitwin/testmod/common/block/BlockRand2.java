package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import com.neckitwin.testmod.common.tile.TileRand;
import com.neckitwin.testmod.common.tile.TileRand2;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRand2 extends BlockContainer {
    public BlockRand2() {
        super(Material.wood);
        setBlockName("BlockRand2");
        setBlockTextureName(TestMod.MOD_ID + ":BlockRand2");
        setCreativeTab(ModTab.INSTANCE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileRand2();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);

            if(tile instanceof TileRand2){
                TileRand2 storage = (TileRand2) tile;
                storage.spawnItem(world, x, y, z);
            }
        }
        return true;
    }
}
