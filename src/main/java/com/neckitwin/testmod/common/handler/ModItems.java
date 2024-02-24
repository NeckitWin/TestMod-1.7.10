package com.neckitwin.testmod.common.handler;

import com.neckitwin.testmod.common.item.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static final ItemTest ITEM_TEST = new ItemTest();
    public static final ItemNecroStaff ITEM_NECRO_STAFF = new ItemNecroStaff();
    public static final ItemBoosterCard ITEM_BOOSTER_CARD = new ItemBoosterCard();
    public static void register() {
        GameRegistry.registerItem(ITEM_TEST, "itemTest");
        GameRegistry.registerItem(ITEM_NECRO_STAFF, "itemNecroStaff");
        GameRegistry.registerItem(ITEM_BOOSTER_CARD, "itemBoosterCard");
    }
}
