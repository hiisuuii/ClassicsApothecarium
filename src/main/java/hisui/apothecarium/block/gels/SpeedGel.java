package hisui.apothecarium.block.gels;

import hisui.apothecarium.block.GelBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class SpeedGel extends GelBlock {
    public SpeedGel(Settings settings, int color) {
        super(settings, color);
    }

    @Override
    protected void effect(Entity entity) {
        super.effect(entity);

        if(entity.isOnGround()) {
            var velocity = entity.getVelocity();
//          var newVelocity = new Vec3d(velocity.x*1.2, velocity.y, velocity.z*1.2);
            entity.setVelocity(entity.getVelocity().multiply(1.2, 1, 1.2));
        }
    }
}
