package hisui.apothecarium.entity;

import hisui.apothecarium.registers.EntityRegister;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.joml.Vector3d;

public class ThrownSlimeCoreVialEntity  extends ThrownVialEntity {

    public ThrownSlimeCoreVialEntity(){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_SLIME_VIAL, null);
    }

    public ThrownSlimeCoreVialEntity(EntityType<? extends ThrownSlimeCoreVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownVialEntity>)entityType, world);
    }

    public ThrownSlimeCoreVialEntity(World world, LivingEntity owner){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_SLIME_VIAL, world, owner);

    }

    public ThrownSlimeCoreVialEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_SLIME_VIAL, world, x, y, z);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {

            // Vial break sound
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 0.7f, 1f, true);
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(ParticleTypes.ITEM_SLIME, this.getX(), this.getY(), this.getZ(), random.nextGaussian() * 0.05, random.nextDouble() * 0.1, random.nextGaussian() * 0.05);
            }
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.NEUTRAL, 1f, 1f, true);

        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(!this.getWorld().isClient()) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof SlimeEntity) {
                ((SlimeEntity) entity).setSize(((SlimeEntity) entity).getSize() + 1, true);
            } else {
                super.onEntityHit(entityHitResult);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if(!this.getWorld().isClient()) {
            super.onBlockHit(blockHitResult);
            Vec3d pos = blockHitResult.getBlockPos().offset(Direction.UP, 1).toCenterPos();//blockHitResult.getPos();

            SlimeEntity slime = new SlimeEntity(EntityType.SLIME, this.getWorld());
            slime.setSize(1, true);
            slime.setPos(pos.x, pos.y, pos.z);
            this.getWorld().spawnEntity(slime);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Emitter.of(this, null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            this.onBlockHit(blockHitResult);
            BlockPos blockPos = blockHitResult.getBlockPos();
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.getWorld().getBlockState(blockPos)));
        }
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}
