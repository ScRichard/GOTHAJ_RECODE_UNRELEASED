package vip.gothaj.client.modules.ext.movement;

import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.modules.ext.movement.flys.BlocksMCFly;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.ModeValue;

public class FlightModule extends Mod {
	
	private ModeValue mode = new ModeValue(this, "Mode", new Value[] {
			new Value(this, "BlocksMC", new BlocksMCFly(this))
	});

	public FlightModule() {
		super("Flight", "Allows you flying without creative mode", null, Category.MOVEMENT);
		this.addSettings(mode);
	}

}
