package com.dragovorn.mccw;

import com.dragovorn.mccw.building.BuildingReference;
import com.dragovorn.mccw.building.SchematicManager;
import com.dragovorn.mccw.command.Test;
import com.dragovorn.mccw.exceptions.BuildingException;
import com.dragovorn.mccw.exceptions.PlayerNotRegisteredException;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.team.Blue;
import com.dragovorn.mccw.game.team.ITeam;
import com.dragovorn.mccw.game.team.Red;
import com.dragovorn.mccw.game.timer.GameState;
import com.dragovorn.mccw.game.util.MessageType;
import com.dragovorn.mccw.listener.*;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.io.File;
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

    private ImmutableList<BuildingReference> buildingReferences;

    private static MCCW instance;

    private GameState state;

    public static final String GAMEPLAY_VERSION = "0.01a";

    public static final int MAX_PLAYERS = 20;

    @Override
    public void onLoad() {
        instance = this;
        this.state = GameState.WAITING;
        this.schematicManager = new SchematicManager();
        this.players = new ArrayList<>();
        this.teams = new ImmutableList.Builder<ITeam>().add(new Blue()).add(new Red()).build();

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        this.schematics = new File(this.getDataFolder(), "schematics");

        if (!this.schematics.exists()) {
            this.schematics.mkdirs();
        }

        // TODO have it figure out map rotation based off of zip files stored on Amazon S3
        // TODO load the schematics based on what map was selected (leave this as is for testing purposes)

//        this.schematicManager.loadSchematics(this.schematics);
    }

    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();

        getCommand("test").setExecutor(new Test());

        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new QuitListener(), this);
        manager.registerEvents(new BlockPlaceListener(), this);
        manager.registerEvents(new PlayerInteractEntityListener(), this);
        manager.registerEvents(new PlayerChatListener(), this);
        manager.registerEvents(new RightClickListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
        this.schematicManager = null;
        this.schematics = null;
        this.teams = null;
        this.players = null;
        this.exp = null;

        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
    }

    public static MCCW getInstance() {
        return instance;
    }

    public MCCWPlayer registerPlayer(Player player) {
        MCCWPlayer mccwPlayer = new MCCWPlayer(player);

        this.players.add(mccwPlayer);

        return mccwPlayer;
    }

    public void deregisterPlayer(Player player) {
        for (MCCWPlayer players : this.players) {
            if (players.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                deregisterPlayer(players);

                return;
            }
        }
    }

    public void deregisterPlayer(MCCWPlayer player) {
        this.players.remove(player);

        // TODO update all player info.
    }

    public void broadcast(MessageType type, String message, Object... objs) {
        Bukkit.broadcastMessage(type.getPrefix() + MessageType.colourize(String.format(message, objs)));
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public List<MCCWPlayer> getPlayers() {
        return this.players;
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

    public BuildingReference getBuildingByName(String name) {
        for (BuildingReference buildingReference : this.buildingReferences) {
            if (buildingReference.getName().equals(name)) {
                return buildingReference;
            }
        }

        throw new BuildingException("could not find building " + name);
    }

    public SchematicManager getBuildingManager() {
        return this.schematicManager;
    }

    public GameState getState() {
        return this.state;
    }
}