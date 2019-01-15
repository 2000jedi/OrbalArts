package com.moscovin.orbal.entity;

import com.moscovin.orbal.OrbalItems;
import com.moscovin.orbal.items.Magics.Magic;
import com.moscovin.orbal.orbment.OrbalConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class MagicBall extends EntityThrowable { // TODO: max distance
    public Magic magic;
    public double mult;

    @Override
    public float getGravityVelocity() {
        return 0;
    }

    public MagicBall(World worldIn) {
        super(worldIn);

    }

    public MagicBall(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public MagicBall(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (magic != null)
            magic.effectHit(this.getEntityWorld(), new BlockPos(posX, posY, posZ));
        if (!this.world.isRemote) {
            List entities;
            if (magic != null) {
                entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(result.hitVec.subtract(magic.range, magic.range, magic.range), result.hitVec.addVector(magic.range, magic.range, magic.range)));
                for (Object e : entities) {
                    if (magic.teamEffect) {
                        if (e instanceof EntityPlayer) {
                            if (OrbalConfig.sameTeam((EntityPlayer) getThrower(), (EntityPlayer) e))
                                magic.effect((EntityPlayer) getThrower(), (EntityLivingBase) e, mult);
                        }
                    } else {
                        if (! (e instanceof EntityPlayer)) {
                            magic.effect((EntityPlayer) getThrower(), (EntityLivingBase) e, mult);
                        } else {
                            if (! OrbalConfig.sameTeam((EntityPlayer) getThrower(), (EntityPlayer) e))
                                magic.effect((EntityPlayer) getThrower(), (EntityLivingBase) e, mult);
                        }
                    }
                }
            }
        }
        this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0, 0, 0);
        this.setDead();
    }

    public static class Render extends RenderSnowball<MagicBall> {
        public Render(RenderManager renderManagerIn) {
            super(renderManagerIn, OrbalItems.orbalPanel, Minecraft.getMinecraft().getRenderItem());
        }
    }
}
