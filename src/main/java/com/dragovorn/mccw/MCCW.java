package com.dragovorn.mccw;

import com.dragovorn.mccw.building.BuildingManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MCCW extends JavaPlugin {

    private File schematic;

    private BuildingManager buildingManager;

    private static MCCW instance;

    @Override
    public void onLoad() {
        instance = this;

        this.buildingManager = new BuildingManager();

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        this.schematic = new File(this.getDataFolder(), "schematics");

        if (!this.schematic.exists()) {
            this.schematic.mkdirs();
        }

        this.buildingManager.loadSchematics(this.schematic);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static MCCW getInstance() {
        return instance;
    }

    public File getSchematicFolder() {
        return this.schematic;
    }

    public BuildingManager getBuildingManager() {
        return this.buildingManager;
    }
}