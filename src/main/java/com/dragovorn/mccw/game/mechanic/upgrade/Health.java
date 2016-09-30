package com.dragovorn.mccw.game.mechanic.upgrade;

import com.dragovorn.mccw.game.MCCWPlayer;

public class Health extends Upgrade {

    private int[] hpValues = new int[] {40, 42, 44, 46, 48, 50, 52, 54, 56, 60};

    public Health() {
        super("Health", 1, 10, UpgradeType.BUFF);
    }

    public void changeScaling(int[] hpValues) {
        this.hpValues = hpValues;
    }

    public void setMaxLevel(int maxLevel) {
        super.setMaxLevel(maxLevel);
    }

    @Override
    public long[] getCosts() {
        return new long[] {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
    }

    @Override
    protected void onApply(MCCWPlayer player) {
        player.getPlayer().setMaxHealth(this.hpValues[0]);
    }

    @Override
    protected void onUpgrade(MCCWPlayer player) {
        player.getPlayer().setMaxHealth(this.hpValues[getLevel() - 1]);
    }
}