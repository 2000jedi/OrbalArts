package com.moscovin.orbal.items.Seipth;

import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public abstract class Seipth extends Item {
    public int strength;
    public SeipthEnum Type;

    public abstract OrbalPropsData effect(OrbalPropsData props);
    public String[] getDesc() {
        return I18n.format("tooltip." + getUnlocalizedName().substring(5)).split("\n");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.addAll(Arrays.asList(getDesc()));
        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}
