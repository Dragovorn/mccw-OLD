package com.dragovorn.mccw.command;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Test implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageType.colourize(MessageType.ERROR.getPrefix() + "Only players can you use this command!"));

            return false;
        }

        MCCWPlayer player = MCCW.getInstance().getPlayer((Player) sender);

        player.sendMessage(MessageType.REGULAR, "This is a test command!");

        return true;
    }
}
