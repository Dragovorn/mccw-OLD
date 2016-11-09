package com.dragovorn.mccw;

import com.dragovorn.mccw.building.Building;
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
import com.dragovorn.mccw.utils.Passwords;
import com.dragovorn.mccw.utils.SQL;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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

        try {
            this.sql = new SQL("dragovorn.com", 8080, "mccw", "mccw", Passwords.sql);
        } catch (SQLException exception) {
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

        /* Leave this commented until I've got some form of schematics */
//        Building townHall = new Building("Town Hall", true, 0, this.schematicManager.getSchematicByName("townhall"));

//        townHall.addItem(new None());

//        this.buildings = new ImmutableList.Builder<Building>().add(townHall).build();
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

        String statement = "UPDATE players SET name=?, xp=?, level=?, disconnected=?, games=?, kills=?, deaths=?, wins=?, losses=? WHERE uuid=?";

        try {
            PreparedStatement preparedStatement = this.sql.getConnection().prepareStatement(statement);

            preparedStatement.setString(1, player.getPlayer().getName());
            preparedStatement.setDouble(2, player.getExp());
            preparedStatement.setInt(3, player.getLevel());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(5, player.getGames());
            preparedStatement.setInt(6, player.getKills());
            preparedStatement.setInt(7, player.getDeaths());
            preparedStatement.setInt(8, player.getWins());
            preparedStatement.setInt(9, player.getLosses());
            preparedStatement.setString(10, player.getPlayer().getUniqueId().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
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

    public Building getBuildingByName(String name) {
        for (Building building : this.buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }

        throw new BuildingException("could not find building " + name);
    }

    public SQL getSql() {
        return this.sql;
    }

    public SchematicManager getBuildingManager() {
        return this.schematicManager;
    }

    public GameState getState() {
        return this.state;
    }
}