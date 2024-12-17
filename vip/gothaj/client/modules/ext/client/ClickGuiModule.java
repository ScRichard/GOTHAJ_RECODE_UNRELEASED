package vip.gothaj.client.modules.ext.client;

import org.lwjgl.input.Keyboard;

import vip.gothaj.client.modules.Bind;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.modules.Bind.Ac;
import vip.gothaj.client.ui.clickguis.Panel.ClickGui;


public class ClickGuiModule extends Mod {

	public ClickGuiModule() {
		super("Click Gui", "Shows you a screen with modules", new Bind(Keyboard.KEY_RSHIFT, Ac.Keyboard), Category.CLIENT);
	}

	public void onEnable() {
		this.mc.displayGuiScreen(new ClickGui());
		this.toggle();
	}
	
}
