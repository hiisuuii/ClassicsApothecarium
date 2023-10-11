package hisui.apothecarium.registers;

import hisui.apothecarium.Main;
import hisui.apothecarium.block.GelBlock;
import hisui.apothecarium.block.gels.BounceGel;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

public class BlockRegister {

    public static final Block SUN_GEL = register("sun_gel", new GelBlock(FabricBlockSettings.create().luminance(15), 0xFFCC00));
    public static final Block SPEED_GEL = register("speed_gel", new GelBlock(FabricBlockSettings.create().velocityMultiplier(1.2f).slipperiness(0.98f), 0xFF6A00));
    public static final Block JUMP_GEL = register("jump_gel", new BounceGel(FabricBlockSettings.create(), 0x0094FF));
    public static final Block STICKY_GEL = register("sticky_gel", new GelBlock(FabricBlockSettings.create().velocityMultiplier(0.1f).jumpVelocityMultiplier(0.3f), 0x92CC92));


    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    private static Block register(String id, Block block) {
        return register(new Identifier(Main.MODID, id), block);
    }

    private static Block register(Identifier id, Block block) {
        Item blockItem = Registry.register(Registries.ITEM, id, new BlockItem(block, new FabricItemSettings()));
        ItemRegister.creativeTabBlockItems.add(blockItem);
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void init(){
        Main.LOGGER.info("Initializing Blocks for mod: \"" + Main.MODID + "\"");
    }
}
