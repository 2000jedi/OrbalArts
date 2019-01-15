package com.moscovin.orbal.proxy;

import com.moscovin.orbal.KeyBinds;
import com.moscovin.orbal.OrbalBlocks;
import com.moscovin.orbal.OrbalItems;
import com.moscovin.orbal.entity.EntityLoader;
import com.moscovin.orbal.guis.GuiSkillTree;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preinit() {
        super.preinit();
        new OrbalItems(null).registerRenderer();
        new OrbalBlocks(null).registerRenderer();
        EntityLoader.register();
        KeyBinds.init();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void openSkillTree(EntityPlayer player) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiSkillTree(player));
    }

    public ClientProxy() {
    }
}
