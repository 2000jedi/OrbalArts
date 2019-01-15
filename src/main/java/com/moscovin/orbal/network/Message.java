package com.moscovin.orbal.network;


import com.moscovin.orbal.OrbalCore;
import com.moscovin.orbal.items.Magics.Magic;
import com.moscovin.orbal.orbment.OrbalConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class Message implements IMessage
{
    public static class Handler implements IMessageHandler<Message, IMessage>
    {
        @Override
        public IMessage onMessage(Message message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                final UUID playerUUID = message.tag.getUniqueId("player");
                final NBTTagCompound nbt = message.tag;
                Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        EntityPlayer player = Minecraft.getMinecraft().getIntegratedServer().getPlayerList().getPlayerByUUID(playerUUID);
                        if (OrbalConfig.notNull(player)) {
                            OrbalConfig.setTag(player, nbt, false);
                        }
                        String new_magic = nbt.getString("new_magic");
                        if (!new_magic.equals("")) {
                            if (nbt.getBoolean("give")) {
                                OrbalConfig.giveMagic(player, (Magic) Item.getByNameOrId(OrbalCore.MODID + ":" + new_magic.substring(5)));
                            }
                            else
                                OrbalConfig.removeMagic(player, (Magic) Item.getByNameOrId(OrbalCore.MODID + ":" + new_magic.substring(5)));
                        }
                    }
                });
            }
            return null;
        }
    }

    public NBTTagCompound tag;

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        tag = ByteBufUtils.readTag(byteBuf);
    }


    @Override
    public void toBytes(ByteBuf byteBuf) {
        ByteBufUtils.writeTag(byteBuf, tag);
    }
}
