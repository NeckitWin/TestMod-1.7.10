package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import com.neckitwin.testmod.common.tile.TileBooster;
import com.neckitwin.testmod.common.tile.TileSampleGUI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBooster extends BlockContainer {
    public BlockBooster() {
        super(Material.wood);
        this.setBlockName("BlockBooster");
        this.setBlockTextureName(TestMod.MOD_ID + ":booster");
        this.setCreativeTab(ModTab.INSTANCE);
        this.blockHardness = 0.5F;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileBooster();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer playerEntity, int side, float hitX, float hitY, float hitZ) {
        playerEntity.openGui(TestMod.instance, 2, world, x, y, z);
        return true;
    }

    // если сломать блок, то выпадут все предметы
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileBooster tile = (TileBooster) world.getTileEntity(x, y, z);
        if (tile != null) {
            for (int i = 0; i < tile.getSizeInventory(); i++) {
                if (tile.getStackInSlot(i) != null) {
                    this.dropBlockAsItem(world, x, y, z, tile.getStackInSlot(i));
                }
            }
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }
}
