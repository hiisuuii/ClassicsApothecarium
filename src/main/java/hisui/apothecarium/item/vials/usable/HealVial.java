package hisui.apothecarium.item.vials.usable;

import hisui.apothecarium.item.vials.base.VialUseable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HealVial extends VialUseable {
    public HealVial(Settings settings, int color) {
        super(settings, color);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this, 5);
        return super.use(world, user, hand);
    }

    @Override
    public void effect(World world, PlayerEntity user, Hand hand) {
        super.effect(world, user, hand);
        if (!world.isClient) {
            user.heal(6);
        }
    }


}
