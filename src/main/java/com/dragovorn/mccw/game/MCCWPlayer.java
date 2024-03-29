package com.dragovorn.mccw.game;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.exceptions.UpgradeException;
import com.dragovorn.mccw.game.shop.item.Item;
import com.dragovorn.mccw.game.shop.kit.Class;
import com.dragovorn.mccw.game.shop.kit.None;
import com.dragovorn.mccw.game.shop.upgrade.Upgrade;
import com.dragovorn.mccw.game.team.ITeam;
import com.dragovorn.mccw.game.util.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class MCCWPlayer {

    private ITeam team;

    private Class clazz;

    private List<Upgrade> upgrades;
    private List<Item> inventory;

    private final Player player;

    private final Team tag;

    private long networth;
    private long gold;

    private int level;
    private int games;
    private int wins;
    private int losses;
    private int kills;
    private int deaths;

    private double exp;
    private double expNextLevel;

    public MCCWPlayer(Player player, int level, int kills, int deaths, double exp) {
        this(player);
        this.level = level;
        this.exp = exp;
        this.kills = kills;
        this.deaths = deaths;
        this.expNextLevel = MCCW.getInstance().exp[level - 1];
    }

    public MCCWPlayer(Player player) {
        this.player = player;
        this.networth = 0;
        this.gold = 0;

        // TODO register player/get info

        this.clazz = new None();
        this.upgrades = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.tag = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(player.getDisplayName());
        this.tag.addEntry(player.getName());
        this.team = null;
    }

    public void addItem(Item item) {
        this.inventory.add(item);
        item.apply(this);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
        this.player.getInventory().remove(item.getItem());
    }

    public void addGold(long gold) {
        this.gold += gold;
        this.networth += gold;
    }

    public void subtractGold(long gold) {
        this.gold -= gold;
        this.networth -= gold;
    }

    public void setPrefix(String prefix) {
        this.tag.setPrefix(MessageType.colourize(prefix));
    }

    public void setSuffix(String suffix) {
        this.tag.setSuffix(MessageType.colourize(suffix));
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

    public void sendMessage(MessageType type, String message, Object... objects) {
        player.getPlayer().sendMessage(String.format(MessageType.colourize(type.getPrefix() + message), objects));
    }

    public void addExp(double exp) {
        this.exp += exp;

        updateExpNextLevel();
    }

    public void setClass(Class clazz) {
        this.clazz = clazz;
        clazz.apply(this);
    }

    public void reset() {
        this.player.getInventory().clear();
        this.player.getInventory().setHelmet(null);
        this.player.getInventory().setChestplate(null);
        this.player.getInventory().setLeggings(null);
        this.player.getInventory().setBoots(null);
        this.player.getInventory().setItemInOffHand(null);

        for (PotionEffect effect : this.player.getActivePotionEffects()) {
            this.player.removePotionEffect(effect.getType());
        }
    }

    public void incrementKills() {
        this.kills++;
    }

    public void updateExpNextLevel() {
        this.expNextLevel = MCCW.getInstance().exp[this.level];
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

    public int getGames() {
        return this.games;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses;
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
        return this.kills / this.deaths;
    }

    public ITeam getTeam() {
        return this.team;
    }

    public List<Upgrade> getUpgrades() {
        return this.upgrades;
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    public ItemStack getMainHand() {
        return this.player.getInventory().getItemInMainHand();
    }

    public boolean isInTeam() {
        MCCW.getInstance().getLogger().info(this.team.toString());

        return this.team != null;
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