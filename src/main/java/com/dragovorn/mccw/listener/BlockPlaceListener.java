package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        MCCWPlayer player = MCCW.getInstance().getPlayer(event.getPlayer());

        if (event.getItemInHand().getItemMeta() != null && event.getItemInHand().getItemMeta().getDisplayName().contains(" §7(Place to Build)")) {
            if (player.isInTeam()) {
                event.setCancelled(true);

                String itemName = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName());
                itemName = itemName.replaceAll(" \\(Place to Build\\)", "");

                event.getBlockPlaced().setType(Material.AIR);
                player.getTeam().getBuildingManager().build(MCCW.getInstance().getBuildingByName(itemName), event.getBlockPlaced().getLocation().subtract(0, 1, 0), 1);
            } else {
                player.sendMessage(MessageType.ERROR, "You have to be on a team to build a building!");
            }
        } else {
            if (player.getPlayer().getGameMode() != GameMode.CREATIVE) {
                player.sendMessage(MessageType.ERROR, "You are not allowed to build!");
                event.setCancelled(true);
            }
        }
    }
}