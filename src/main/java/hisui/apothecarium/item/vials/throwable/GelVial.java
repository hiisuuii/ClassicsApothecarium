package hisui.apothecarium.item.vials.throwable;

import hisui.apothecarium.block.GelBlock;
import hisui.apothecarium.entity.ThrownGelVialEntity;
import hisui.apothecarium.item.vials.base.VialThrowable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GelVial extends VialThrowable {

    private Block gelBlock;

    public GelVial(Block gelBlock, Settings settings) {
        super(settings);
        this.gelBlock = gelBlock;
    }


    public GelBlock getGelBlock(){
        return (GelBlock)this.gelBlock;
    }

    public int getColor() { return this.getGelBlock().getColor(); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            ThrownGelVialEntity vialEntity = new ThrownGelVialEntity(world, user, this.gelBlock);
            vialEntity.setItem(itemStack);
            vialEntity.setColor(this.getGelBlock().getColor());
            vialEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0f, 0.5f, 1.0f);
            world.spawnEntity(vialEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

}
