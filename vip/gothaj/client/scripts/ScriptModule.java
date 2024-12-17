package vip.gothaj.client.scripts;

import vip.gothaj.client.event.Event;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventMotion;
import vip.gothaj.client.event.events.EventRenderGui;
import vip.gothaj.client.event.events.EventUpdate;
import vip.gothaj.client.modules.Mod;

public class ScriptModule extends Mod{
	private Script script;

	public ScriptModule(Script script) {
		super(script.getName(), script.getDescription(), null, script.getCategory());
		this.script = script;
	}
	
	public void onEnable() {
		script.callEvent("enable", new Event[] {});
	}
	
	public void onDisable() {
		script.callEvent("disable", new Event[] {});
	}
	
	@EventListener
	public void onUpdate(EventUpdate e) {
		script.callEvent("update", new Event[] {});
	}
	
	@EventListener
	public void onUpdate(EventRenderGui e) {
		script.callEvent("render2d", new Event[] {});
	}
	
	@EventListener
	public void onMotion(EventMotion e) {
		/*
		if(e.getType() == EventType.PRE)
			script.callEvent("premotion", new Event[] {});
		else
			script.callEvent("postmotion", new Event[] {});*/
	}

}
