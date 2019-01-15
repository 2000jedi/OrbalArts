package com.moscovin.orbal;

import com.moscovin.orbal.items.Magics.Magic;
import com.moscovin.orbal.orbment.OrbalConfig;
import com.moscovin.orbal.orbment.OrbalPropsData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBinds {
    private static KeyBinding self_magic = new KeyBinding("key.self_magic.desc", Keyboard.KEY_F, "key.orbal.category");
    private static KeyBinding skill_tree = new KeyBinding("key.skill_tree.desc", Keyboard.KEY_P, "key.orbal.category");
    private static KeyBinding change_team = new KeyBinding("key.change_team.desc", Keyboard.KEY_UP, "key.orbal.category");

    @SideOnly(Side.CLIENT)
    public static void init() {
        ClientRegistry.registerKeyBinding(self_magic);
        ClientRegistry.registerKeyBinding(skill_tree);
        ClientRegistry.registerKeyBinding(change_team);
    }

    @SideOnly(Side.CLIENT)
    static void checkKeyBind(Minecraft minecraft) {
        if (self_magic.isPressed()) {
            Item item = minecraft.player.inventory.getCurrentItem().getItem();
            if (item instanceof Magic) {
                ((Magic) item).useOnSelf(minecraft.player);
            }
        }
        if (skill_tree.isPressed()) {
            if (OrbalConfig.notNull(minecraft.player)) {
                OrbalCore.proxy.openSkillTree(minecraft.player);
            }
        }
        if (change_team.isPressed()) {
            try {
                OrbalPropsData data = new OrbalPropsData(Minecraft.getMinecraft().player);
                data.team = (data.team + 1) % 3;
                Minecraft.getMinecraft().player.sendChatMessage("Changing team to: " + "RGB".charAt(data.team));
                OrbalConfig.setTag(Minecraft.getMinecraft().player, data);
            } catch (NullPointerException e) {
                Minecraft.getMinecraft().player.sendChatMessage("Failed! Please restart game.");
            }
        }
    }
}
