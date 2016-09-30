package com.dragovorn.mccw.game.mechanic.kit;

import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.mechanic.upgrade.Upgrade;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public abstract class Class {

    private String name;

    private int cost;

    private List<Upgrade> upgrades;

    public Class(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.upgrades = new ArrayList<>();
    }

    public void onPurchase(MCCWPlayer player) { }

    public void apply(MCCWPlayer player) {
        for (Upgrade upgrade : this.upgrades) {
            upgrade.apply(player);
        }
    }

    public ImmutableList<Upgrade> getUpgrades() {
        return ImmutableList.copyOf(this.upgrades);
    }

    public void addUpgrade(Upgrade upgrade) {
        this.upgrades.add(upgrade);
    }

    public void setUpgrade(int index, Upgrade upgrade) {
        if (!this.upgrades.contains(upgrade)) {
            return;
        }

        this.upgrades.set(index, upgrade);
    }

    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public Upgrade getUpgrade(String name) {
        for (Upgrade upgrade : this.upgrades) {
            if (upgrade.getName().equals(name)) {
                return upgrade;
            }
        }

        return null;
    }
}