package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(event.getPlayer().getDisplayName()).unregister();
        MCCW.getInstance().deregisterPlayer(event.getPlayer());
    }
}