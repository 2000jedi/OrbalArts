package com.moscovin.orbal;

import com.moscovin.orbal.blocks.OrbalCraftTable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class OrbalBlocks {
    private CreativeTabs creativeTab;

    public static Block orbalCraftTable = new OrbalCraftTable();

    public OrbalBlocks(CreativeTabs creativeTab){
        this.creativeTab = creativeTab;
    }

    public void init() {
        register(orbalCraftTable);
    }

    private void register(Block item) {
        ForgeRegistries.BLOCKS.register(item);
        ForgeRegistries.ITEMS.register(new ItemBlock(item).setRegistryName(item.getRegistryName()));
        item.setCreativeTab(this.creativeTab);
    }

    public void registerRenderer(){
        registerModel(orbalCraftTable);
    }

    private void registerModel(Block item) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(item), 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static class Events {
        @SubscribeEvent
        public void registerItems(RegistryEvent.Register<Item> e) {
            register(e, orbalCraftTable);
        }

        private void register(RegistryEvent.Register<Item> e, Block item) {
            e.getRegistry().register(Item.getItemFromBlock(item));
        }
    }
}
