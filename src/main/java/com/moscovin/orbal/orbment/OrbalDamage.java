package com.moscovin.orbal.orbment;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import net.minecraft.util.DamageSource;

public class OrbalDamage {
    public static DamageSource Fire = new OrbalDamageSource("Fire Orb", SeipthEnum.FIRE);
    public static DamageSource Water = new OrbalDamageSource("Water Orb", SeipthEnum.WATER);
    public static DamageSource Wind = new OrbalDamageSource("Wind Orb", SeipthEnum.WIND);
    public static DamageSource Earth = new OrbalDamageSource("Earth Orb", SeipthEnum.EARTH);
    public static DamageSource Dark = new OrbalDamageSource("Dark Orb", SeipthEnum.DARK);

    public static class OrbalDamageSource extends DamageSource {
        public SeipthEnum type;

        public OrbalDamageSource(String damageTypeIn, SeipthEnum type) {
            super(damageTypeIn);
            this.type = type;
        }
    }
}
