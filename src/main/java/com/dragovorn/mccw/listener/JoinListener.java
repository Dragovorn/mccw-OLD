package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        MCCW.getInstance().registerPlayer(event.getPlayer());
    }
}