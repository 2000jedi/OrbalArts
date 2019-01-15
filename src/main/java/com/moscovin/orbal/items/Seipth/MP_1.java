package com.moscovin.orbal.items.Seipth;

import com.moscovin.orbal.orbment.OrbalPropsData;

public class MP_1 extends Seipth{
    public static String name = "seipth_mp_1";

    public MP_1() {
        super();

        strength = 1;
        Type = SeipthEnum.WATER;

        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public OrbalPropsData effect(OrbalPropsData props) {
        props.ManaMult += 0.1;
        return props;
    }
}
