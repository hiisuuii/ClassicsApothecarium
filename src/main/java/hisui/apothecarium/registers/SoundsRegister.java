package hisui.apothecarium.registers;

import hisui.apothecarium.Main;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundsRegister {


    private static SoundEvent register(String id) {
        return register(new Identifier(Main.MODID, id));
    }

    private static SoundEvent register(Identifier id) {
        return register(id, id);
    }

    private static SoundEvent register(Identifier id, Identifier soundId) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    public static void init(){
        Main.LOGGER.info("Initializing Sounds for mod: \"" + Main.MODID + "\"");
    }
}
