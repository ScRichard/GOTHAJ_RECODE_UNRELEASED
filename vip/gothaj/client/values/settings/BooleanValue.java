package vip.gothaj.client.values.settings;

import java.util.function.Supplier;

import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.client.ChatUtils;
import vip.gothaj.client.values.Setting;
import vip.gothaj.client.values.Value;

public class BooleanValue extends Value {

	private boolean enabled;
	
	public BooleanValue(Mod parent, String id, String name, boolean enabled, Supplier<Boolean> visible,Setting setting) {
		super(parent, id, name,visible , setting);
		this.enabled = enabled;
	}
	public BooleanValue(Mod parent, String name, boolean enabled, Supplier<Boolean> visible,Setting setting) {
		super(parent, name, name,visible , setting);
		this.enabled = enabled;
	}
	public BooleanValue(Mod parent, String name, boolean enabled, Setting setting) {
		super(parent, name, name, () -> true, setting);
		this.enabled = enabled;
	}
	
	public BooleanValue(Mod parent, String id, String name, boolean enabled, Setting setting) {
		super(parent, id, name, () -> true, setting);
		this.enabled = enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		if(parent.isEnabled() && setting != null) {
			if(enabled) {
				this.setting.onEnable();
			}
			else {
				this.setting.onDisable();
			}
		}
	}

}
