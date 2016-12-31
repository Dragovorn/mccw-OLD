package com.dragovorn.mccw.building;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private final List<Block> blocks;

    private BuildingReference reference;

    private Location location;

    private Villager villager;

    private int level;

    Building(BuildingReference reference, Location location) {
        this.blocks = new ArrayList<>();
        this.reference = reference;
        this.location = location;
        this.villager = null;
        this.level = 1;
    }

    public void levelup() {
        this.level++;

        // TODO build the new level building over the old one.
    }

    public Villager getVillager() {
        return this.villager;
    }

    public void setVillager(Villager villager) {
        this.villager = villager;
    }
}