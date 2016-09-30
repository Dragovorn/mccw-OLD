package com.dragovorn.mccw.game.mechanic.kit;

import com.dragovorn.mccw.game.mechanic.upgrade.Health;

public class None extends Class {

    public None() {
        super("No Class", 1000);

        addUpgrade(new Health());
    }
}
