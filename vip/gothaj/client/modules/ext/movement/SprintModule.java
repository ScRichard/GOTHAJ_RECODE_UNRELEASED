package vip.gothaj.client.modules.ext.movement;

import org.lwjgl.input.Keyboard;

import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventUpdate;
import vip.gothaj.client.modules.Bind;
import vip.gothaj.client.modules.Bind.Type;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;

public class SprintModule extends Mod{

	public SprintModule() {
		super("Sprint", "Toggles sprint on player", null, Category.MOVEMENT);
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		mc.thePlayer.setSprinting(false);
	}
	
	@EventListener
	public void onUpdate(EventUpdate e) {
		
		if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0) {
			mc.thePlayer.setSprinting(true);
		}else mc.thePlayer.setSprinting(false);

		
	}
}
