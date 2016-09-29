package com.dragovorn.mccw.game.team;

import com.dragovorn.mccw.building.BuildingManager;
import com.dragovorn.mccw.game.MCCWPlayer;
import org.bukkit.ChatColor;

import java.util.List;

public interface ITeam {

    void join(MCCWPlayer player);
    void leave(MCCWPlayer player);

    String getName();

    ChatColor getColour();

    BuildingManager getBuildingManager();

    List<MCCWPlayer> getPlayers();
}