package com.dragovorn.mccw.game.mechanic.upgrade;

import com.dragovorn.mccw.game.MCCWPlayer;

public abstract class Upgrade {

    private UpgradeType type;

    private String name;

    private int level;
    private int maxLevel;

    public Upgrade(String name, int level, int maxLevel, UpgradeType type) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.type = type;
    }

    public void apply(MCCWPlayer player) {
        onApply(player);
    }

    public void onHit(MCCWPlayer damager, MCCWPlayer damagee) { }
    public void onDamage(MCCWPlayer damager, MCCWPlayer damagee) { }
    public void onUpdate(MCCWPlayer player) { }
    protected abstract void onApply(MCCWPlayer player);
    protected void onUpgrade(MCCWPlayer player) { }

    public abstract long[] getCosts();

    protected void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void levelup(MCCWPlayer player) {
        if (this.maxLevel <= this.level) {
            return;
        }

        this.level++;
        onUpgrade(player);
    }

    public UpgradeType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }
}