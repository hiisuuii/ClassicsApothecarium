package hisui.apothecarium.entity;

import com.google.common.collect.Sets;
import hisui.apothecarium.registers.EntityRegister;
import hisui.apothecarium.registers.ItemRegister;
import io.netty.util.internal.ThreadLocalRandom;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class ThrownVialEntity extends ThrownItemEntity {
    protected ObjectArrayList<BlockPos> affectedBlocks = new ObjectArrayList<>();
    protected ObjectArrayList<Entity> affectedEntities = new ObjectArrayList<>();
    protected int color = 0xFFFFFF;
    protected float power = 1.2f;

    private static final TrackedData<Integer> COLOR = DataTracker.registerData(ThrownVialEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ThrownVialEntity(){
        super((EntityType<? extends ThrownItemEntity>) EntityRegister.THROWN_BASE_VIAL, null);
    }

    public ThrownVialEntity(EntityType<? extends ThrownVialEntity> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>)entityType, world);
        this.power += (world.random.nextFloat() * 0.4f);
    }

    public ThrownVialEntity(EntityType<? extends ThrownVialEntity> entityType, World world, LivingEntity owner) {
        super((EntityType<? extends ThrownItemEntity>)entityType, owner, world);
        this.setOwner(owner);
        this.power += (world.random.nextFloat() * 0.4f);
    }

    public ThrownVialEntity(EntityType<? extends ThrownVialEntity> entityType, World world, double x, double y, double z) {
        super((EntityType<? extends ThrownItemEntity>)entityType, x, y, z, world);
        this.power += (world.random.nextFloat() * 0.4f);
    }

    // Override me!
    @Deprecated
    protected void effect(World world, Vec3d pos){

    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(this.getParticleParameters(), this.getX(), this.getY(), this.getZ(), random.nextGaussian() * 0.05, random.nextDouble() * 0.1, random.nextGaussian() * 0.05);
            }
        }

    }

    @Deprecated
    public int getColor() {
        return this.getDataTracker().get(COLOR);
    }

    public void setColor(int color) {
        this.getDataTracker().set(COLOR, color);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(COLOR, 0xFF00FF);
    }


    protected void collectBlocks(World world, Vec3d pos){
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;
        int l;
        int k;
        world.emitGameEvent((Entity)this, GameEvent.PROJECTILE_LAND, new Vec3d(x, y, z));

        HashSet<BlockPos> set = Sets.newHashSet();
        int i = 16;
        for (int j = 0; j < i; ++j) {
            for (k = 0; k < i; ++k) {
                block2: for (l = 0; l < i; ++l) {
                    if (j != 0 && j != i-1 && k != 0 && k != i-1 && l != 0 && l != i-1) continue;
                    double d = (float)j / 15.0f * 2.0f - 1.0f;
                    double e = (float)k / 15.0f * 2.0f - 1.0f;
                    double f = (float)l / 15.0f * 2.0f - 1.0f;
                    double g = Math.sqrt(d * d + e * e + f * f);
                    d /= g;
                    e /= g;
                    f /= g;
                    double m = x;
                    double n = y;
                    double o = z;
                    for (float h = this.power * (0.7f + world.random.nextFloat() * 0.7f); h > 0.0f; h -= 0.22500001f) {
                        BlockPos blockPos = BlockPos.ofFloored(m, n, o);
                        if (!world.isInBuildLimit(blockPos)) continue block2;
                        if(ThreadLocalRandom.current().nextDouble() > 0.4) {
                            set.add(blockPos);
                        }
                        m += d * (double)0.3f;
                        n += e * (double)0.3f;
                        o += f * (double)0.3f;
                    }
                }
            }
        }
        this.affectedBlocks.addAll(set);
    }
    protected void collectEntities(World world, Vec3d pos){
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;
        float q = this.power * 2.0f;
        int k = MathHelper.floor(x - (double)q - 1.0);
        int l = MathHelper.floor(x + (double)q + 1.0);
        int r = MathHelper.floor(y - (double)q - 1.0);
        int s = MathHelper.floor(y + (double)q + 1.0);
        int t = MathHelper.floor(z - (double)q - 1.0);
        int u = MathHelper.floor(z + (double)q + 1.0);

        List<Entity> list = world.getOtherEntities(this, new Box(k, r, t, l, s, u), entity -> entity instanceof LivingEntity && !entity.equals(this.getOwner()));
        list.remove(this.getOwner());
        Vec3d vec3d = new Vec3d(x, y, z);
        for (Entity entity : list) {
            double z1;
            double y1;
            double x1;
            double aa;
            double w;
            if (!((w = Math.sqrt(entity.squaredDistanceTo(vec3d)) / (double) q) <= 1.0) || (aa = Math.sqrt((x1 = entity.getX() - x) * x1 + (y1 = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - y) * y1 + (z1 = entity.getZ() - z) * z1)) == 0.0)
                continue;
            this.affectedEntities.add(entity);
        }
    }

    public List<BlockPos> getAffectedBlocks() {
        return affectedBlocks;
    }
    public List<Entity> getAffectedEntities() {return affectedEntities; }
    protected ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    protected void affectWorld(World world, Vec3d pos) {
        this.collectBlocks(world, pos);
        this.collectEntities(world, pos);
        effect(world, pos);
    }

    @Override
    protected float getGravity() {
        return 0.05f;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegister.EMPTY_VIAL;
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return (LivingEntity) super.getOwner();
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.affectWorld(this.getWorld(), hitResult.getPos());
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

}
