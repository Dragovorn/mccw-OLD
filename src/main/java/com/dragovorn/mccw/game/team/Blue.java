package com.dragovorn.mccw.game.team;

import com.dragovorn.mccw.building.BuildingReference;
import com.dragovorn.mccw.building.BuildingManager;
import com.dragovorn.mccw.exceptions.TeamException;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Blue implements ITeam {

    private List<MCCWPlayer> players;

    private BuildingManager buildingManager;

    public Blue() {
        this.players = new ArrayList<>();
        this.buildingManager = new BlueBuildingManager();
    }

    @Override
    public void join(MCCWPlayer player) {
        player.sendMessage(MessageType.REGULAR, "You joined the " + getColour() + getName() + "&7 team!");

        // other voodoo
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
        return "Blue";
    }

    @Override
    public ChatColor getColour() {
        return ChatColor.BLUE;
    }

    @Override
    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    @Override
    public String toString() {
        return getName();
    }

    private Blue outer() {
        return this;
    }

    @Override
    public List<MCCWPlayer> getPlayers() {
        return this.players;
    }

    private class BlueBuildingManager extends BuildingManager {

        private List<BuildingReference> buildingReferences;

        private BlueBuildingManager() {
            this.buildingReferences = new ArrayList<>();
        }

        @Override
        public void build(BuildingReference buildingReference, Location location) {
            this.buildingReferences.add(buildingReference.build(this, location));
        }

        public List<BuildingReference> getBuildingReferences() {
            return this.buildingReferences;
        }

        @Override
        public ITeam getTeam() {
            return Blue.this.outer();
        }
    }
}
