package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.shop.item.JoinRed;
import com.dragovorn.mccw.game.timer.GameState;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        MCCWPlayer player = MCCW.getInstance().registerPlayer(event.getPlayer());

        if (player.getPlayer().getDisplayName().equals("Dragovorn")) {
            player.setPrefix("&6&lDeveloper &7");
        } else {
            player.setPrefix("&7");
        }

        player.sendMessage(MessageType.REGULAR, "Welcome to MCCW Patch: &a%s &7Engine Version: &a%s&7!", MCCW.GAMEPLAY_VERSION, MCCW.getInstance().getDescription().getVersion());

        if (MCCW.getInstance().getState() == GameState.WAITING) {
            MCCW.getInstance().broadcast(MessageType.REGULAR, "%s (Level: %s) has connected! [%s/%s]", player.getPlayer().getDisplayName(), player.getLevel(), Bukkit.getOnlinePlayers().size(), MCCW.MAX_PLAYERS);
        }

        player.addItem(new JoinRed());

        if (Bukkit.getOnlinePlayers().size() == MCCW.MAX_PLAYERS) {
            MCCW.getInstance().setState(GameState.VOTING);
            // TODO begin voting phase
        }
    }
}