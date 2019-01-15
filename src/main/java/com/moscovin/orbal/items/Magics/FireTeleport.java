package com.moscovin.orbal.items.Magics;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FireTeleport extends Magic{
    public static String name = "magic_fireteleport";

    public FireTeleport() {
        super();
        range = 1;
        requirement = new int[]{0, 3, 5, 0, 0};
        manaCost = 150;
        cooldown = 12;
        Type = SeipthEnum.FIRE;
        teamEffect = false;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void useOnSelf(EntityPlayer player){
    }

    @Override
    public void effect(EntityPlayer self, double mult) {
        RayTraceResult result = self.rayTrace(12, 1);
        BlockPos pos = result.getBlockPos();
        self.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        super.useOnSelf(playerIn);
        return new ActionResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

        @Override
    public void effect(EntityPlayer self, EntityLivingBase taker, double mult) {
    }
}
