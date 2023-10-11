package hisui.apothecarium.entity;

import hisui.apothecarium.registers.EntityRegister;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ThrownFrostburstVialEntity extends ThrownVialEntity {

    public ThrownFrostburstVialEntity(){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_FROSTBURST_VIAL, null);
    }

    public ThrownFrostburstVialEntity(EntityType<? extends ThrownFrostburstVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownVialEntity>)entityType, world);
    }

    public ThrownFrostburstVialEntity(World world, LivingEntity owner){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_FROSTBURST_VIAL, world, owner);

    }

    public ThrownFrostburstVialEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_FROSTBURST_VIAL, world, x, y, z);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {

            // Vial break sound
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 0.7f, 1f, true);
            for (int i = 0; i < 16; ++i) {
                this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), random.nextGaussian() * 0.25, random.nextDouble() * 0.4, random.nextGaussian() * 0.25);
            }
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_HURT_FREEZE, SoundCategory.NEUTRAL, 1f, 1f, true);

        }
    }


    private boolean canFreeze( WorldView world, BlockPos pos) {
        BlockState blockState1 = world.getBlockState(pos);
        if(blockState1.getFluidState().getFluid() instanceof WaterFluid || blockState1.getFluidState().getFluid() instanceof LavaFluid) {
            return true;
        }
        BlockState blockState2 = world.getBlockState(pos.down());
        if(blockState1.getBlock() instanceof AbstractFireBlock){
            return true;
        }
        if(!blockState1.isReplaceable()){
            return false;
        }
        if (blockState2.isIn(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
            return false;
        }
        if (blockState2.isIn(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)) {
            return true;
        }
        return Block.isFaceFullSquare(blockState2.getCollisionShape(world, pos.down()), Direction.UP);
    }

    @Override
    protected void effect(World world, Vec3d pos) {

        this.power /= 1.3;

        this.collectBlocks(world, pos);

        if (!world.isClient()) {
            for (BlockPos bpos : this.affectedBlocks) {
                BlockState blockState1 = world.getBlockState(bpos);
                if (canFreeze(world, bpos)) {
                    if(blockState1.getFluidState().getFluid() instanceof WaterFluid) {
                        world.setBlockState(bpos, Blocks.ICE.getDefaultState(), Block.NOTIFY_ALL);
                    } else if(blockState1.getFluidState().getFluid() instanceof LavaFluid) {
                        world.setBlockState(bpos, Blocks.OBSIDIAN.getDefaultState(), Block.NOTIFY_ALL);
                    } else if(blockState1.getBlock() instanceof AbstractFireBlock) {
                        world.setBlockState(bpos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                    }else if(blockState1.getFluidState().isEmpty()){
                        world.setBlockState(bpos, Blocks.SNOW.getDefaultState(), Block.NOTIFY_ALL);
                    }

                }
            }
        }
    }
}
