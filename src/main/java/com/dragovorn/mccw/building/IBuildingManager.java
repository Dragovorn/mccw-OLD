package com.dragovorn.mccw.building;

import com.dragovorn.mccw.game.team.ITeam;
import org.bukkit.Location;

import java.util.List;

public interface IBuildingManager {

    void build(Building building, final Location location);

    List<Building> getBuildings();

    ITeam getTeam();
}