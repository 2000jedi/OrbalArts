package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.OrbalCore;
import com.moscovin.orbal.Schedules;
import com.moscovin.orbal.items.Seipth.SeipthEnum;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;

import static net.minecraft.init.MobEffects.SLOWNESS;

public class WaterPrison extends Magic{
    public static String name = "magic_water_prison";

    public WaterPrison() {
        super();
        range = 2;
        requirement = new int[]{2, 0, 0, 2, 0};
        manaCost = 100;
        cooldown = 10;
        Type = SeipthEnum.EARTH;
        teamEffect = false;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void effect(EntityPlayer self, double mult) {}

    @Override
    public void effect(EntityPlayer self, final EntityLivingBase taker, double mult) {
        taker.getEntityWorld().setBlockState(taker.getPosition(), Blocks.WATER.getDefaultState(), 0);
        taker.addPotionEffect(new PotionEffect(SLOWNESS, 3 * 20, 10));
        OrbalCore.proxy.schedules.addSchedule(new Schedules.Schedule(3) {
            @Override
            public void run() {
                taker.getEntityWorld().setBlockState(taker.getPosition(), Blocks.AIR.getDefaultState());
            }
        });
    }
}
