package com.neckitwin.testmod.common.gui;


import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.container.ContainerSampleGUI;
import com.neckitwin.testmod.common.tile.TileSampleGUI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSampleGUI extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMod.MOD_ID + ":textures/gui/sampleGUI.png");
    private static final ResourceLocation ANIME = new ResourceLocation(TestMod.MOD_ID + ":textures/gui/anime.png");
    private TileSampleGUI tile;

    public GuiSampleGUI(InventoryPlayer player,TileSampleGUI tile) {
        super(new ContainerSampleGUI(player, tile));
        this.tile = tile;
        this.xSize = 256;
        this.ySize = 252;
    }



    @Override
    public void drawGuiContainerBackgroundLayer(float size, int x, int y) {
        // Прозрачность
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        // Тут код
//        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        mc.getTextureManager().bindTexture(ANIME);
        this.drawTexturedModalRect(guiLeft + 290, guiTop+40, 36, 0, 256, 256);
        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
    }
}
