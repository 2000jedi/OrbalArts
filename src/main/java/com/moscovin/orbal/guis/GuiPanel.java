package com.moscovin.orbal.guis;

import com.moscovin.orbal.OrbalCore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiPanel extends GuiContainer {
	private static final ResourceLocation GUITextures = new ResourceLocation(OrbalCore.MODID + ":" + "textures/gui/craft.png");
    private ContainerPanel panel;
	public GuiPanel(ContainerPanel inventory){
	    super(inventory);
	    panel = inventory;
	}

    @Override
    public void drawGuiContainerForegroundLayer(int i1, int i2) {
	}

    @Override
    public void drawGuiContainerBackgroundLayer(float p_146976_1_, int i1, int i2) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUITextures);

        int px = (width - xSize) / 2;
        int py = (height - ySize) / 2;
        drawTexturedModalRect(px, py, 0, 0, xSize, ySize);
    }

	@Override
	public boolean doesGuiPauseGame() {
		return false;
}
}
