package com.dragovorn.mccw;

import com.dragovorn.mccw.building.SchematicManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MCCW extends JavaPlugin {

    private File schematics;

    private SchematicManager schematicManager;

    private static MCCW instance;

    @Override
    public void onLoad() {
        instance = this;
        this.schematicManager = new SchematicManager();

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        this.schematics = new File(this.getDataFolder(), "schematics");

        if (!this.schematics.exists()) {
            this.schematics.mkdirs();
        }

        this.schematicManager.loadSchematics(this.schematics);
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
        return this.schematics;
    }

    public SchematicManager getBuildingManager() {
        return this.schematicManager;
    }
}