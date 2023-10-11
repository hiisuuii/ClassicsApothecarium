package hisui.apothecarium.entity;

import hisui.apothecarium.registers.EntityRegister;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrownAirburstVialEntity extends ThrownVialEntity {

    public ThrownAirburstVialEntity(){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_AIRBURST_VIAL, null);
    }

    public ThrownAirburstVialEntity(EntityType<? extends ThrownAirburstVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownVialEntity>)entityType, world);
    }

    public ThrownAirburstVialEntity(World world, LivingEntity owner){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_AIRBURST_VIAL, world, owner);

    }

    public ThrownAirburstVialEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_AIRBURST_VIAL, world, x, y, z);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            this.collectEntities(this.getWorld(), this.getPos());

            // Vial break sound
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 0.7f, 1f, true);
            // Fizzle sound
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.NEUTRAL, 0.7f, 1f, true);
            // Smoke
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), random.nextGaussian() * 0.05, random.nextDouble() * 0.1, random.nextGaussian() * 0.05);
            }
            // Entity Smoke
            for(Entity e : this.getAffectedEntities()){
                if(e.equals(this.getOwner())) { continue; }
                Vec3d velocity = e.getVelocity();
                for (int j = 0; j < 8; ++j) {
                    this.getWorld().addParticle(ParticleTypes.POOF, e.getX(), e.getY()+0.3, e.getZ(), velocity.x, velocity.y, velocity.z);
                }
            }
        }
    }

    @Override
    protected void effect(World world, Vec3d pos) {
        this.power *= 2;

        this.collectEntities(world, pos);
        this.collectBlocks(world, pos);

        if(!world.isClient()) {
            for (Entity entity : this.getAffectedEntities()) {
                if (entity instanceof LivingEntity) {
                    Vec3d motion = entity.getPos().subtract(pos).normalize().multiply(2);

                    entity.addVelocity(motion);
                    entity.setFireTicks(0);
                    entity.setOnFire(false);
                }
            }
            for(BlockPos blockPos : this.getAffectedBlocks()) {
                if(world.getBlockState(blockPos).getBlock() instanceof AbstractFireBlock) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                }
            }
        }

    }
}
