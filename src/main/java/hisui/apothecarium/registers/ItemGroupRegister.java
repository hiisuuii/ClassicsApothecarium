package hisui.apothecarium.registers;

import hisui.apothecarium.Main;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroupRegister {

    public static final ItemGroup ITEMGROUP_APOTHECARIUM = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.GLASS_BOTTLE))
            .displayName(Text.translatable("itemgroup.apothecarium.name"))
            .entries((displayContext, entries) ->
            {
                for(Item item : ItemRegister.creativeTabBlockItems) {
                    if (ItemRegister.excludeTabItems.contains(item)) {
                        continue;
                    }
                    entries.add(item);
                }
                for(Item item : ItemRegister.creativeTabItems) {
                    if (ItemRegister.excludeTabItems.contains(item)) {
                        continue;
                    }
                    entries.add(item);
                }
            })
            .build();


    public static void init(){
        Registry.register(Registries.ITEM_GROUP, new Identifier(Main.MODID, "itemgroup_apothecarium"), ITEMGROUP_APOTHECARIUM);
        Main.LOGGER.info("Initializing ItemGroup for mod: \"" + Main.MODID + "\"");
    }
}
