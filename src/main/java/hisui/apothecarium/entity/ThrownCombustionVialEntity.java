package hisui.apothecarium.entity;

import hisui.apothecarium.registers.EntityRegister;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrownCombustionVialEntity  extends ThrownVialEntity {
    public ThrownCombustionVialEntity(){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_COMBUSTION_VIAL, null);
    }

    public ThrownCombustionVialEntity(EntityType<? extends ThrownCombustionVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownVialEntity>)entityType, world);
    }

    public ThrownCombustionVialEntity(World world, LivingEntity owner){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_COMBUSTION_VIAL, world, owner);

    }

    public ThrownCombustionVialEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_COMBUSTION_VIAL, world, x, y, z);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {

            // Vial break sound
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 0.7f, 1f, true);

        }
    }

    @Override
    protected void effect(World world, Vec3d pos) {
        if (!world.isClient()) {
            world.createExplosion(this, pos.x, pos.y, pos.z, 2.0f + world.getRandom().nextFloat(), false, World.ExplosionSourceType.TNT);
        }
    }
}
