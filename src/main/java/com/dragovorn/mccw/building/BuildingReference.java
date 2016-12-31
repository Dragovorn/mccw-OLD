package com.dragovorn.mccw.building;

import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.shop.ShopItem;
import com.dragovorn.mccw.utils.Calculator;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class BuildingReference {

    private String name;

    private Schematic[] schematics;

    private Map<ShopItem, Long> shop;

    private boolean isShop;

    private int level;

    public BuildingReference(String name, boolean shop, int level, Schematic... schematics) {
        this.name = name;
        this.isShop = shop;
        this.level = level;
        this.schematics = schematics;
        this.shop = new HashMap<>();
    }

    public BuildingReference addItem(ShopItem item) {
        this.shop.put(item, item.getCost());

        return this;
    }

    public void openShop(MCCWPlayer player) {
        if (!this.isShop) {
            return;
        }

        int slots = Calculator.formToLine(this.shop.size() * 9);

        Inventory inventory = Bukkit.createInventory(null, slots, this.name);

        int x = 0;

        for (Map.Entry<ShopItem, Long> entry : this.shop.entrySet()) {
            inventory.setItem(x, entry.getKey().getShopItem());
        }

        player.getPlayer().openInventory(inventory);
    }

    // TODO make this return the a Building object with all of the big boy stuff
    public Building build(BuildingManager manager, Location build) {
        Building building = new Building(this, build);

        return building;
    }

    private void buildBlock(Block block, int type, byte data) {
        Material blockType = Material.getMaterial(type);

        block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, blockType, data);

        block.setType(blockType, false);
        block.setData(data, false);
    }

    public void levelup() {
        this.level++;
        updateBuild();
    }

    public String getName() {
        return this.name;
    }

    public Schematic[] getSchematics() {
        return this.schematics;
    }

    public boolean isShop() {
        return this.isShop;
    }

    public int getLevel() {
        return this.level;
    }

    private void updateBuild() {
        // TODO deletes the old building and builds the next level one
    }
}