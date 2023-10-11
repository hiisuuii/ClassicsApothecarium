package hisui.apothecarium.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class SlipperinessFixMixin {
    @Inject(method = "getVelocityAffectingPos", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfoReturnable<BlockPos> cir) {
        Entity entity = ((Entity)(Object)this);
        cir.setReturnValue(
                entity.getWorld().getBlockState(entity.getBlockPos()).isAir() ?
                        ((EntityGetPosWithYOffsetMixin)entity).invokeGetPosWithYOffset(0.500001f)
                        : entity.getBlockPos());

    }


}
