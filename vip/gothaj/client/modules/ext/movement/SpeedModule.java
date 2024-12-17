package vip.gothaj.client.modules.ext.movement;

import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.EventType;
import vip.gothaj.client.event.events.EventJump;
import vip.gothaj.client.event.events.EventMotion;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.move.MovementUtils;

public class SpeedModule extends Mod{
	public SpeedModule() {
		super("Speed", "Allows you to walk faster", null, Category.MOVEMENT);
	}
	
	@EventListener
	public void onMotion(EventMotion e) {

	}
	@EventListener
	public void onJump(EventJump e) {
	    
	}
}
