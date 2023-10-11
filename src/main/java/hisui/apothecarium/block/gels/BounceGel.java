package hisui.apothecarium.block.gels;

import hisui.apothecarium.block.GelBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class BounceGel extends GelBlock {
    public BounceGel(Settings settings, int color) {
        super(settings, color);
    }

    @Override
    protected void effect(Entity entity) {
        entity.fallDistance = 0;
        if(!entity.isSneaking()){
            var velocity = entity.getVelocity();
            var newVelocity = new Vec3d(velocity.x, 1.2, velocity.z);
            entity.setVelocity(newVelocity);
        }
    }
}
