package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.OrbalPotions;
import com.moscovin.orbal.entity.MagicBall;
import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalConfig;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public abstract class Magic extends Item{
    public float range = 0;
    public int[] requirement = new int[]{0, 0, 0, 0, 0};
    double manaCost = 0;
    int cooldown = 0;

    public SeipthEnum Type;
    public boolean teamEffect = true;

    private boolean cost(EntityPlayer self){
        if (self.getActivePotionEffect(OrbalPotions.cooldown) != null)
            return false;
        OrbalPropsData data = new OrbalPropsData(self);
        if (data.takeMana(manaCost)) {
            OrbalConfig.setTag(self, data);
            return true;
        } else {
            return false;
        }
    }

    public abstract void effect(EntityPlayer player, double mult);
    public abstract void effect(EntityPlayer self, EntityLivingBase taker, double mult);
    public void effectHit(World world, BlockPos pos){}

    public void useOnSelf(EntityPlayer player) {
        if (teamEffect) {
            OrbalPropsData data = new OrbalPropsData(player);
            if (cost(player)) {
                effect(player, data.magicMult(Type));
            }
            player.addPotionEffect(new PotionEffect(OrbalPotions.cooldown, cooldown * 20, 0));
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if (cost(playerIn)) {
            OrbalPropsData data = new OrbalPropsData(playerIn);
            MagicBall ball = new MagicBall(worldIn, playerIn);
            ball.magic = (Magic) playerIn.inventory.getCurrentItem().getItem();
            ball.mult = data.magicMult(Type);
            ball.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 0.0F);
            worldIn.spawnEntity(ball);
            playerIn.addStat(StatList.getObjectUseStats(this));

        }
        playerIn.addPotionEffect(new PotionEffect(OrbalPotions.cooldown, cooldown * 20, 0));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public String[] getDesc(EntityPlayer player) {
        return I18n.format("tooltip." + getUnlocalizedName().substring(5)).split("\n");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.addAll(Arrays.asList(getDesc(playerIn)));
        if (this.range != 1) tooltip.add(I18n.format("tooltip.range") + this.range);
        tooltip.add(I18n.format("tooltip.mana_cost") + this.manaCost);
        tooltip.add(I18n.format("tooltip.cool_down") + this.cooldown);
        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}
