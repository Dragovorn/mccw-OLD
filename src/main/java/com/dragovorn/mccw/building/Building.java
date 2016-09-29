package com.dragovorn.mccw.building;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.exceptions.BuildingException;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Building {

    private String name;

    private Schematic[] schematics;

    private List<Block> blocks;

    private Location location;

//    private Map<Upgrade, Integer> shop

    private boolean isShop;

    private int level;

    public Building(String name, Location location, boolean shop, int level, Schematic... schematics) {
        this.name = name;
        this.location = location;
        this.isShop = shop;
        this.level = level;
        this.schematics = schematics;
        this.blocks = new ArrayList<>();
    }

    public void build(BuildingManager manager) {
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
                                byte data = schematic.getData()[blocks.get(block)];

                                if (!block.getLocation().equals(location)) {
                                    if (data == DyeColor.WHITE.getData()) {
                                        if (manager.getTeam().getColour() == ChatColor.RED) {
                                            data = DyeColor.RED.getData();
                                        } else if (manager.getTeam().getColour() == ChatColor.BLUE) {
                                            data = DyeColor.BLUE.getData();
                                        }
                                    }

                                    buildBlock(block, data);
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
    }

    private void buildBlock(Block block, byte data) {
        block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());

        block.setType(block.getType(), false);
        block.setData(data, false);
    }

    public void levelup() {
        level++;
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

    public boolean isShop() {
        return this.isShop;
    }

    public int getLevel() {
        return this.level;
    }

    private void updateBuild() {
        // TODO
    }
}