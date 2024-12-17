package vip.gothaj.client.modules.ext.player;

import vip.gothaj.client.modules.Bind;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.ModeValue;

public class AutoHealModule extends Mod{

	public boolean cancelAura;
	
	public ModeValue mode = new ModeValue(this,"Mode", new Value[] {
			new Value(this, "Silent"), new Value(this, "Legit")
		});

	public AutoHealModule() {
		super("Auto Heal", "Automatically heals you", null, Category.PLAYER);
		// TODO Auto-generated constructor stub
	}

}
