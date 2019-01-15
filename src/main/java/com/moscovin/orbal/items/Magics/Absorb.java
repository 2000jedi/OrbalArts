package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalConfig;
import com.moscovin.orbal.orbment.OrbalDamage;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class Absorb extends Magic{
    public static String name = "magic_absorb";

    public Absorb() {
        super();
        range = 1;
        requirement = new int[]{10, 0, 5, 1, 0};
        manaCost = 250;
        cooldown = 20;
        Type = SeipthEnum.WATER;
        teamEffect = false;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void effect(EntityPlayer self, double mult) {}

    @Override
    public void effect(EntityPlayer self, EntityLivingBase taker, double mult) {
        OrbalPropsData data = new OrbalPropsData(self);
        taker.attackEntityFrom(OrbalDamage.Water, (float) ((20 + 0.5 * data.INT) * mult));

        List entities = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(self.getPositionVector().subtract(2, 2, 2), self.getPositionVector().addVector(2, 2, 2)));
        for (Object e : entities) {
            NBTTagCompound nbt = OrbalConfig.getTag((EntityPlayer) e);
            if (nbt != null) {
                data = new OrbalPropsData(nbt);
                data.heal(mult * (20 + 0.5 * data.INT) * 0.4);
                OrbalConfig.setTag((EntityPlayer) e, data);
            }
        }
    }
}
