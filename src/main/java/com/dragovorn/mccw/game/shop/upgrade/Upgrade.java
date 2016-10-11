package com.dragovorn.mccw.game.shop.upgrade;

import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.shop.ShopItem;
import com.dragovorn.mccw.game.shop.kit.Class;
import com.dragovorn.mccw.utils.Calculator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Upgrade implements ShopItem {

    private UpgradeType type;

    private String name;

    private List<Class> classes;

    private int level;
    private int maxLevel;

    Upgrade(String name, int level, int maxLevel, UpgradeType type, Class... classes) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.type = type;
        this.classes = new ArrayList<>(Arrays.asList(classes));
    }

    @Override
    public long getCost() {
        return getCosts()[getLevel()];
    }

    @Override
    public ItemStack getShopItem() {
        ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
        stack.getItemMeta().setDisplayName(ChatColor.GREEN + this.name + ChatColor.GRAY + " " + Calculator.toRoman(this.level));

        return new ItemStack(Material.ENCHANTED_BOOK);
    }

    public void apply(MCCWPlayer player) {
        onApply(player);

        if (player.getUpgrades().contains(this)) {
            player.getUpgrades().remove(this); // maybe require each upgrade to have a uid
        }

        player.getUpgrades().add(this);
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

    public List<Class> getClasses() {
        return this.classes;
    }
}