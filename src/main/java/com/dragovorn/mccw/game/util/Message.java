package com.dragovorn.mccw.game.util;

import com.dragovorn.mccw.game.MCCWPlayer;
import org.bukkit.ChatColor;

public class Message {

    public static String colourize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void send(MCCWPlayer player, MessageType type, String message) {
        player.getPlayer().sendMessage(type.getPrefix() + message);
    }
}