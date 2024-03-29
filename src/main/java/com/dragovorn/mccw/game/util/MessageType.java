package com.dragovorn.mccw.game.util;

import org.bukkit.ChatColor;

public enum MessageType {
    REGULAR(ChatColor.BLUE + "MCCW" + ChatColor.GRAY + "> "),
    COMBAT(ChatColor.RED + "COMBAT" + ChatColor.GRAY + "> "),
    WARN(ChatColor.YELLOW + "WARN> "),
    ERROR(ChatColor.RED + "ERROR> "),
    NONE("");

    private final String prefix;

    MessageType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public static String colourize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}