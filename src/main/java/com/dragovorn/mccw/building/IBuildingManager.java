package com.dragovorn.mccw.building;

import org.bukkit.Location;

import java.util.List;

public interface IBuildingManager {

    void build(Schematic schematic, final Location location);

    List<Building> getBuildings();
}