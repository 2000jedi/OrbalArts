package com.moscovin.orbal.guis;

import com.moscovin.orbal.OrbalCore;
import com.moscovin.orbal.OrbalPotions;
import com.moscovin.orbal.orbment.OrbalConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OrbalHUD extends GuiIngame{
    private static final ResourceLocation bar = new ResourceLocation(OrbalCore.MODID + ":" + "textures/gui/bar.png");
    private static final ResourceLocation hp = new ResourceLocation(OrbalCore.MODID + ":" + "textures/gui/hp.png");
    private static final ResourceLocation mp = new ResourceLocation(OrbalCore.MODID + ":" + "textures/gui/mp.png");
    private static final ResourceLocation sttc = new ResourceLocation(OrbalCore.MODID + ":" + "textures/gui/static.png");
    public OrbalHUD(Minecraft m) {
        super(m);
    }

    private double min(double a, double b) {
        if (a < b)
            return a;
        return b;
    }

    @Override
    protected void renderPlayerStats(ScaledResolution scaledRes) { // change the HP and MP bar
        NBTTagCompound tag = OrbalConfig.getTag(this.mc.player);
        double MPLevel;
        double MaxMPLevel;
        double HPLevel;
        double MaxHPLevel;
        if (tag == null) {
            MPLevel = 0;
            MaxMPLevel = 0;
            MaxHPLevel = 20;
            HPLevel = min(this.mc.player.getHealth(), MaxHPLevel);
        } else {
            MPLevel = tag.getDouble("mana");
            MaxMPLevel = tag.getDouble("mmana");
            MaxHPLevel = tag.getDouble("mhealth");
            HPLevel = tag.getDouble("health");
        }
        FoodStats foodstats = this.mc.player.getFoodStats();
        foodstats.setFoodLevel(10); // lock food level

        int HorizontalIndent = scaledRes.getScaledWidth() / 2 - 91;
        int HorizontalIndent2 = scaledRes.getScaledWidth() / 2 + 1;
        int VerticalIndent = scaledRes.getScaledHeight() - 48;

        this.mc.getTextureManager().bindTexture(bar);
        this.drawTexturedModalRect(HorizontalIndent, VerticalIndent, 0, 0, 90, 9);
        this.mc.getTextureManager().bindTexture(hp);
        this.drawTexturedModalRect(HorizontalIndent, VerticalIndent, 0, 0, (int)(90 * HPLevel / MaxHPLevel), 9);
        this.mc.getTextureManager().bindTexture(bar);
        this.drawTexturedModalRect(HorizontalIndent2, VerticalIndent, 0, 0, 90, 9);
        if (this.mc.player.isPotionActive(OrbalPotions.cooldown)) {
            this.mc.getTextureManager().bindTexture(sttc);
        }
        else {
            this.mc.getTextureManager().bindTexture(mp);
        }
        this.drawTexturedModalRect(HorizontalIndent2, VerticalIndent, 0, 0, (int)(90 * MPLevel / MaxMPLevel), 9);
        String HPStatus = (int)HPLevel + "/" + (int)MaxHPLevel;
        this.mc.fontRendererObj.drawString(HPStatus, HorizontalIndent + 45 - this.mc.fontRendererObj.getStringWidth(HPStatus) / 2, VerticalIndent, 0x000000);
        String MPStatus = (int)MPLevel + "/" + (int)MaxMPLevel;
        this.mc.fontRendererObj.drawString(MPStatus, HorizontalIndent2 + 45 - this.mc.fontRendererObj.getStringWidth(MPStatus) / 2, VerticalIndent, 0x000000);
        this.mc.mcProfiler.endSection();
    }
}
