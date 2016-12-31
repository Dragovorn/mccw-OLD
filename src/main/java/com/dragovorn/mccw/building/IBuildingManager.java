package com.dragovorn.mccw.building;

import com.dragovorn.mccw.game.team.ITeam;
import org.bukkit.Location;

import java.util.List;

public interface IBuildingManager {

    void build(Schematic schematic, final Location location);

    void build(BuildingReference buildingReference, final Location location);

    List<BuildingReference> getBuildingReferences();

    ITeam getTeam();
}