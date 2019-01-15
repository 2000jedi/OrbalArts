package com.moscovin.orbal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Crafts {
    public Crafts(){
        GameRegistry.addShapelessRecipe(new ItemStack(OrbalBlocks.orbalCraftTable),
            OrbalItems.orbmentRed, OrbalItems.orbmentBlue, OrbalItems.orbmentYellow, OrbalItems.orbmentGreen);
        /*GameRegistry.addRecipe(new ItemStack(OrbalBlocks.orbalSynthesizeTable),
                "BAC",
                "D E",
                'A', Blocks.CRAFTING_TABLE, 'B', OrbalItems.orbmentRed, 'D', OrbalItems.orbmentBlue, 'C', OrbalItems.orbmentYellow, 'E', OrbalItems.orbmentGreen);*/
        // GameRegistry.addSmelting(new ItemStack(Items.dye, 1, 1), new ItemStack(Items.dye, 1, 11), 0.1F);
    }
}
