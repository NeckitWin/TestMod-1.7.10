package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAnime extends Block {
    public BlockAnime() {
        super(Material.wood);
        setBlockName("Anime");
        setBlockTextureName(TestMod.MOD_ID+":Anime");
        setCreativeTab(ModTab.INSTANCE);
        setHardness(0.5F);
    }

    @Override // устанавливаем свет
    public int getLightValue() {
        return 15;
    }
}
