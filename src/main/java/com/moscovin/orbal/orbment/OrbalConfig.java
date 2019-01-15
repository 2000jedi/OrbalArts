package com.moscovin.orbal.orbment;

import com.moscovin.orbal.OrbalItems;
import com.moscovin.orbal.items.Magics.Magic;
import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.network.Message;
import com.moscovin.orbal.network.NetworkManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class OrbalConfig {
    public static boolean sameTeam(EntityPlayer a, EntityPlayer b) {
        OrbalPropsData a_ = new OrbalPropsData(a);
        OrbalPropsData b_ = new OrbalPropsData(b);
        return a_.team == b_.team;
    }

    public static double damageMultiplier(OrbalPropsData data, LivingHurtEvent e) {
        if (e.getSource() instanceof OrbalDamage.OrbalDamageSource)
            return data.seipthCorrection(((OrbalDamage.OrbalDamageSource) e.getSource()).type);
        if (e.getSource() == DamageSource.DROWN)
            return data.seipthCorrection(SeipthEnum.WATER) * 10;
        if (e.getSource() == DamageSource.IN_FIRE || e.getSource() == DamageSource.ON_FIRE)
            return data.seipthCorrection(SeipthEnum.FIRE) * 10;
        if (e.getSource() == DamageSource.LAVA)
            return data.seipthCorrection(SeipthEnum.FIRE) * 50;
        if (e.getSource() == DamageSource.FALL || e.getSource() == DamageSource.LIGHTNING_BOLT)
            return data.seipthCorrection(SeipthEnum.WIND) * 10;
        if (e.getSource() == DamageSource.FALLING_BLOCK || e.getSource() == DamageSource.IN_WALL)
            return data.seipthCorrection(SeipthEnum.EARTH) * 10;
        return data.corr() * 10;
    }

    public static boolean notNull(EntityPlayer player) {
        return player != null && player.inventory != null && player.inventory.mainInventory != null && OrbalConfig.getTag(player) != null;
    }

    public static void tickPlayer(EntityPlayer player) {
        OrbalPropsData data = new OrbalPropsData(player);
        data.tick();

        setTag(player, data.toNBT(), false);
    }

    public static NBTTagCompound getTag(EntityPlayer player) {
        if (player.inventory.offHandInventory.get(0).getItem() == OrbalItems.orbalPanel) {
            return player.inventory.offHandInventory.get(0).getTagCompound();
        } else {
            return null;
        }
    }

    public static void setTag(EntityPlayer player, NBTTagCompound nbt) {
        if (player.inventory.offHandInventory.get(0).getItem() == OrbalItems.orbalPanel) {
            player.inventory.offHandInventory.get(0).setTagCompound(nbt);
        }

        uploadPanel(player);
    }

    public static void setTag(EntityPlayer player, NBTTagCompound nbt, boolean flag) {
        if (player.inventory.offHandInventory.get(0).getItem() == OrbalItems.orbalPanel) {
            player.inventory.offHandInventory.get(0).setTagCompound(nbt);
        }
    }

    public static void setTag(EntityPlayer player, OrbalPropsData data) {
        setTag(player, data.toNBT());
    }

    public static boolean hasMagic(EntityPlayer player, Magic magic) {
        return player.inventory.getSlotFor(new ItemStack(magic, 1)) != -1;
    }

    public static void removeMagic(EntityPlayer player, Magic magic) {
        player.inventory.removeStackFromSlot(player.inventory.getSlotFor(new ItemStack(magic, 1)));
    }

    public static void giveMagic(EntityPlayer player, Magic magic) {
        player.inventory.addItemStackToInventory(new ItemStack(magic, 1));
    }

    public static void uploadPanel(EntityPlayer player) { // only tick when use item or get damage
        NBTTagCompound tag = getTag(player);
        Message message = new Message();
        message.tag = tag;
        NBTTagList list = new NBTTagList();
        player.inventory.writeToNBT(list);
        message.tag.setUniqueId("player", player.getUniqueID());
        message.tag.setString("new_magic", "");
        NetworkManager.instance.sendToServer(message);
    }

    public static void uploadPanel(EntityPlayer player, Magic magic, boolean give) {
        NBTTagCompound tag = getTag(player);
        Message message = new Message();
        message.tag = tag;
        NBTTagList list = new NBTTagList();
        player.inventory.writeToNBT(list);
        message.tag.setUniqueId("player", player.getUniqueID());
        message.tag.setString("new_magic", magic.getUnlocalizedName());
        message.tag.setBoolean("give", give);
        NetworkManager.instance.sendToServer(message);
    }
}
