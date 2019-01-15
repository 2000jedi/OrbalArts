package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalConfig;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class Heal_2 extends Magic{
    public static String name = "magic_range_heal";

    public Heal_2() {
        super();
        range = 7;
        requirement = new int[]{6, 0, 2, 0, 0};
        manaCost = 200;
        cooldown = 20;
        Type = SeipthEnum.WATER;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    private double amount(EntityPlayer player) {
        return 50;
    }

    @Override
    public String[] getDesc(EntityPlayer player) {
        return new String[]{I18n.format("tooltip.magic_heal" , amount(player))};
    }

    @Override
    public void effect(EntityPlayer self, double mult) {
        List entities = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(self.getPositionVector().subtract(range, range, range), self.getPositionVector().addVector(range, range, range)));
        for (Object e : entities) {
            NBTTagCompound nbt = OrbalConfig.getTag((EntityPlayer) e);
            if (nbt != null) {
                OrbalPropsData data = new OrbalPropsData(nbt);
                data.heal(mult * amount(self));
                OrbalConfig.setTag((EntityPlayer) e, data);
            }
        }
    }

    @Override
    public void effect(EntityPlayer self, EntityLivingBase taker, double mult) {
        if (taker instanceof EntityPlayer) {
            NBTTagCompound nbt = OrbalConfig.getTag((EntityPlayer) taker);
            if (nbt != null) {
                OrbalPropsData data = new OrbalPropsData(nbt);
                data.heal(mult * amount(self));
                OrbalConfig.setTag((EntityPlayer) taker, data);
            }
        }
    }
}
