package com.moscovin.orbal.guis;

import com.moscovin.orbal.OrbalCore;
import com.moscovin.orbal.TileEntity.CraftTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {
    public static final int GUI_PANEL = 0;

    public GuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(OrbalCore.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case GUI_PANEL:
                return new ContainerPanel(player, (CraftTable) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case GUI_PANEL:
                return new GuiPanel(new ContainerPanel(player, (CraftTable) world.getTileEntity((new BlockPos(x, y, z)))));
            default:
                return null;
        }
    }
}
