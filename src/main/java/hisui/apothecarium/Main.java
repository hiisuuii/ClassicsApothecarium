package hisui.apothecarium;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final String MODID = "apothecarium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);


	static {
		// Register Screen Handlers Here
	}

	@Override
	public void onInitialize() {

		hisui.apothecarium.registers.ItemRegister.init();
		hisui.apothecarium.registers.BlockEntityRegister.init();
		hisui.apothecarium.registers.BlockRegister.init();
		hisui.apothecarium.registers.EntityRegister.init();
		hisui.apothecarium.registers.ItemGroupRegister.init();
		hisui.apothecarium.registers.SoundsRegister.init();
		hisui.apothecarium.registers.MiscRegister.init();
	}
}