package com.neckitwin.testmod.common.handler;

import com.neckitwin.testmod.TestMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModTab extends CreativeTabs {
    public static final ModTab INSTANCE = new ModTab();

    private ModTab() {
        super(TestMod.MOD_ID);
        setBackgroundImageName("item_search.png");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return ModItems.ITEM_TEST;
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}