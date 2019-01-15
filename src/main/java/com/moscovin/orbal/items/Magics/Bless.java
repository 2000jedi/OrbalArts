package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;

public class Bless extends Magic{
    public static String name = "magic_bless";

    public Bless() {
        super();
        range = 1;
        requirement = new int[]{3, 0, 0, 1, 0};
        manaCost = 10;
        cooldown = 5;
        Type = SeipthEnum.WATER;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void effect(EntityPlayer self, double mult) {
        self.curePotionEffects(new ItemStack(new ItemBucketMilk(), 1));
        self.clearActivePotions();
        self.addStat(StatList.getObjectUseStats(this));
    }

    @Override
    public void effect(EntityPlayer self, EntityLivingBase taker, double mult) {
        taker.curePotionEffects(new ItemStack(new ItemBucketMilk(), 1));
        taker.clearActivePotions();
        if (taker instanceof EntityPlayer) {
            ((EntityPlayer) taker).addStat(StatList.getObjectUseStats(this));
        }
    }
}
