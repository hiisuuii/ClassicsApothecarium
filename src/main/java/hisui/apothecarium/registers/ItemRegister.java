package hisui.apothecarium.registers;

import hisui.apothecarium.Main;
import hisui.apothecarium.item.vials.base.VialThrowable;
import hisui.apothecarium.item.vials.base.VialUseable;
import hisui.apothecarium.item.vials.throwable.*;
import hisui.apothecarium.item.vials.usable.HealVial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;


public class ItemRegister {

    public static List<Item> creativeTabItems = new ArrayList<>();
    public static List<Item> creativeTabBlockItems = new ArrayList<>();
    public static List<Item> excludeTabItems = new ArrayList<>();

    public static final Item EMPTY_VIAL = register("empty_vial", new VialThrowable(new FabricItemSettings()));

    public static final Item SUN_VIAL = register("sun_vial", new GelVial(BlockRegister.SUN_GEL, new FabricItemSettings()));
    public static final Item SPEED_VIAL = register("speed_vial", new GelVial(BlockRegister.SPEED_GEL, new FabricItemSettings()));
    public static final Item JUMP_VIAL = register("jump_vial", new GelVial(BlockRegister.JUMP_GEL, new FabricItemSettings()));
    public static final Item STICKY_VIAL = register("sticky_vial", new GelVial(BlockRegister.STICKY_GEL, new FabricItemSettings()));

    public static final Item AIRBURST_VIAL = register("airburst_vial", new AirburstVial(new FabricItemSettings()));
    public static final Item STORMBURST_VIAL = register("stormburst_vial", new StormburstVial(new FabricItemSettings()));
    public static final Item COMBUSTION_VIAL = register("combustion_vial", new CombustionVial(new FabricItemSettings(), 0xC68100));
    public static final Item FLAMEBURST_VIAL = register("flameburst_vial", new FlameburstVial(new FabricItemSettings()));
    public static final Item FROSTBURST_VIAL = register("frostburst_vial", new FrostburstVial(new FabricItemSettings()));
    public static final Item SLIME_CORE_VIAL = register("slime_core_vial", new SlimeCoreVial(new FabricItemSettings()));
    public static final Item HEAL_VIAL = register("heal_vial", new HealVial(new FabricItemSettings(), 0xC10000));

    private static Item register(String id, Item item) {
        if(!(item instanceof SpawnEggItem) && !(creativeTabItems.contains(item))) {
            creativeTabItems.add(item);
        }
        return register(new Identifier(Main.MODID, id), item);
    }

    private static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void init(){
        Main.LOGGER.info("Initializing Items for mod: \"" + Main.MODID + "\"");
    }
}
