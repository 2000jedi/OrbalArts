package com.moscovin.orbal;

import com.moscovin.orbal.TileEntity.CraftTable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class OrbalEntities {
    public static void init() {
        GameRegistry.registerTileEntity(CraftTable.class, OrbalCore.MODID + ":" + CraftTable.name);
    }
}
