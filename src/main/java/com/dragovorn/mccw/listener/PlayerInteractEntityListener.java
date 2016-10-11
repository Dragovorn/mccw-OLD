package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.building.Building;
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
            for (Building building : player.getTeam().getBuildingManager().getBuildings()) {
                if (!building.isShop()) {
                    continue;
                }

                if (building.getShopKeeper().getUniqueId().equals(entity.getUniqueId())) {
                    building.openShop(player);
                    break;
                }
            }
        }
    }
}