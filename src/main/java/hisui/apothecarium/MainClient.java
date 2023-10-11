package hisui.apothecarium;

import hisui.apothecarium.client.ClientRendererRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientRendererRegister.init();
    }
}
