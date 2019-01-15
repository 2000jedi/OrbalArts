package com.moscovin.orbal.TileEntity;

import com.moscovin.orbal.OrbalCore;
import com.moscovin.orbal.items.Seipth.Seipth;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CraftTable extends TileEntity implements ITickable{
    public static String name = "craft_table";

    private ItemStackHandler inventory = new ItemStackHandler(8);

    private ItemStack prevPanel = ItemStack.EMPTY;
    @Override
    public void update() {
        if (!this.world.isRemote) {
            OrbalPropsData props = new OrbalPropsData();
            if (inventory.getStackInSlot(0) != prevPanel) { // extract seipths
                if (inventory.getStackInSlot(0) != ItemStack.EMPTY && inventory.getStackInSlot(0).getTagCompound() != null) {
                    props = new OrbalPropsData(inventory.getStackInSlot(0).getTagCompound());
                    for (int i = 0; i < 7; ++i) {
                        if (!props.seipths[i].equals(""))
                            inventory.setStackInSlot(i + 1, new ItemStack(Item.getByNameOrId(OrbalCore.MODID + ":" + props.seipths[i])));
                    }
                } else {
                    if (inventory.getStackInSlot(0) == ItemStack.EMPTY) {
                        for (int i = 1; i < 8; ++i) {
                            inventory.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    }
                }
            }
            prevPanel = inventory.getStackInSlot(0);
            for (int i = 1; i < 8; ++i) {
                if (!inventory.getStackInSlot(i).isEmpty()) {
                    props.seipths[i-1] = inventory.getStackInSlot(i).getUnlocalizedName().substring(5);
                    Seipth seipth = (Seipth) inventory.getStackInSlot(i).getItem();
                    switch (seipth.Type) {
                        case FIRE:
                            props.fire += seipth.strength;
                            break;
                        case WATER:
                            props.water += seipth.strength;
                            break;
                        case EARTH:
                            props.earth += seipth.strength;
                            break;
                        case WIND:
                            props.wind += seipth.strength;
                            break;
                        case DARK:
                            props.dark += seipth.strength;
                            break;
                    }
                    props = seipth.effect(props);
                } else {
                    props.seipths[i-1] = "";
                }
            }
            ItemStack panel = inventory.getStackInSlot(0);
            panel.setTagCompound(props.toNBT());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory.deserializeNBT(compound.getCompoundTag("Inventory"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.inventory.serializeNBT());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            @SuppressWarnings("unchecked")
            T result = (T) inventory;
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
