package com.moscovin.orbal.blocks;

import com.moscovin.orbal.TileEntity.CraftTable;
import com.moscovin.orbal.guis.GuiHandler;
import com.moscovin.orbal.OrbalCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class OrbalCraftTable extends BlockContainer {
    public OrbalCraftTable() {
        super(Material.WOOD);

        String name = "craft_table";
        setUnlocalizedName(name);

        setHardness(2f);
        setSoundType(SoundType.WOOD);

        setRegistryName(name);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(OrbalCore.instance, GuiHandler.GUI_PANEL, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        CraftTable te =  (CraftTable)world.getTileEntity(pos);

        IItemHandler item = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int i = item.getSlots() - 1; i >= 0; --i)
        {
            if (item.getStackInSlot(i) != ItemStack.EMPTY)
            {
                Block.spawnAsEntity(world, pos, item.getStackInSlot(i));
                ((IItemHandlerModifiable) item).setStackInSlot(i, ItemStack.EMPTY);
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new CraftTable();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
