package com.neckitwin.testmod.common.item;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import net.minecraft.item.Item;

public class ItemTest extends Item {
    public ItemTest(){
        setUnlocalizedName("ItemTest");
        setTextureName(TestMod.MOD_ID+":ItemTest");
        setCreativeTab(ModTab.INSTANCE);

    }
}
