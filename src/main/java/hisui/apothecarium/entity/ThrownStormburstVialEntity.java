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

public class ThrownStormburstVialEntity extends ThrownVialEntity {
    public ThrownStormburstVialEntity(){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_STORMBURST_VIAL, null);
    }

    public ThrownStormburstVialEntity(EntityType<? extends ThrownStormburstVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownVialEntity>)entityType, world);
    }

    public ThrownStormburstVialEntity(World world, LivingEntity owner){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_STORMBURST_VIAL, world, owner);

    }

    public ThrownStormburstVialEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_STORMBURST_VIAL, world, x, y, z);
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
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightningEntity.setPosition(pos);
            world.spawnEntity(lightningEntity);
        }
    }
}
