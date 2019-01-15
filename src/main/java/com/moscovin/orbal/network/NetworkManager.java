package com.moscovin.orbal.network;

import com.moscovin.orbal.OrbalCore;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkManager {
    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(OrbalCore.MODID);
    private static int nextID = 0;
    public static void register(Side side) {
        registerMessage(Message.Handler.class, Message.class, side);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        instance.registerMessage(messageHandler, requestMessageType, nextID++, side);
    }
}
