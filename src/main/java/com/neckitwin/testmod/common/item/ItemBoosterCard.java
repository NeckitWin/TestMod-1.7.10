package com.neckitwin.testmod.common.item;

import com.neckitwin.testmod.TestMod;
import com.neckitwin.testmod.common.handler.ModTab;
import net.minecraft.item.Item;

public class ItemBoosterCard extends Item {
    public ItemBoosterCard() {
        setUnlocalizedName("BoosterCard");
        setTextureName(TestMod.MOD_ID + ":boosterCard");
        setCreativeTab(ModTab.INSTANCE);
    }
}
