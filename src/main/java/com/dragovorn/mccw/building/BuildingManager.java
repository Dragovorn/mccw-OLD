package com.dragovorn.mccw.building;

import org.bukkit.Location;

public abstract class BuildingManager implements IBuildingManager {

    private int blocksPerTime = 1;

    private long delay = 0L;

    public void setBlocksPerTime(int blocksPerTime) {
        this.blocksPerTime = blocksPerTime;
    }

    @Override
    public final void build(Schematic schematic, final Location location) {
//        final HashMap<Block, Integer> blocks = new HashMap<>();
//        List<Block> allBlocks = new ArrayList<>();
//
//        for (int x = 0; x < schematic.getWidth(); x++) {
//            for (int y = 0; y < schematic.getHeight(); y++) {
//                for (int z = 0; z < schematic.getLength(); z++) {
//                    Location temp = build.clone().add(x, y, z);
//                    Block block = temp.getBlock();
//
//                    int index = y * schematic.getWidth() * schematic.getLength() + z * schematic.getWidth() + x;
//
//                    if (block.getType() != Material.getMaterial(schematic.getBlocks()[index])) {
//                        blocks.put(block, index);
//                        allBlocks.add(block);
//                    }
//                }
//            }
//        }
//
//        final List<Block> orderedBlocks = new ArrayList<>();
//
//        orderedBlocks.addAll(allBlocks);
//
//        orderedBlocks.sort(Comparator.comparingDouble(Block::getY));
//
//        final int size = orderedBlocks.size();
//
//        if (size > 0) {
//            try {
//                new BukkitRunnable() {
//                    int index = 0;
//
//                    double blocksBuilt = 0;
//
//                    @Override
//                    public void run() {
//                        for (int x = 0; x < manager.getBlocksPerTime(); x++) {
//                            this.blocksBuilt += manager.getBlocksPerTime();
//
//                            if (this.index < size) {
//                                Block block = orderedBlocks.get(this.index);
//                                int otherIndex = blocks.get(block);
//                                int type = schematic.getBlocks()[otherIndex];
//                                byte data = schematic.getData()[otherIndex];
//
//                                if (!block.getLocation().equals(build)) {
//                                    if (data == DyeColor.WHITE.getDyeData() && (Material.getMaterial(type) == Material.WOOL || Material.getMaterial(type) == Material.STAINED_GLASS || Material.getMaterial(type) == Material.STAINED_GLASS_PANE || Material.getMaterial(type) == Material.CARPET)) {
//                                        if (manager.getTeam().getColour() == ChatColor.RED) {
//                                            data = DyeColor.RED.getDyeData();
//                                        } else if (manager.getTeam().getColour() == ChatColor.BLUE) {
//                                            data = DyeColor.BLUE.getDyeData();
//                                        }
//                                    }
//
//                                    if (Material.getMaterial(type) == Material.BEDROCK) {
//                                        building.setVillager((Villager) build.getWorld().spawnEntity(block.getLocation(), EntityType.VILLAGER));
//                                    } else {
//                                        buildBlock(block, type, data);
//                                    }
//                                }
//
//                                this.index++;
//                            } else {
//                                this.cancel();
//                            }
//                        }
//                    }
//                }.runTaskTimer(MCCW.getInstance(), 1L, manager.getDelay()).wait();
//            } catch (InterruptedException exception) {
//                throw new BuildingException(exception);
//            }
//        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getBlocksPerTime() {
        return this.blocksPerTime;
    }

    public long getDelay() {
        return this.delay;
    }
}