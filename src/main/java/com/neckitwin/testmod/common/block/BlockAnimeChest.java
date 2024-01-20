package com.neckitwin.testmod.common.block;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public class BlockAnimeChest extends Block {
    public BlockAnimeChest() {
        super(Material.rock);
        setBlockName("AnimeChest");
        setBlockTextureName(TestMod.MOD_ID+":AnimeChest");
        setCreativeTab(ModTab.INSTANCE);
    }
}
