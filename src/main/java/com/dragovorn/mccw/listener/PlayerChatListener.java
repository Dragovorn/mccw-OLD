package com.dragovorn.mccw.listener;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        MCCWPlayer player = MCCW.getInstance().getPlayer(event.getPlayer());

        if (!player.isInTeam() || event.getMessage().startsWith("@a")) {
            event.setMessage(event.getMessage().replace("@a", ""));

            event.setFormat(MessageType.colourize("&7[ALL] %s&7: ") + "%s");

            if (player.getPlayer().getDisplayName().equals("Dragovorn")) {
                event.setFormat(MessageType.colourize("&7[ALL] %s&7:&f %s"));
            }
        } else {
            for (MCCWPlayer players : player.getTeam().getPlayers()) {
                players.sendMessage(MessageType.NONE, player.getTeam().getColour() + "[ALLIES] %s&f: %s", player.getPlayer().getDisplayName(), event.getMessage());
            }

            event.setCancelled(true);
        }
    }
}