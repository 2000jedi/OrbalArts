package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalConfig;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Heal extends Magic{
    public static String name = "magic_heal";

    public Heal() {
        super();
        range = 1;
        requirement = new int[]{1, 0, 0, 0, 0};
        manaCost = 20;
        cooldown = 15;
        Type = SeipthEnum.WATER;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    private double amount(EntityPlayer player) {
        NBTTagCompound nbt = OrbalConfig.getTag(player);
        if (nbt != null) {
            OrbalPropsData data = new OrbalPropsData(nbt);
            return 15 + 0.75 * data.INT;
        }
        return 0;
    }

    @Override
    public String[] getDesc(EntityPlayer player) {
        return new String[]{I18n.format("tooltip.magic_heal" , amount(player))};
    }
    @Override
    public void effect(EntityPlayer self, double mult) {
        NBTTagCompound nbt = OrbalConfig.getTag(self);
        if (nbt != null) {
            OrbalPropsData data = new OrbalPropsData(nbt);
            data.heal(mult * amount(self));
            OrbalConfig.setTag(self, data);
        }
    }

    @Override
    public void effect(EntityPlayer player, EntityLivingBase taker, double mult) {
        if (taker instanceof EntityPlayer) {
            NBTTagCompound nbt = OrbalConfig.getTag((EntityPlayer) taker);
            if (nbt != null) {
                OrbalPropsData data = new OrbalPropsData(nbt);
                data.heal(mult * amount(player));
                OrbalConfig.setTag((EntityPlayer) taker, data);
            }
        }
    }
}
