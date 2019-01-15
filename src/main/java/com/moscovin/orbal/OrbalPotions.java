package com.moscovin.orbal;

import com.moscovin.orbal.orbment.OrbalDamage;
import com.moscovin.orbal.potion.Cooldown;
import com.moscovin.orbal.potion.Fire;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class OrbalPotions {
    public static Cooldown cooldown = new Cooldown();
    public static Fire fire = new Fire();

    public static void tick(EntityLivingBase entity) { // detect and effect potions
        PotionEffect effect;
        effect = entity.getActivePotionEffect(fire);
        if (effect != null) {
            entity.attackEntityFrom(OrbalDamage.Fire, (float) (effect.getAmplifier()));
        }
    }
}
