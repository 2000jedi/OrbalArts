package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.OrbalCore;
import com.moscovin.orbal.Schedules;
import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalDamage;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Hell extends Magic{
    public static String name = "magic_hell";

    public Hell() {
        super();
        range = 5;
        requirement = new int[]{0, 12, 5, 2, 0};
        manaCost = 500;
        cooldown = 60;
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
        taker.attackEntityFrom(OrbalDamage.Fire, (float) ((80 + data.INT) * mult));
    }

    @Override
    public void effectHit(final World world, final BlockPos pos) {
        world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 0);

        OrbalCore.proxy.schedules.addSchedule(new Schedules.Schedule(3) {
            @Override
            public void run() {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        });
    }
}
