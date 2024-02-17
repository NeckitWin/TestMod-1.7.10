package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import com.neckitwin.testmod.common.tile.TileMechanism;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMechanism extends BlockContainer{
    public BlockMechanism() {
        super(Material.wood);
        this.setBlockName("blockMechanism");
        this.setBlockTextureName(TestMod.MOD_ID +":mechanism");
        this.setCreativeTab(ModTab.INSTANCE);
        this.blockHardness = 0.5F;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileMechanism();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer playerEntity, int side, float hitX, float hitY, float hitZ) {
        playerEntity.openGui(TestMod.instance, 1, world, x, y, z);
        return true;
    }

    // если сломать блок, то выпадут все предметы
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileMechanism tile = (TileMechanism) world.getTileEntity(x, y, z);
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
