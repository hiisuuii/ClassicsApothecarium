package hisui.apothecarium.mixin;

import com.ibm.icu.impl.Assert;
import net.minecraft.block.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MapColor.class)
public interface MapColorArrayAccessorMixin {
    @Accessor
    static MapColor[] getCOLORS(){
        throw new AssertionError();
    }

}
