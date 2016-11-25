package com.dragovorn.mccw.game.shop.item;

import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.shop.ShopItem;
import com.dragovorn.mccw.game.shop.kit.Class;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Item implements ShopItem {

    private String name;

    private int level;
    private int maxLevel;

    private List<Class> classes;

    Item(String name, int level, int maxLevel, Class... classes) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.classes = new ArrayList<>(Arrays.asList(classes));
    }

    public void onHit(MCCWPlayer hit) { }
    public void onRightClick(MCCWPlayer player) { }
    public void onLeftClick(MCCWPlayer player) { }
    public boolean onDrop(MCCWPlayer player) { return true; }

    public abstract ItemStack getItem();

    abstract long[] getCosts();

    @Override
    public void apply(MCCWPlayer player) {
        player.getPlayer().getInventory().addItem(getItem());
    }

    public List<Class> getClasses() {
        return this.classes;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    public long getCost() {
        return getCosts()[this.level];
    }
}