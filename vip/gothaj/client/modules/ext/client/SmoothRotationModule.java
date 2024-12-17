package vip.gothaj.client.modules.ext.client;

import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.settings.BooleanValue;

public class SmoothRotationModule extends Mod{

	public BooleanValue ka = new BooleanValue(this, "Kill Aura", true, null);

	public SmoothRotationModule() {
		super("Smooth Rotations", "Making smooth movement for the legit view", null, Category.CLIENT);
	}

}
