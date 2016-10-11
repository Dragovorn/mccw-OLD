package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.Message;
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

        if (player.isInTeam()) {
            if (event.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.GRAY + "(Building)")) {
                String itemName = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName());
                itemName = itemName.replace(" (Building)", "");


            }
        } else {
            Message.send(player, MessageType.ERROR, "You have to be on a team to build a building!");
        }
    }
}