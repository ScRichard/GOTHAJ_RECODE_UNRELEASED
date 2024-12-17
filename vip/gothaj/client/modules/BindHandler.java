package vip.gothaj.client.modules;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Bind.Ac;
import vip.gothaj.client.modules.Bind.Type;
import vip.gothaj.client.utils.client.ChatUtils;

public class BindHandler {
	
	public static void handleKey(int key, Ac keyboard) {
		for(Mod m : Client.INSTANCE.getModuleManager().getModules()) {
			if(m.getBind() == null) continue;

			if(m.getBind().getKey() == key && m.getBind().getAc().name().equals(keyboard.name())) {
				m.toggle();
			}
		}
	}
	
	public static void checkHolding() {
		for(Mod m : Client.INSTANCE.getModuleManager().getModules()) {
			if(m.getBind() == null || m.getBind().getType() != Type.HOLD) continue;
			
			if(!(Mouse.isButtonDown(m.getBind().getKey()) || Keyboard.isKeyDown(m.getBind().getKey())) && m.isEnabled()) {
				m.toggle();
			}
		}
	}
}
