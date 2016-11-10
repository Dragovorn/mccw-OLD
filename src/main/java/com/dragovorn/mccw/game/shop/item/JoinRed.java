package com.dragovorn.mccw.game.shop.item;

import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class JoinRed extends Item {

    public JoinRed() {
        super("Join Red Team", 1, 1);
    }

    @Override
    public void onRightClick(MCCWPlayer player) {
        player.sendMessage(MessageType.REGULAR, "It worked!");
    }

    @Override
    public ItemStack getShopItem() {
        return new ItemStack(Material.BARRIER);
    }

    @Override
    public ItemStack getItem() {
        ItemStack stack = new ItemStack(Material.WOOL);

        Wool wool = (Wool) stack.getItemMeta();

        wool.setColor(DyeColor.RED);
        stack = wool.toItemStack();

        stack.getItemMeta().setDisplayName(MessageType.colourize("&cJoin Red Team &7- (Right-Click)"));

        return stack;
    }

    @Override
    long[] getCosts() {
        return new long[] {0};
    }
}
