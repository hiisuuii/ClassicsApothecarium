package hisui.apothecarium.registers;

import hisui.apothecarium.Main;
import hisui.apothecarium.block.GelBlock;
import hisui.apothecarium.item.vials.base.VialThrowable;
import hisui.apothecarium.item.vials.base.VialUseable;
import hisui.apothecarium.item.vials.throwable.GelVial;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.BlockItem;

public class MiscRegister {

    private static void registerFuels() {
    }

    private static void registerColorProviders(){
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
                        ((GelBlock)state.getBlock()).getColor(),
                BlockRegister.SUN_GEL, BlockRegister.JUMP_GEL, BlockRegister.SPEED_GEL, BlockRegister.STICKY_GEL);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                        ((GelBlock) ((BlockItem)stack.getItem()).getBlock()).getColor(),
                BlockRegister.SUN_GEL.asItem(), BlockRegister.JUMP_GEL.asItem(), BlockRegister.SPEED_GEL.asItem(), BlockRegister.STICKY_GEL.asItem());

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                        tintIndex == 0 ? -1 : ((VialThrowable) (stack.getItem())).getColor(),
                ItemRegister.SUN_VIAL, ItemRegister.SPEED_VIAL, ItemRegister.JUMP_VIAL, ItemRegister.STICKY_VIAL,
                ItemRegister.COMBUSTION_VIAL);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                        tintIndex == 0 ? -1 : ((VialUseable) (stack.getItem())).getColor(),
                ItemRegister.HEAL_VIAL);
    }

    public static void init(){
        registerFuels();
        registerColorProviders();
        Main.LOGGER.info("Initializing miscellaneous registries for mod: \"" + Main.MODID + "\"");
    }
}
