package com.dragovorn.mccw.building;

import com.dragovorn.mccw.game.team.ITeam;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;

public interface IBuildingManager {

    List<Block> build(Schematic schematic, final Location location);

    void build(BuildingReference buildingReference, Location location, int level);
    void levelBuilding(Building building);

    List<Building> getBuildings();

    ITeam getTeam();
}