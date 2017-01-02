package com.dragovorn.mccw.building;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Building {

    private final List<Block> blocks;

    private BuildingReference reference;

    private Location location;

    private Villager villager;

    private int level;

    private double health;
    private double maxHealth;

    Building(List<Block> blocks, BuildingReference reference, Location location, int level, String name) {
        this.blocks = blocks;
        this.reference = reference;
        this.location = location;
        this.level = level;
        this.health = reference.getMaxHealthAtLevel(this.level);
        this.maxHealth = reference.getMaxHealthAtLevel(this.level);

        Set<Block> set = this.blocks.stream().filter(block -> block.getType() == Material.BEDROCK).collect(Collectors.toSet());

        set.forEach(block -> {
            this.blocks.remove(block);

            block.setType(Material.AIR);

            this.villager = (Villager) location.getWorld().spawnEntity(block.getLocation(), EntityType.VILLAGER);
            this.villager.setProfession(Villager.Profession.BLACKSMITH);
            this.villager.setCanPickupItems(false);
            this.villager.setCollidable(false);
            this.villager.setAI(false);
            this.villager.setCustomNameVisible(true);
            this.villager.setCustomName(name);
            this.villager.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.health);
        });
    }

    public Villager getVillager() {
        return this.villager;
    }

    public BuildingReference getReference() {
        return this.reference;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getLevel() {
        return this.level;
    }

    public double getHealth() {
        return this.health;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public ImmutableList<Block> getBlocks() {
        return new ImmutableList.Builder<Block>().addAll(this.blocks).build();
    }

    public void setVillager(Villager villager) {
        this.villager = villager;
    }
}