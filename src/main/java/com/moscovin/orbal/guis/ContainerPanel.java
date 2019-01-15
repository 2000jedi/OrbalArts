package com.moscovin.orbal.guis;

import com.moscovin.orbal.OrbalItems;
import com.moscovin.orbal.TileEntity.CraftTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;


public class ContainerPanel extends Container {
    private ItemStackHandler inventory;
    private Slot[] inv = {null, null, null, null, null, null, null};
    private CraftTable tile;

    public ContainerPanel(EntityPlayer player, CraftTable tile) {
        this.tile = tile;
        this.inventory = (ItemStackHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.addSlotToContainer(inv[0] = new SlotItemHandler(inventory, 0, 35, 33){
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack != null && stack.getCount() == 1 && stack.getItem() == OrbalItems.orbalPanel && super.isItemValid(stack);
            }
            @Override
            public int getItemStackLimit(ItemStack stack){
                return 1;
            }
        });
        this.addSlotToContainer(inv[1] = new SeipthInventoryPanel(inventory, 1, 105, 33));
        this.addSlotToContainer(inv[2] = new SeipthInventoryPanel(inventory, 2, 115, 53));
        this.addSlotToContainer(new SeipthInventoryPanel(inventory, 3, 115, 13));
        this.addSlotToContainer(new SeipthInventoryPanel(inventory, 4, 135, 13));
        this.addSlotToContainer(new SeipthInventoryPanel(inventory, 5, 135, 53));
        this.addSlotToContainer(new SeipthInventoryPanel(inventory, 6, 145, 33));
        this.addSlotToContainer(new SeipthInventoryPanel(inventory, 7, 125, 33));
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return null;
        }
        return null;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.getDistanceSq(this.tile.getPos()) <= 64;
    }
}
