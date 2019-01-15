package com.moscovin.orbal;

import com.moscovin.orbal.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;


@Mod(modid = OrbalCore.MODID, version = OrbalCore.VERSION, name = OrbalCore.NAME, acceptedMinecraftVersions = "[1.11.2]")
public class OrbalCore
{
    public static final String MODID = "orbal";
    public static final String VERSION = "0.0.1";
    public static final String NAME = "Orbal Art";
    private static Logger logger;

    public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel("orbal-network");

    @SidedProxy(serverSide="com.moscovin.orbal.proxy.CommonProxy", clientSide="com.moscovin.orbal.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance("orbal")
    public static OrbalCore instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preinit();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

}
