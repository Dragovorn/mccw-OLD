package com.dragovorn.mccw.game.shop.kit;

import com.dragovorn.mccw.game.shop.upgrade.Health;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class None extends Class {

    public None() {
        super("No Class", 0);

        addUpgrade(new Health());
    }

    @Override
    public ItemStack getShopItem() {
        ItemStack stack = new ItemStack(Material.BARRIER);

        stack.getItemMeta().setDisplayName(getName());

        return stack;
    }
}
