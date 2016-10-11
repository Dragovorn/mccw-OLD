package com.dragovorn.mccw;

import com.dragovorn.mccw.building.Building;
import com.dragovorn.mccw.building.SchematicManager;
import com.dragovorn.mccw.exceptions.PlayerNotRegisteredException;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.team.Blue;
import com.dragovorn.mccw.game.team.ITeam;
import com.dragovorn.mccw.game.team.Red;
import com.dragovorn.mccw.listener.BlockPlaceListener;
import com.dragovorn.mccw.listener.JoinListener;
import com.dragovorn.mccw.listener.PlayerInteractEntityListener;
import com.dragovorn.mccw.listener.QuitListener;
import com.dragovorn.mccw.utils.Passwords;
import com.dragovorn.mccw.utils.SQL;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MCCW extends JavaPlugin {

    public int[] exp = new int[] {
            1000, 2000, 3000, 4000
    };

    private File schematics;

    private SchematicManager schematicManager;

    private ImmutableList<ITeam> teams;

    private List<MCCWPlayer> players;

    private ImmutableList<Building> buildings;

    private SQL sql;

    private static MCCW instance;

    @Override
    public void onLoad() {
        instance = this;
        this.schematicManager = new SchematicManager();
        this.players = new ArrayList<>();
        this.teams = new ImmutableList.Builder<ITeam>().add(new Blue()).add(new Red()).build();

        try {
            this.sql = new SQL("dragovorn.com", 8080, "mccw", "mccw", Passwords.sql);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        this.schematics = new File(this.getDataFolder(), "schematics");

        if (!this.schematics.exists()) {
            this.schematics.mkdirs();
        }

        this.schematicManager.loadSchematics(this.schematics);

        this.buildings = new ImmutableList.Builder<Building>().add(new Building("Town Hall", true, 1, this.schematicManager.getSchematicByName("townhall"))).build();
    }

    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new QuitListener(), this);
        manager.registerEvents(new BlockPlaceListener(), this);
        manager.registerEvents(new PlayerInteractEntityListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
        this.schematicManager = null;
        this.schematics = null;
        this.teams = null;
        this.players = null;
        this.sql = null;
        this.exp = null;
    }

    public static MCCW getInstance() {
        return instance;
    }

    public void registerPlayer(Player player) {
        this.players.add(new MCCWPlayer(player));
    }

    public void deregisterPlayer(Player player) {
        for (MCCWPlayer players : this.players) {
            if (players.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                this.players.remove(players);

                return;
            }
        }
    }

    public MCCWPlayer getPlayer(Player player) {
        for (MCCWPlayer players : this.players) {
            if (players.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return players;
            }
        }

        throw new PlayerNotRegisteredException(player.getName() + " isn't registered");
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