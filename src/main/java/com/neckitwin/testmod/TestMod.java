package com.neckitwin.testmod;

import com.neckitwin.testmod.common.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import static com.neckitwin.testmod.TestMod.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME)
public class TestMod {
    public static final String MOD_ID = "testmod";
    public static final String MOD_NAME = "TestMod";
    public static final String VERSION = "1.0";

    @SidedProxy(
            clientSide = "com.neckitwin.testmod.common.ClientProxy",
            serverSide = "com.neckitwin.testmod.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
