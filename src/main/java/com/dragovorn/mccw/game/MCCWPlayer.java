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

    public MCCWPlayer(Player player, int level) {
        this(player);
        this.level = level;
    }

    public MCCWPlayer(Player player) {
        this.player = player;
        this.networth = 0;
        this.gold = 0;
        this.level = 0;
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