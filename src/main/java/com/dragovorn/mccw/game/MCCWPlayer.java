package com.dragovorn.mccw.game;

import com.dragovorn.mccw.game.team.ITeam;
import org.bukkit.entity.Player;

public class MCCWPlayer {

    private ITeam team;

    // List of upgrades or something

    private final Player player;

    private long networth;
    private long gold;

    private int level;
    private int kills;
    private int deaths;

    private double exp;
    private double expNextLevel;
    private double kda;

    public MCCWPlayer(Player player, int level, int kills, int deaths, double exp, double expNextLevel) {
        this(player);
        this.level = level;
        this.exp = exp;
        this.kills = kills;
        this.deaths = deaths;
        this.expNextLevel = expNextLevel;
        this.kda = kills / deaths;
    }

    public MCCWPlayer(Player player) {
        this.player = player;
        this.networth = 0;
        this.gold = 0;
        this.level = 0;
        this.kills = 0;
        this.deaths = 0;
        this.exp = 0;
        this.expNextLevel = 1000;
        this.kda = 0.0;
    }

    public void addGold(long gold) {
        this.gold += gold;
        this.networth += gold;
    }

    public void subtractGold(long gold) {
        this.gold -= gold;
        this.networth -= gold;
    }

    public void buy() { // Upgrade
        this.gold -= 0; // subtract the upgrade cost
        // increment upgrade
        update();
    }

    public void leaveTeam() {
        if (this.team == null) {
            return;
        }

        this.team.leave(this);
    }

    public void joinTeam(ITeam team) {
        this.team = team;

        team.join(this);
    }

    public void addExp(double exp) {
        this.exp += exp;

        updateExpNextLevel();
    }

    public void updateExpNextLevel() {
        // use an array to figure what their next value is
    }

    public Player getPlayer() {
        return this.player;
    }

    public long getNetworth() {
        return this.networth;
    }

    public long getGold() {
        return this.gold;
    }

    public int getLevel() {
        return this.level;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public double getExp() {
        return this.exp;
    }

    public double getExpNextLevel() {
        return this.expNextLevel;
    }

    public double getKda() {
        return this.kda;
    }

    public ITeam getTeam() {
        return this.team;
    }

    public boolean isInTeam() {
        return team != null;
    }

    private void update() {
        // update player inventory + effects
    }
}