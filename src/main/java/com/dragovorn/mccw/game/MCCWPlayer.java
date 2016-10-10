package com.dragovorn.mccw.game;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.exceptions.UpgradeException;
import com.dragovorn.mccw.game.shop.kit.Class;
import com.dragovorn.mccw.game.shop.kit.None;
import com.dragovorn.mccw.game.shop.upgrade.Upgrade;
import com.dragovorn.mccw.game.team.ITeam;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MCCWPlayer {

    private ITeam team;

    private Class clazz;

    private List<Upgrade> upgrades;

    private final Player player;

    private long networth;
    private long gold;

    private int level;
    private int kills;
    private int deaths;

    private double exp;
    private double expNextLevel;
    private double kda;

    public MCCWPlayer(Player player, int level, int kills, int deaths, double exp) {
        this(player);
        this.level = level;
        this.exp = exp;
        this.kills = kills;
        this.deaths = deaths;
        this.expNextLevel = MCCW.getInstance().exp[level - 1];
        this.kda = kills / deaths;
    }

    public MCCWPlayer(Player player) { // Here is where we resolve the data from the database
        this.player = player;
        this.networth = 0;
        this.gold = 0;
        this.level = 0;
        this.kills = 0;
        this.deaths = 0;
        this.exp = 0;
        this.expNextLevel = 1000;
        this.kda = 0.0;
        this.clazz = new None();
        this.upgrades = new ArrayList<>();
    }

    public void addGold(long gold) {
        this.gold += gold;
        this.networth += gold;
    }

    public void subtractGold(long gold) {
        this.gold -= gold;
        this.networth -= gold;
    }

    public void buy(String name) {
        Upgrade ug = null;

        for (Upgrade upgrade : this.upgrades) {
            if (upgrade.getName().equals(name)) {
                ug = upgrade;
            }
        }

        if (ug == null) {
            ug = this.clazz.getUpgrade(name);
        }

        if (ug == null) {
            throw new UpgradeException(this.player.getName() + " doesn't have upgrade " + name);
        }

        int index = this.clazz.getUpgrades().indexOf(ug);

        this.gold -= ug.getCosts()[ug.getLevel() - 1];
        ug.levelup(this);
        this.clazz.setUpgrade(index, ug);
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

    public List<Upgrade> getUpgrades() {
        return this.upgrades;
    }

    public boolean isInTeam() {
        return team != null;
    }

    private void update() {
        for (Upgrade upgrade : this.upgrades) {
            upgrade.onUpdate(this);
        }

        for (Upgrade upgrade : this.clazz.getUpgrades()) {
            upgrade.onUpdate(this);
        }
    }
}