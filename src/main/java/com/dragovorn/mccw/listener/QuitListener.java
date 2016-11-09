package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.timer.GameState;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        MCCWPlayer player = MCCW.getInstance().getPlayer(event.getPlayer());

        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(event.getPlayer().getDisplayName()).unregister();

        if (MCCW.getInstance().getState() == GameState.WAITING) {
            MCCW.getInstance().broadcast(MessageType.REGULAR, "%s (Level: %s) has disconnected! [%s/%s]", player.getPlayer().getDisplayName(), player.getLevel(), Bukkit.getOnlinePlayers().size() - 1, MCCW.MAX_PLAYERS);
        } else if (MCCW.getInstance().getState() == GameState.VOTING) {
            MCCW.getInstance().broadcast(MessageType.REGULAR, "%s (Level: %s) has disconnected! [%s/%s]", player.getPlayer().getDisplayName(), player.getLevel(), Bukkit.getOnlinePlayers().size() - 1, MCCW.MAX_PLAYERS);
            MCCW.getInstance().broadcast(MessageType.WARN, "Not enough players to proceed, returning to waiting phase!");
            // TODO Reset the countdown timer
            MCCW.getInstance().setState(GameState.WAITING);
        } else if (MCCW.getInstance().getState() == GameState.SAFE || MCCW.getInstance().getState() == GameState.WAR || MCCW.getInstance().getState() == GameState.DEATHMATCH) {
            // TODO start 5 min timer before banning them for 1 hour
        }

        MCCW.getInstance().deregisterPlayer(player);
    }
}