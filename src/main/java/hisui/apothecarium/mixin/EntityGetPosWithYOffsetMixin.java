package hisui.apothecarium.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityGetPosWithYOffsetMixin {
    @Invoker("getPosWithYOffset")
    public BlockPos invokeGetPosWithYOffset(float offset);
}
