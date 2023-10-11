package hisui.apothecarium.entity;

import hisui.apothecarium.Main;
import hisui.apothecarium.block.GelBlock;
import hisui.apothecarium.registers.EntityRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class ThrownGelVialEntity extends ThrownVialEntity {


    private Block gelBlock = Blocks.AIR;

    public ThrownGelVialEntity(){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_GEL_VIAL, null);
    }

    public ThrownGelVialEntity(@Nullable Block block){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_GEL_VIAL, null);
        this.gelBlock = block != null ? block : Blocks.AIR;
    }

    public ThrownGelVialEntity(EntityType<? extends ThrownGelVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownVialEntity>)entityType, world);
    }

    public ThrownGelVialEntity(World world, LivingEntity owner){
        super((EntityType<? extends ThrownVialEntity>) EntityRegister.THROWN_GEL_VIAL, world, owner);

    }

    public ThrownGelVialEntity(World world, LivingEntity owner, Block block) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_GEL_VIAL, world, owner);
        this.gelBlock = block;
    }

    public ThrownGelVialEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownVialEntity>)EntityRegister.THROWN_GEL_VIAL, world, x, y, z);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        this.getWorld().syncWorldEvent(WorldEvents.INSTANT_SPLASH_POTION_SPLASHED, this.getBlockPos(), this.getColor());
    }

    @Override
    protected void effect(World world, Vec3d pos) {
        if(this.getGelBlock() instanceof GelBlock) {
            this.collectBlocks(world, pos);
            for (BlockPos bpos : this.affectedBlocks) {
                BlockState blockState = world.getBlockState(bpos);
                if (Block.isFaceFullSquare(blockState.getCollisionShape(world, bpos), Direction.UP) && world.getBlockState(bpos.up()).isReplaceable() && getGelBlock().getDefaultState().canPlaceAt(world, bpos.up())) {
                    world.setBlockState(bpos.up(), getGelBlock().getDefaultState());
                }
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (!this.getWorld().isClient) {
            this.affectWorld(this.getWorld(), hitResult.getPos());
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

    public Block getGelBlock() {
        return gelBlock;
    }
}
