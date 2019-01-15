package com.moscovin.orbal.guis;

import com.moscovin.orbal.OrbalCore;
import com.moscovin.orbal.OrbalItems;
import com.moscovin.orbal.items.Magics.Magic;
import com.moscovin.orbal.items.Seipth.SeipthEnum;
import com.moscovin.orbal.orbment.OrbalConfig;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiSkillTree extends GuiScreen {
    private static final ResourceLocation GUITextures = new ResourceLocation(OrbalCore.MODID + ":" + "textures/gui/skilltree.png");

    @Nonnull
    private final EntityPlayer player;
    @Nonnull
    private SeipthEnum tree;
    private int[] points;

    public GuiSkillTree(@Nonnull EntityPlayer player) {
        this.tree = SeipthEnum.WATER;
        this.player = player;
    }

    @Override
    public void drawScreen(int x, int y, float p) {
        // update
        OrbalPropsData prop = new OrbalPropsData(player);
        this.points = new int[]{prop.water + 100, prop.fire + 100, prop.wind + 100, prop.earth + 100, prop.dark + 100};
        for (MagicButton magic: magics) {
            magic.visible = magic.magic.Type == tree;
            if (magic.own()) {
                this.points[0] -= magic.magic.requirement[0];
                this.points[1] -= magic.magic.requirement[1];
                this.points[2] -= magic.magic.requirement[2];
                this.points[3] -= magic.magic.requirement[3];
                this.points[4] -= magic.magic.requirement[4];
            }
        }

        // draw
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUITextures);
        drawTexturedModalRect((width - 500) / 2, (height - 500) / 2, 0, 0, 500, 500);
        // pages
        for (PropButton page : pages) {
            page.drawButton(this.mc, x, y);
        }
        // skills
        for (MagicButton magic: magics) {
            if (magic.visible) {
                magic.drawButton(this.mc, x, y);

                // draw tooltip
                if (magic.isMouseOver(x, y)) {
                    List<String> display = new ArrayList<String>();
                    display.add(I18n.format(magic.magic.getUnlocalizedName() + ".name"));
                    display.add(I18n.format("tooltip.requirement"));
                    for (int i=0; i<5; ++i) {
                        if (magic.magic.requirement[i] > 0) {
                            display.add(SeipthEnum.toString(i) + ": " + magic.magic.requirement[i]);
                        }
                    }
                    if (magic.prev != null)
                        display.add(I18n.format(magic.prev.getUnlocalizedName() + ".name"));
                    drawHoveringText(display, x, y);
                }
            }
        }
        // point remaining
        List<String> display = new ArrayList<String>();
        display.add(I18n.format("tooltip.remain_points") + points[tree.toInt()]);

        drawHoveringText(display, 150, 80);
        super.drawScreen(x, y, p);
    }

    @Override
    public void updateScreen() {
        drawScreen(Mouse.getEventX() * this.width / this.mc.displayWidth,this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1,0);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (PropButton page : pages) {
            if (page.mousePressed(this.mc, mouseX, mouseY)) {
                tree = page.typ;
            }
        }
        for (MagicButton magic: magics) {
            if (magic.mousePressed(this.mc, mouseX, mouseY)){
                if (OrbalConfig.hasMagic(player, magic.magic) && !ownNext(magic)) {
                    OrbalConfig.removeMagic(player, magic.magic);
                    OrbalConfig.uploadPanel(player, magic.magic, false);
                } else {
                    if (metReq(magic) && ownPrev(magic)) {
                        OrbalConfig.giveMagic(player, magic.magic);
                        OrbalConfig.uploadPanel(player, magic.magic, true);
                    }
                }
            }
        }
        updateScreen();
    }

    @SideOnly(Side.CLIENT)
    class PropButton extends GuiButton {
        ResourceLocation res;
        SeipthEnum typ;
        PropButton(int x, int y, String res, SeipthEnum typ) {
            super(-1, x, y, 32, 32, "");
            this.enabled = true;
            this.res = new ResourceLocation(OrbalCore.MODID + ":" + res);
            this.typ = typ;
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            return super.mousePressed(mc, mouseX, mouseY);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.typ == tree)
                GL11.glColor4f(1.0f,1.0f,1.0f,1.0f);
            else
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
            mc.getTextureManager().bindTexture(res);
            drawTexturedModalRect(xPosition, yPosition, 0, 0, width, height);
        }
    }

    @SideOnly(Side.CLIENT)
    class MagicButton extends GuiButton {
        ResourceLocation res;
        int[] loc;
        Magic magic;
        Magic prev;

        MagicButton(int x, int y, String res, Item magic, Item prev) {
            super(-1, 100 + x * 20, 100 + y * 20, 16, 16, "");
            this.enabled = true;
            this.loc = new int[]{x, y};
            this.res = new ResourceLocation(OrbalCore.MODID + ":" + res + ".png");
            this.magic = (Magic) magic;
            this.prev = (Magic) prev;
        }

        public boolean own() {
            return OrbalConfig.hasMagic(player, this.magic);
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            return super.mousePressed(mc, mouseX, mouseY);
        }

        public boolean isMouseOver(int mouseX, int mouseY) {
            return this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        }

        @Override
        public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY) {
            if (this.own())
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            else
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
            mc.getTextureManager().bindTexture(res);
            drawTexturedModalRect(xPosition, yPosition, 0, 0, width, height);
        }

    }

    // Switch Tree Buttons
    private PropButton pages[] = {
            new PropButton(100, 10, "textures/gui/water.png", SeipthEnum.WATER),
            new PropButton(150, 10, "textures/gui/fire.png", SeipthEnum.FIRE),
            new PropButton(200, 10, "textures/gui/wind.png", SeipthEnum.WIND),
            new PropButton(250, 10, "textures/gui/earth.png", SeipthEnum.EARTH),
            new PropButton(300, 10, "textures/gui/dark.png", SeipthEnum.DARK)
    };

    private MagicButton magics[] = {
            new MagicButton(0,0,"textures/gui/heal", OrbalItems.heal, null),
            new MagicButton(1,0, "textures/gui/heal", OrbalItems.bless, null),
            new MagicButton(0,1,"textures/gui/heal", OrbalItems.heal2, OrbalItems.heal),
            new MagicButton(1, 1, "textures/gui/heal", OrbalItems.absorb, null),
            new MagicButton(0,0,"textures/gui/fireball", OrbalItems.fireball, null),
            new MagicButton(0, 1, "textures/gui/fireball", OrbalItems.firestorm, OrbalItems.fireball),
            new MagicButton(0, 2, "textures/gui/fireball", OrbalItems.hell, OrbalItems.firestorm),
            new MagicButton(0, 0, "textures/gui/heal", OrbalItems.teleport, null),
            new MagicButton(0, 0, "textures/gui/heal", OrbalItems.waterprison, null),
    };

    private boolean ownPrev(MagicButton button) {
        if (button.prev == null) // no prerequisite magic
            return true;

        for (MagicButton magic: magics) {
            if (magic.magic == button.prev)
                return magic.own();
        }

        return false;
    }

    private boolean ownNext(MagicButton button) {
        for (MagicButton magic: magics) {
            if (magic.prev == button.magic)
                return magic.own();
        }

        return false;
    }

    private boolean metReq(MagicButton magic) {
        for (int i=0; i<5; i++)
            if (points[i] < magic.magic.requirement[i])
                return false;
        return true;
    }
}
