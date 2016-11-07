package com.dragovorn.mccw.game.util;

import com.dragovorn.mccw.game.MCCWPlayer;
import org.bukkit.ChatColor;

/**
 * @deprecated moving this into a sendMessage method in {@link MCCWPlayer}
 */
@Deprecated
public class Message {

    @Deprecated
    public static String colourize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    @Deprecated
    public static void send(MCCWPlayer player, MessageType type, String message, Object... objs) {
        player.getPlayer().sendMessage(String.format(colourize(type.getPrefix() + message), objs));
    }
}