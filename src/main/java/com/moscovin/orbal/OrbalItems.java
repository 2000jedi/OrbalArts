package com.moscovin.orbal;

import com.moscovin.orbal.items.*;
import com.moscovin.orbal.items.Magics.*;
import com.moscovin.orbal.items.Seipth.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class OrbalItems {
    private CreativeTabs creativeTab;

    public static Item orbalPanel = new OrbalPanel();
    public static Item orbmentRed = new OrbmentRed();
    public static Item orbmentBlue = new OrbmentBlue();
    public static Item orbmentYellow = new OrbmentYellow();
    public static Item orbmentGreen = new OrbmentGreen();
    public static Item orbmentDark = new OrbmentDark();
    public static Item mp1 = new MP_1();
    public static Item heal = new Heal();
    public static Item bless = new Bless();
    public static Item heal2 = new Heal_2();
    public static Item absorb = new Absorb();
    public static Item fireball = new FireBall();
    public static Item firestorm = new FireStorm();
    public static Item hell = new Hell();
    public static Item teleport = new Teleport();
    public static Item waterprison = new WaterPrison();

    public OrbalItems(CreativeTabs creativeTab){
        this.creativeTab = creativeTab;
    }

    public void init() {
        register(orbalPanel);
        register(orbmentRed);
        register(orbmentBlue);
        register(orbmentGreen);
        register(orbmentYellow);
        register(orbmentDark);
        register(mp1);
        register(heal);
        register(bless);
        register(heal2);
        register(absorb);
        register(fireball);
        register(firestorm);
        register(hell);
        register(teleport);
        register(waterprison);
    }

    private void register(Item item) {
        ForgeRegistries.ITEMS.register(item);
        item.setCreativeTab(this.creativeTab);
    }

    public void registerRenderer() {
        registerModel(orbalPanel);
        registerModel(orbmentBlue);
        registerModel(orbmentGreen);
        registerModel(orbmentRed);
        registerModel(orbmentYellow);
        registerModel(orbmentDark);
        registerModel(mp1);
        registerModel(heal);
        registerModel(bless);
        registerModel(heal2);
        registerModel(absorb);
        registerModel(fireball);
        registerModel(firestorm);
        registerModel(hell);
        registerModel(teleport);
        registerModel(waterprison);
    }

    private void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
