package com.dragovorn.mccw.building;

import com.dragovorn.mccw.MCCW;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class BuildingManager implements IBuildingManager {

    private int blocksPerTime = 1;

    private long delay = 1L;

    public final void setBlocksPerTime(int blocksPerTime) {
        this.blocksPerTime = blocksPerTime;
    }

    @Override
    public final void levelBuilding(Building building) {
        if (building.getReference().getMaxLevel() == building.getLevel()) {
            return;
        }

        build(building.getReference(), building.getLocation(), building.getLevel() + 1);
    }

    @Override
    public final List<Block> build(Schematic schematic, final Location location) {
        final HashMap<Block, Integer> blocks = new HashMap<>();
        List<Block> allBlocks = new ArrayList<>();
        final List<Block> finalBlocks = new ArrayList<>();

        for (int x = 0; x < schematic.getWidth(); x++) {
            for (int y = 0; y < schematic.getHeight(); y++) {
                for (int z = 0; z < schematic.getLength(); ++z) {
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

        orderedBlocks.sort(Comparator.comparingDouble(Block::getY));

        final int size = orderedBlocks.size();

        if (size > 0) {
            new BukkitRunnable() {
                int index = 0;

                double blocksBuilt = 0;

                @Override
                public void run() {
                    for (int x = 0; x < getBlocksPerTime(); x++) {
                        this.blocksBuilt += getBlocksPerTime();

                        if (this.index < size) {
                            Block block = orderedBlocks.get(this.index);
                            int otherIndex = blocks.get(block);
                            int type = schematic.getBlocks()[otherIndex];
                            byte data = schematic.getData()[otherIndex];

                            if (!block.getLocation().equals(location)) {
                                buildBlock(finalBlocks, block, Material.getMaterial(type), data);
                            }

                            this.index++;
                        } else {
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(MCCW.getInstance(), 10L, getDelay());
        }

        return finalBlocks;
    }

    private void buildBlock(List<Block> blocks, Block block, final Material type, byte data) {
        final Location loc = block.getLocation();

        loc.getWorld().playEffect(loc, Effect.STEP_SOUND, (type == Material.AIR ? block.getType().getId() : type.getId()));
        block.setTypeIdAndData(type.getId(), data, false);

        blocks.add(block);
    }

    public final void setDelay(int delay) {
        this.delay = delay;
    }

    public final int getBlocksPerTime() {
        return this.blocksPerTime;
    }

    public final long getDelay() {
        return this.delay;
    }
}