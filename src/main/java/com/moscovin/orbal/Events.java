package com.moscovin.orbal;

import com.moscovin.orbal.orbment.OrbalConfig;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Events {
    private Minecraft minecraft;

    public Events(Minecraft mc) {
        minecraft = mc;
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            KeyBinds.checkKeyBind(minecraft);
            if (OrbalConfig.notNull(minecraft.player)) {
                OrbalConfig.tickPlayer(minecraft.player);
            }
        }
    }

    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.END){
            for (EntityPlayerMP player: FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
                if (OrbalConfig.notNull(player)) {
                    OrbalConfig.tickPlayer(player);
                    if (OrbalConfig.getTag(player).getBoolean("down")) {
                        NBTTagCompound tag = OrbalConfig.getTag(player);
                        tag.setBoolean("down", false);
                        OrbalConfig.setTag(player, tag);
                        OrbalConfig.uploadPanel(player);
                        player.setHealth(-1);
                    }
                }
                OrbalPotions.tick(player);
            }
        }
    }

    @SubscribeEvent
    public void livingEvent(LivingEvent.LivingUpdateEvent e) {
        if (e.getEntityLiving() != null) {
            OrbalPotions.tick(e.getEntityLiving());
        }
    }

    @SubscribeEvent
    public void getDamage(LivingHurtEvent e) {
        if (e.getEntity() instanceof EntityPlayer) {
            e.setCanceled(true);
            e.setResult(Event.Result.DENY);
            System.out.println("Player get damage from: " + e.getSource().getDamageType()); // TODO: remove debug
            NBTTagCompound tag = OrbalConfig.getTag((EntityPlayer) e.getEntity());
            double multiplier;
            if (tag != null) {
                OrbalPropsData data = new OrbalPropsData(tag);
                multiplier = OrbalConfig.damageMultiplier(data, e);
                System.out.println("Deal damage: " + (float) (e.getAmount() * multiplier)); // TODO: remove debug
                data.getDamage((float) (e.getAmount() * multiplier));
                OrbalConfig.setTag((EntityPlayer) e.getEntity(), data);
            } else {
                e.setCanceled(false);
                e.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public void dropEvent(LivingDropsEvent e) {
        if (e.getEntityLiving() instanceof EntityZombie) {
            Item item;
            switch ((int)(Math.random() * 5)) {
                case 0:
                    item = OrbalItems.orbmentBlue;
                    break;
                case 1:
                    item = OrbalItems.orbmentRed;
                    break;
                case 2:
                    item = OrbalItems.orbmentGreen;
                    break;
                case 3:
                    item = OrbalItems.orbmentYellow;
                    break;
                case 4:
                    item = OrbalItems.orbmentDark;
                    break;
                default:
                    item = OrbalItems.orbalPanel;
            }
            e.getEntityLiving().dropItem(item, (int) (Math.random() * 3));
        }
    }
}
