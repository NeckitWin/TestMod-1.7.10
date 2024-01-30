package com.neckitwin.testmod;

import com.neckitwin.testmod.common.CommonProxy;
import com.neckitwin.testmod.common.network.RadiusPacket;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

import static com.neckitwin.testmod.TestMod.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME)
public class TestMod {
    public static final String MOD_ID = "testmod";
    public static final String MOD_NAME = "TestMod";
    public static final String VERSION = "1.0";
    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MOD_ID);

    @SidedProxy(
            clientSide = "com.neckitwin.testmod.common.ClientProxy",
            serverSide = "com.neckitwin.testmod.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NETWORK.registerMessage(RadiusPacket.Handler.class, RadiusPacket.class, 0, Side.CLIENT);
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
