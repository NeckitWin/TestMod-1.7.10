package com.neckitwin.testmod.common.handler;

import com.neckitwin.testmod.common.container.ContainerSampleGUI;
import com.neckitwin.testmod.common.gui.GuiSampleGUI;
import com.neckitwin.testmod.common.tile.TileSampleGUI;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (ID == 0 && tile instanceof TileSampleGUI)
            return new ContainerSampleGUI(player.inventory, (TileSampleGUI) tile);
        return null;
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (ID == 0 && tile instanceof TileSampleGUI)
            return new GuiSampleGUI(player.inventory, (TileSampleGUI) tile);
        return null;
    }
}
