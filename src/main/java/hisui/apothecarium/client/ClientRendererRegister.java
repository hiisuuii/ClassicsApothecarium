package hisui.apothecarium.client;

import hisui.apothecarium.registers.EntityRegister;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ClientRendererRegister {
    public static void init() {
        EntityRendererRegistry.register(EntityRegister.THROWN_GEL_VIAL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegister.THROWN_BASE_VIAL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegister.THROWN_AIRBURST_VIAL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegister.THROWN_STORMBURST_VIAL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegister.THROWN_COMBUSTION_VIAL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegister.THROWN_FLAMEBURST_VIAL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegister.THROWN_FROSTBURST_VIAL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegister.THROWN_SLIME_VIAL, FlyingItemEntityRenderer::new);
    }
}
