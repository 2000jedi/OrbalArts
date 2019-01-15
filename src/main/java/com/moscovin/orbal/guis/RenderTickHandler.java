package com.moscovin.orbal.guis;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTickHandler {
    Boolean started = false;
    private Minecraft mc;

    public RenderTickHandler(Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            this.tickStart();
        }

        if (event.phase == TickEvent.Phase.END) {
            this.tickEnd();
        }

    }

    public void tickStart() {
        if (!this.started) {
            this.mc.ingameGUI = new OrbalHUD(this.mc);
            this.started = true;
        }
    }

    public void tickEnd() {
    }
}
