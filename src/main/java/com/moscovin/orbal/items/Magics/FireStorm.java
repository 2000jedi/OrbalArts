package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.OrbalPotions;
import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class FireStorm extends Magic{
    public static String name = "magic_firestorm";

    public FireStorm() {
        super();
        range = 2;
        requirement = new int[]{0, 5, 3, 0, 0};
        manaCost = 150;
        cooldown = 10;
        Type = SeipthEnum.FIRE;
        teamEffect = false;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void effect(EntityPlayer self, double mult) {}

    @Override
    public void effect(EntityPlayer self, EntityLivingBase taker, double mult) {
        OrbalPropsData data = new OrbalPropsData(self);
        taker.addPotionEffect(new PotionEffect(OrbalPotions.fire, 2 * 20, (int) ((50 + 0.6 * data.INT) * mult / 20.0f)));
        taker.setFire(5);
    }
}
