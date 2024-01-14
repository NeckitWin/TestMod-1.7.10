package com.neckitwin.testmod.common.handler;

import com.neckitwin.testmod.common.item.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static final ItemTest ITEM_TEST = new ItemTest();
    public static void register() {
        GameRegistry.registerItem(ITEM_TEST, "itemTest");
    }
}
