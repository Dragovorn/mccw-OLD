package com.dragovorn.mccw.game.shop;

import com.dragovorn.mccw.game.MCCWPlayer;
import org.bukkit.inventory.ItemStack;

public interface ShopItem {

    ItemStack getShopItem();

    long getCost();

    void apply(MCCWPlayer player);
}