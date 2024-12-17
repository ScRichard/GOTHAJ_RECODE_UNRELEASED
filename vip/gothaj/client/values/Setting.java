package vip.gothaj.client.values;

import net.minecraft.client.Minecraft;
import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Mod;

public class Setting<T> {
	
	protected T parent;

	public static Minecraft mc = Minecraft.getMinecraft();
	
	public Setting(T parent) {

		this.parent = parent;
	}

	public T getParent() {
		return parent;
	}

	public void onEnable() {
		Client.INSTANCE.getEventBus().register(this);
	}
	public void onDisable() {
		Client.INSTANCE.getEventBus().unregister(this);
	}
	
}
