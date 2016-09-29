package com.dragovorn.mccw.building;

public abstract class BuildingManager implements IBuildingManager {

    private int blocksPerTime = 1;

    private long delay = 0L;

    public void setBlocksPerTime(int blocksPerTime) {
        this.blocksPerTime = blocksPerTime;
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