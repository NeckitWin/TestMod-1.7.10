package com.neckitwin.testmod.common.gui;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.container.ContainerBooster;
import com.neckitwin.testmod.common.container.ContainerMechanism;
import com.neckitwin.testmod.common.tile.TileBooster;
import com.neckitwin.testmod.common.tile.TileMechanism;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiBooster extends GuiContainer {
    private TileBooster tile;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMod.MOD_ID + ":textures/gui/booster.png");
    private static final ResourceLocation ANIME = new ResourceLocation(TestMod.MOD_ID + ":textures/gui/anime.png");

    public GuiBooster(InventoryPlayer player , TileBooster tile) {
        super(new ContainerBooster(player, tile));
        this.tile = tile;
        this.xSize = 190;
        this.ySize = 190;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float size, int x, int y) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        mc.getTextureManager().bindTexture(ANIME);
        this.drawTexturedModalRect(guiLeft + 290, guiTop+40, 44, 0, 256, 256);

        if (tile.getTimer() > 0) {
            int animationFrame = (tile.getTimer() / 20) % 5;
            mc.getTextureManager().bindTexture(BACKGROUND);
            this.drawTexturedModalRect(guiLeft+64, guiTop+50, 190, 0, 15-3*animationFrame, 8);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.inventorySlots.detectAndSendChanges();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
    }
}
