package com.neckitwin.testmod.common.item;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import net.minecraft.item.Item;

public class ItemNecroStaff extends Item {
    public ItemNecroStaff() {
        setUnlocalizedName("NecroStaff");
        setTextureName(TestMod.MOD_ID+":NecroStaff");
        setCreativeTab(ModTab.INSTANCE);
    }
}
