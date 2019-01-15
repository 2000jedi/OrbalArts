package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalDamage;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class FireBall extends Magic{
    public static String name = "magic_fireball";

    public FireBall() {
        super();
        range = 1;
        requirement = new int[]{0, 1, 0, 0, 0};
        manaCost = 30;
        cooldown = 4;
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
        taker.attackEntityFrom(OrbalDamage.Fire, (float) ((20 + 0.7 * data.INT) * mult));
        if (Math.random() < 0.1) {
            taker.setFire(5);
        }
    }
}
