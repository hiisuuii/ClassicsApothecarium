package hisui.apothecarium.item.vials.throwable;

import hisui.apothecarium.entity.ThrownFlameburstVialEntity;
import hisui.apothecarium.entity.ThrownFrostburstVialEntity;
import hisui.apothecarium.item.vials.base.VialThrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FrostburstVial extends VialThrowable {
    public FrostburstVial(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            ThrownFrostburstVialEntity vialEntity = new ThrownFrostburstVialEntity(world, user);
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
