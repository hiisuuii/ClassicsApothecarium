package hisui.apothecarium.registers;

import hisui.apothecarium.Main;
import hisui.apothecarium.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityRegister {

    public static final EntityType<ThrownGelVialEntity> THROWN_GEL_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_gel"),
            FabricEntityTypeBuilder.<ThrownGelVialEntity>create(SpawnGroup.MISC, ThrownGelVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());
    public static final EntityType<ThrownVialEntity> THROWN_BASE_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_base"),
            FabricEntityTypeBuilder.<ThrownVialEntity>create(SpawnGroup.MISC, ThrownVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());
    public static final EntityType<ThrownAirburstVialEntity> THROWN_AIRBURST_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_airburst"),
            FabricEntityTypeBuilder.<ThrownAirburstVialEntity>create(SpawnGroup.MISC, ThrownAirburstVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());
    public static final EntityType<ThrownStormburstVialEntity> THROWN_STORMBURST_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_stormburst"),
            FabricEntityTypeBuilder.<ThrownStormburstVialEntity>create(SpawnGroup.MISC, ThrownStormburstVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());
    public static final EntityType<ThrownCombustionVialEntity> THROWN_COMBUSTION_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_combustion"),
            FabricEntityTypeBuilder.<ThrownCombustionVialEntity>create(SpawnGroup.MISC, ThrownCombustionVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());
    public static final EntityType<ThrownFlameburstVialEntity> THROWN_FLAMEBURST_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_flameburst"),
            FabricEntityTypeBuilder.<ThrownFlameburstVialEntity>create(SpawnGroup.MISC, ThrownFlameburstVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());
    public static final EntityType<ThrownFrostburstVialEntity> THROWN_FROSTBURST_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_frostburst"),
            FabricEntityTypeBuilder.<ThrownFrostburstVialEntity>create(SpawnGroup.MISC, ThrownFrostburstVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());
    public static final EntityType<ThrownSlimeCoreVialEntity> THROWN_SLIME_VIAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MODID, "vial_slime_core"),
            FabricEntityTypeBuilder.<ThrownSlimeCoreVialEntity>create(SpawnGroup.MISC, ThrownSlimeCoreVialEntity::new).dimensions(new EntityDimensions(0.25f,0.25f, true))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());



    public static void registerAttributes() {
        // Use FabricDefaultAttributeRegistry here
    }

    public static void init(){
        registerAttributes();
        Main.LOGGER.info("Initializing Entities for mod: \"" + Main.MODID + "\"");
    }

}
