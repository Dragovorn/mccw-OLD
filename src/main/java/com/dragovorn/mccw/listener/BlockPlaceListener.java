package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        event.setCancelled(true);

        MCCWPlayer player = MCCW.getInstance().getPlayer(event.getPlayer());

        if (event.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.GRAY + "(Building)")) {
            if (player.isInTeam()) {
                String itemName = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName());
                itemName = itemName.replace(" (Building)", "");

                // TODO Building Block placed, build the building
            } else {
                player.sendMessage(MessageType.ERROR, "You have to be on a team to build a building!");
            }
        }
    }
}