package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        event.setCancelled(true);

        Entity entity = event.getRightClicked();
        MCCWPlayer player = MCCW.getInstance().getPlayer(event.getPlayer());

        if (player.isInTeam()) {
//            for (BuildingReference buildingReference : player.getTeam().getBuildingManager().getBuildingReferences()) {
//                if (!buildingReference.isShop()) {
//                    continue;
//                }

//                if (buildingReference.getShopKeeper().getUniqueId().equals(entity.getUniqueId())) {
//                    buildingReference.openShop(player);
//                    break;
//                }
//            }
        }
    }
}