package com.dragovorn.mccw.building;

import com.dragovorn.mccw.MCCW;
import com.dragovorn.mccw.exceptions.BuildingException;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BuildingManager implements IBuildingManager {

    private int blocksPerTime = 1;

    private long delay = 0L;

    public void setBlocksPerTime(int blocksPerTime) {
        this.blocksPerTime = blocksPerTime;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    protected void regularBuild(Schematic schematic, final Location location) {
        List<Block> allBlocks = new ArrayList<>();

        for (int x = 0; x < schematic.getWidth(); x++) {
            for (int y = 0; y < schematic.getHeight(); y++) {
                for (int z = 0; z < schematic.getLength(); z++) {
                    Location temp = location.clone().add(x, y, z);
                    Block block = temp.getBlock();

                    int index = y * schematic.getWidth() * schematic.getLength() + z * schematic.getWidth() + x;

                    if (block.getType() != Material.getMaterial(schematic.getBlocks()[index])) {
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
                        for (int x = 0; x < blocksPerTime; x++) {
                            this.blocksBuilt += blocksPerTime;

                            if (this.index < size) {
                                Block block = orderedBlocks.get(this.index);

                                if (!block.getLocation().equals(location)) {
                                    buildBlock(block);
                                }

                                this.index++;
                            } else {
                                this.cancel();
                            }
                        }
                    }
                }.runTaskTimer(MCCW.getInstance(), 1L, delay).wait();
            } catch (InterruptedException exception) {
                throw new BuildingException(exception);
            }
        }
    }

    protected void buildBlock(Block block) {
        block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());

        block.setType(block.getType(), false);
    }
}