package hisui.apothecarium.entity;

import hisui.apothecarium.registers.EntityRegister;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ThrownFlameburstVialEntity extends ThrownVialEntity {

    public ThrownFlameburstVialEntity(){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_FLAMEBURST_VIAL, null);
    }

    public ThrownFlameburstVialEntity(EntityType<? extends ThrownFlameburstVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownVialEntity>)entityType, world);
    }

    public ThrownFlameburstVialEntity(World world, LivingEntity owner){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_FLAMEBURST_VIAL, world, owner);

    }

    public ThrownFlameburstVialEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_FLAMEBURST_VIAL, world, x, y, z);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {

            // Vial break sound
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 0.7f, 1f, true);
            for (int i = 0; i < 16; ++i) {
                this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), random.nextGaussian() * 0.25, random.nextDouble() * 0.4, random.nextGaussian() * 0.25);
            }
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 1f, 1f, true);

        }
    }

    @Override
    protected void effect(World world, Vec3d pos) {

        this.power /= 1.3;

        this.collectBlocks(world, pos);

        if (!world.isClient()) {
            for (BlockPos bpos : this.affectedBlocks) {
                BlockPos blockPos2 = bpos.offset(Direction.UP);
                BlockState blockState1 = world.getBlockState(blockPos2);
                if (world.getRandom().nextDouble() > 0.5 && (AbstractFireBlock.canPlaceAt(world, blockPos2, Direction.Type.HORIZONTAL.random(world.random))
                        || blockState1.getBlock().equals(Blocks.ICE) || blockState1.getBlock().equals(Blocks.SNOW))) {
                    BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                    world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                    world.emitGameEvent(null, GameEvent.BLOCK_PLACE, bpos);

                }
            }
        }
    }
}
