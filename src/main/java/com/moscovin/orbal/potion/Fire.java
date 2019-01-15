package com.moscovin.orbal.potion;

import net.minecraft.potion.Potion;

public class Fire extends Potion {
    public Fire() {
        super(true, 0xFF0000);
        this.setPotionName("potion.fire");
        this.setIconIndex(7, 1);
    }
}
