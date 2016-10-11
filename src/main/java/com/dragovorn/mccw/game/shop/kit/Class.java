package com.dragovorn.mccw.game.shop.kit;

import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.shop.ShopItem;
import com.dragovorn.mccw.game.shop.upgrade.Upgrade;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public abstract class Class implements ShopItem {

    private String name;

    private long cost;

    private List<Upgrade> upgrades;

    Class(String name, long cost) {
        this.name = name;
        this.cost = cost;
        this.upgrades = new ArrayList<>();
    }

    @Override
    public long getCost() {
        return this.cost;
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

    public Upgrade getUpgrade(String name) {
        for (Upgrade upgrade : this.upgrades) {
            if (upgrade.getName().equals(name)) {
                return upgrade;
            }
        }

        return null;
    }
}