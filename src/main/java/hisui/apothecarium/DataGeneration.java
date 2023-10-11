package hisui.apothecarium;

import hisui.apothecarium.registers.BlockRegister;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class DataGeneration  implements DataGeneratorEntrypoint {

    private static List<Block> excludeBlocks = Arrays.asList(null);
    private static List<Item> excludeItems = Arrays.asList(null);


    private static class ModModelGenerator extends FabricModelProvider {
        private ModModelGenerator(FabricDataOutput generator){
            super(generator);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator){
            for(RegistryKey<Block> key : Registries.BLOCK.getKeys()){
                if(key.getValue().getNamespace().equals(Main.MODID)){
                    if(!excludeBlocks.isEmpty() && excludeBlocks.contains(Registries.BLOCK.get(key))) { continue; }
                    Main.LOGGER.info("Generating Block model for block: " + key.getValue().getNamespace() + ":" + key.getValue().getPath());
                    blockStateModelGenerator.registerSimpleCubeAll(Registries.BLOCK.get(key));
                }
            }
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator){
            for(RegistryKey<Item> key : Registries.ITEM.getKeys()){
                if(key.getValue().getNamespace().equals(Main.MODID)){
                    Identifier identifier = key.getValue();
                    Item i = Registries.ITEM.get(key);
                    if(!excludeItems.isEmpty() && excludeItems.contains(i)) { continue; }
                    if(Registries.BLOCK.containsId(identifier)) { continue; }
                    Main.LOGGER.info("Generating Item model for item: " + key.getValue().getNamespace() + ":" + key.getValue().getPath());
                    if(i instanceof ToolItem){
                        itemModelGenerator.register(i, Models.HANDHELD);
                    } else {
                        itemModelGenerator.register(i, Models.GENERATED);
                    }
                }
            }
        }
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(ModModelGenerator::new);
    }
}
