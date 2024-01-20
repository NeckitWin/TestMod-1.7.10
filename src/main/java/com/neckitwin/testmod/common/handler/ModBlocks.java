package com.neckitwin.testmod.common.handler;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.block.*;
import com.neckitwin.testmod.common.tile.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static final BlockRand BLOCK_RAND = new BlockRand();
    public static final BlockRand2 BLOCK_RAND2 = new BlockRand2();
    public static final BlockCollector BLOCK_COLLECTOR = new BlockCollector();
    public static final BlockAnimeChest BLOCK_ANIME_CHEST = new BlockAnimeChest();
    public static void register() {
        GameRegistry.registerBlock(BLOCK_RAND, "blockRand");
        GameRegistry.registerBlock(BLOCK_RAND2, "blockRand2");
        GameRegistry.registerBlock(BLOCK_COLLECTOR, "blockCollector");
        GameRegistry.registerBlock(BLOCK_ANIME_CHEST, "blockAnimeChest");
        // Тайлы
        GameRegistry.registerTileEntity(TileRand.class, TestMod.MOD_ID+"tileRand");
        GameRegistry.registerTileEntity(TileCollector.class, TestMod.MOD_ID+"tileCollector");
        GameRegistry.registerTileEntity(TileRand2.class, TestMod.MOD_ID+"tileRand2");
    }
}