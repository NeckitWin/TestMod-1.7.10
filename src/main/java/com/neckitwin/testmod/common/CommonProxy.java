package com.neckitwin.testmod.common;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.GuiHandler;
import com.neckitwin.testmod.common.handler.ModBlocks;
import com.neckitwin.testmod.common.handler.ModItems;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void registerGuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(TestMod.instance, new GuiHandler());
    }
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.register();
        ModBlocks.register();
    }

    public void init(FMLInitializationEvent event) {
        registerGuiHandler();
    }

    public void postInit(FMLPostInitializationEvent event) {}


}