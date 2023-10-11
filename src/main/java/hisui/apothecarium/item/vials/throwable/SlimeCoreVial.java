package hisui.apothecarium.item.vials.throwable;

import hisui.apothecarium.entity.ThrownCombustionVialEntity;
import hisui.apothecarium.entity.ThrownSlimeCoreVialEntity;
import hisui.apothecarium.item.vials.base.VialThrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SlimeCoreVial extends VialThrowable {
    public SlimeCoreVial(Settings settings) {
        super(settings);
    }

    public SlimeCoreVial(Settings settings, int color) {
        super(settings, color);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            ThrownSlimeCoreVialEntity vialEntity = new ThrownSlimeCoreVialEntity(world, user);
            vialEntity.setItem(itemStack);
            vialEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0f, 0.75f, 1.0f);
            world.spawnEntity(vialEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
