package com.dragovorn.mccw.building;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.exceptions.BuildingException;
import com.dragovorn.mccw.game.MCCWPlayer;
import com.dragovorn.mccw.game.shop.ShopItem;
import com.dragovorn.mccw.utils.Calculator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Building {

    private String name;

    private Schematic[] schematics;

    private List<Block> blocks;

    private Location location;

    private Map<ShopItem, Long> shop;

    private Entity villager;

    private boolean isShop;

    private int level;

    public Building(String name, boolean shop, int level, Schematic... schematics) {
        this.name = name;
        this.isShop = shop;
        this.level = level;
        this.schematics = schematics;
        this.blocks = new ArrayList<>();
        this.shop = new HashMap<>();
    }

    public Building addItem(ShopItem item) {
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

    public Building build(BuildingManager manager, Location location) {
        this.location = location;

        Schematic schematic = this.schematics[this.level];

        final HashMap<Block, Integer> blocks = new HashMap<>();
        List<Block> allBlocks = new ArrayList<>();

        for (int x = 0; x < schematic.getWidth(); x++) {
            for (int y = 0; y < schematic.getHeight(); y++) {
                for (int z = 0; z < schematic.getLength(); z++) {
                    Location temp = location.clone().add(x, y, z);
                    Block block = temp.getBlock();

                    int index = y * schematic.getWidth() * schematic.getLength() + z * schematic.getWidth() + x;

                    if (block.getType() != Material.getMaterial(schematic.getBlocks()[index])) {
                        blocks.put(block, index);
                        allBlocks.add(block);
                    }
                }
            }
        }

        final List<Block> orderedBlocks = new ArrayList<>();

        orderedBlocks.addAll(allBlocks);

        Collections.sort(orderedBlocks, (Block block1, Block block2) -> Double.compare(block1.getY(), block2.getY()));

        final int size = orderedBlocks.size();

        if (size > 0) {
            try {
                new BukkitRunnable() {
                    int index = 0;

                    double blocksBuilt = 0;

                    @Override
                    public void run() {
                        for (int x = 0; x < manager.getBlocksPerTime(); x++) {
                            this.blocksBuilt += manager.getBlocksPerTime();

                            if (this.index < size) {
                                Block block = orderedBlocks.get(this.index);
                                int otherIndex = blocks.get(block);
                                int type = schematic.getBlocks()[otherIndex];
                                byte data = schematic.getData()[otherIndex];

                                if (!block.getLocation().equals(location)) {
                                    if (data == DyeColor.WHITE.getData() && (Material.getMaterial(type) == Material.WOOL || Material.getMaterial(type) == Material.STAINED_GLASS || Material.getMaterial(type) == Material.STAINED_GLASS_PANE || Material.getMaterial(type) == Material.CARPET)) {
                                        if (manager.getTeam().getColour() == ChatColor.RED) {
                                            data = DyeColor.RED.getData();
                                        } else if (manager.getTeam().getColour() == ChatColor.BLUE) {
                                            data = DyeColor.BLUE.getData();
                                        }
                                    }

                                    if (Material.getMaterial(type) == Material.BEDROCK) {
                                        villager = location.getWorld().spawnEntity(block.getLocation(), EntityType.VILLAGER);
                                    } else {
                                        buildBlock(block, type, data);
                                    }
                                }

                                this.index++;
                            } else {
                                this.cancel();
                            }
                        }
                    }
                }.runTaskTimer(MCCW.getInstance(), 1L, manager.getDelay()).wait();
            } catch (InterruptedException exception) {
                throw new BuildingException(exception);
            }
        }

        return this;
    }

    private void buildBlock(Block block, int type, byte data) {
        Material blockType = Material.getMaterial(type);

        block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, blockType, data);

        block.setType(blockType, false);
        block.setData(data, false);
        this.blocks.add(block);
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

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public Location getLocation() {
        return this.location;
    }

    public Entity getShopKeeper() {
        return this.villager;
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