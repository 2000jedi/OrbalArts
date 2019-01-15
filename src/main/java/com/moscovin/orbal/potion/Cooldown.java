package com.moscovin.orbal.potion;

import net.minecraft.potion.Potion;

public class Cooldown extends Potion {
    public Cooldown() {
        super(true, 0xEEEEEE);
        this.setPotionName("potion.cooldown");
        this.setIconIndex(7, 1);
    }
}
