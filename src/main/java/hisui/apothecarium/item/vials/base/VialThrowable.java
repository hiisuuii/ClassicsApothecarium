package hisui.apothecarium.item.vials.base;

import hisui.apothecarium.entity.ThrownVialEntity;
import hisui.apothecarium.registers.EntityRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VialThrowable extends Item {

    int color = 0xFFFFFF;

    public VialThrowable(Settings settings) {
        super(settings);
    }

    public VialThrowable(Settings settings, int color) {
        super(settings);
        this.color = color;
    }

    @Override
    @Deprecated
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            ThrownVialEntity vialEntity = new ThrownVialEntity(EntityRegister.THROWN_BASE_VIAL, world, user);
            vialEntity.setItem(itemStack);
            vialEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0f, 0.5f, 1.0f);
            world.spawnEntity(vialEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public int getColor() {
        return color;
    }
}
