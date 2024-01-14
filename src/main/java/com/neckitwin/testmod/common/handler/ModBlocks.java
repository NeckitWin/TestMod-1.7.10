package com.neckitwin.testmod.common.handler;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.block.*;
import com.neckitwin.testmod.common.tile.TileRand;
import cpw.mods.fml.common.registry.GameRegistry;
import scala.tools.nsc.backend.icode.Primitives;

public class ModBlocks {
    public static final BlockRand BLOCK_RAND = new BlockRand();
    public static void register() {
        GameRegistry.registerBlock(BLOCK_RAND, "blockRand");
        // Тайлы
        GameRegistry.registerTileEntity(TileRand.class, TestMod.MOD_ID+"tileRand");
    }
}