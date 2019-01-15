package com.moscovin.orbal.proxy;

import com.moscovin.orbal.*;
import com.moscovin.orbal.guis.GuiHandler;
import com.moscovin.orbal.guis.RenderTickHandler;
import com.moscovin.orbal.network.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
    public Schedules schedules;

    private static CreativeTabs TabOrbal = new CreativeTabs("orbal") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(OrbalItems.orbalPanel);
        }
    };

    public void preinit() {
        NetworkManager.register(Side.SERVER);
        new OrbalItems(TabOrbal).init();
        new OrbalBlocks(TabOrbal).init();
        OrbalEntities.init();
    }

    public void init() {
        schedules = new Schedules();

        new Crafts();
        new GuiHandler();

        MinecraftForge.EVENT_BUS.register(new RenderTickHandler(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new com.moscovin.orbal.Events(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(schedules);
    }

    public void openSkillTree(EntityPlayer player){}

    public CommonProxy() {
    }
}
