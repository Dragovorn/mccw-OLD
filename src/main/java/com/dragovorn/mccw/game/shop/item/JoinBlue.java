package com.dragovorn.mccw.game.shop.item;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JoinBlue extends Item {

    public JoinBlue() {
        super("Join Red Team", 1, 1);
    }

    @Override
    public void onRightClick(MCCWPlayer player) {
        player.joinTeam(MCCW.getInstance().getTeams().get(0));
    }

    @Override
    public ItemStack getShopItem() {
        return new ItemStack(Material.BARRIER);
    }

    @Override
    public ItemStack getItem() {
        ItemStack stack = new ItemStack(Material.WOOL, 1, (byte) 11);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(MessageType.colourize("&9Join Blue Team &7- (Right-Click)"));

        stack.setItemMeta(meta);

        return stack;
    }

    @Override
    long[] getCosts() {
        return new long[] {0};
    }
}