package com.dragovorn.mccw.game.shop.kit;

import com.dragovorn.mccw.game.shop.upgrade.Health;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class None extends Class {

    public None() {
        super("No Class", 1000);

        addUpgrade(new Health());
    }

    @Override
    public ItemStack getShopItem() {
        return new ItemStack(Material.BARRIER);
    }
}
