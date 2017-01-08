package com.dragovorn.mccw.game.team;

import com.dragovorn.mccw.building.Building;
import com.dragovorn.mccw.building.BuildingReference;
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
        this.buildingManager = new BuildingManager() {
            private List<Building> buildings = new ArrayList<>();

            @Override
            public void build(BuildingReference buildingReference, Location location, int level) {
                Building building = buildingReference.build(this, location, level);

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
        };
    }

    @Override
    public void join(MCCWPlayer player) {
        player.sendMessage(MessageType.REGULAR, "You joined the " + getColour() + getName() + "&7 team!");

        if (player.getPlayer().getDisplayName().equals("Dragovorn")) {
            player.setPrefix("&6&lDeveloper &c");
        } else {
            player.setPrefix("&c");
        }
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
        return this.buildingManager;
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
}