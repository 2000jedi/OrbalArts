package com.moscovin.orbal.guis;

import com.moscovin.orbal.items.Seipth.Seipth;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SeipthInventoryPanel extends SlotItemHandler {
    public SeipthInventoryPanel(ItemStackHandler h, int index, int posx, int posy) {
        super(h, index, posx, posy);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof Seipth;
    }
    @Override
    public int getItemStackLimit(ItemStack stack){
        return 1;
    }
}
