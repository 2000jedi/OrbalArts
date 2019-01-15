package com.moscovin.orbal.entity;

import com.moscovin.orbal.OrbalCore;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLoader {
    private static int nextID = 0;

    @SideOnly(Side.CLIENT)
    public static void register() {
        registerEntity(MagicBall.class, "magic_ball", 80, 2, true);
        registerEntityRender(MagicBall.class, MagicBall.Render.class);
    }

    @SideOnly(Side.CLIENT)
    private static <T extends Entity> void registerEntityRender(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
                                       int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(new ResourceLocation("entity_magic_ball"), entityClass, name, nextID++, OrbalCore.instance, trackingRange, updateFrequency,
                sendsVelocityUpdates);
    }

    public static class EntityRenderFactory<E extends Entity> implements IRenderFactory<E> {
        private final Class<? extends Render<E>> renderClass;

        public EntityRenderFactory(Class<? extends Render<E>> renderClass) {
            this.renderClass = renderClass;
        }

        @Override
        public Render<E> createRenderFor(RenderManager manager)
        {
            try
            {
                return renderClass.getConstructor(RenderManager.class).newInstance(manager);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
