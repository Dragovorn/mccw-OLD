package com.dragovorn.mccw;

import com.dragovorn.mccw.building.SchematicManager;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.team.Blue;
import com.dragovorn.mccw.game.team.ITeam;
import com.dragovorn.mccw.game.team.Red;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MCCW extends JavaPlugin {

    public int[] exp = new int[] {
            1000, 2000, 3000, 4000
    };

    private File schematics;

    private SchematicManager schematicManager;

    private List<ITeam> teams;

    private List<MCCWPlayer> players;

    private static MCCW instance;

    @Override
    public void onLoad() {
        instance = this;
        this.schematicManager = new SchematicManager();
        this.teams = new ArrayList<>();
        this.players = new ArrayList<>();

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
        this.teams.add(new Red());
        this.teams.add(new Blue());
    }

    @Override
    public void onDisable() {
        instance = null;
        this.schematicManager = null;
        this.schematics = null;
        this.teams = null;
    }

    public static MCCW getInstance() {
        return instance;
    }

    public void registerPlayer(Player player) {
        this.players.add(new MCCWPlayer(player));
    }

    public void deregisterPlayer(Player player) {
        for (int x = 0; x < this.players.size(); x++) {
            if (this.players.get(x).getPlayer().getUniqueId().equals(player.getUniqueId())) {
                this.players.remove(x);

                return;
            }
        }
    }

    public List<ITeam> getTeams() {
        return this.teams;
    }

    public File getSchematicFolder() {
        return this.schematics;
    }

    public SchematicManager getBuildingManager() {
        return this.schematicManager;
    }
}