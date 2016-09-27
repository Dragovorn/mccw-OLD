package com.dragovorn.mccw;

import com.dragovorn.mccw.building.BuildingManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCCW extends JavaPlugin {

    private BuildingManager buildingManager;

    private static MCCW instance;

    @Override
    public void onLoad() {
        instance = this;

        this.buildingManager = new BuildingManager();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public MCCW getInstance() {
        return instance;
    }

    public BuildingManager getBuildingManager() {
        return this.buildingManager;
    }
}