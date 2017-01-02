package com.dragovorn.mccw.building;

import com.dragovorn.mccw.game.shop.ShopItem;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingReference {

    private String name;

    private Schematic[] schematics;

    private Map<Integer, List<ShopItem>> shop;

    private double[] maxHealth;

    private int maxLevel;

    private boolean isShop;

    public BuildingReference(String name, int maxLevel, boolean shop, Schematic[] schematics, double[] maxHealth) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.isShop = shop;
        this.schematics = schematics;
        this.shop = new HashMap<>();
        this.maxHealth = maxHealth;
    }

    public BuildingReference addItemToShop(int level, ShopItem item) {
        if (!this.isShop) {
            return this;
        }

        this.shop.computeIfAbsent(level, Function -> this.shop.put(level, new ArrayList<>()));

        this.shop.get(level).add(item);

        return this;
    }

    public BuildingReference linkShops(int level1, int level2) {
        if (!this.isShop) {
            return this;
        }

        this.shop.computeIfAbsent(level1, Function -> this.shop.put(level1, new ArrayList<>()));

        this.shop.put(level2, this.shop.get(level1));

        return this;
    }

    public Inventory getShop(int level) {
        // TODO convert map to a shop

        return null;
    }

    public Building build(BuildingManager manager, Location build, int level) {
        return new Building(manager.build(this.schematics[level - 1], build), this, build, level, this.name);
    }

    public String getName() {
        return this.name;
    }

    public Schematic getLevelSchematic(int level) {
        return this.schematics[level];
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public double getMaxHealthAtLevel(int level) {
        return this.maxHealth[level - 1];
    }

    public boolean isShop() {
        return this.isShop;
    }

    public List<ShopItem> getLevelShop(int level) {
        return this.shop.get(level);
    }
}