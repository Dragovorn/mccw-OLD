package com.dragovorn.mccw.game.team;

import com.dragovorn.mccw.building.Building;
import com.dragovorn.mccw.building.BuildingManager;
import com.dragovorn.mccw.exceptions.TeamException;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Red implements ITeam {

    private List<MCCWPlayer> players;

    private BuildingManager buildingManager;

    public Red() {
        this.players = new ArrayList<>();
        this.buildingManager = new RedBuildingManager();
    }

    @Override
    public void join(MCCWPlayer player) {
        if (!player.isInTeam()) {
            players.add(player);
        } else {
            throw new TeamException(player.getPlayer().getName() + " is already in a team");
        }

        player.sendMessage(MessageType.REGULAR, "You joined the " + getColour() + getName() + "&7 team!");
    }

    @Override
    public void leave(MCCWPlayer player) {
        if (player.isInTeam() && this.players.contains(player)) {
            this.players.remove(player);
        } else {
            throw new TeamException(player.getPlayer().getName() + " either isn't in a team or isn't in the " + getName() + " team");
        }

        player.sendMessage(MessageType.REGULAR, "You left the " + getColour() + getName() + "&7 team!");
    }

    @Override
    public String getName() {
        return "Red";
    }

    @Override
    public ChatColor getColour() {
        return ChatColor.RED;
    }

    @Override
    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    @Override
    public List<MCCWPlayer> getPlayers() {
        return this.players;
    }

    @Override
    public String toString() {
        return getName();
    }

    private Red outer() {
        return this;
    }

    private class RedBuildingManager extends BuildingManager {

        private List<Building> buildings;

        private RedBuildingManager() {
            this.buildings = new ArrayList<>();
        }

        @Override
        public void build(Building building, Location location) {
            building.build(this, location);

            this.buildings.add(building);
        }

        @Override
        public List<Building> getBuildings() {
            return this.buildings;
        }

        @Override
        public ITeam getTeam() {
            return Red.this.outer();
        }
    }
}