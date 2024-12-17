package vip.gothaj.client.values.settings;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.Setting;
import vip.gothaj.client.values.Value;

public class ModeValue extends Value {

	private List<Value> modes;
	
	private Value active;
	
	public ModeValue(Mod parent, String id, String name, Value[] modes, Supplier visible) {
		super(parent, id, name, visible, null);
		this.modes = Arrays.asList(modes);
		
		setMode(this.modes.get(0));
	}
	public ModeValue(Mod parent, String name, Value[] modes, Supplier visible) {
		super(parent, name, name, visible, null);
		this.modes = Arrays.asList(modes);
		setMode(this.modes.get(0));
	}
	public ModeValue(Mod parent, String id, String name, Value[] modes) {
		super(parent, id, name, () -> true, null);
		this.modes = Arrays.asList(modes);
		setMode(this.modes.get(0));
	}
	public ModeValue(Mod parent, String name, Value[] modes) {
		super(parent, name, name, () -> true, null);
		this.modes = Arrays.asList(modes);
		setMode(this.modes.get(0));
	}
	public List<Value> getModes() {
		return modes;
	}
	public void setModes(List<Value> modes) {
		this.modes = modes;
	}
	
	public Value getMode(String name) {
		return modes.stream().filter(m -> m.getName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
	}

	public Value getActiveMode() {
		return active;
	}
	
	public String getMode() {
		return active.getName();
	}
	public boolean isMode(String st) {
		return active.getName().toLowerCase().equals(st.toLowerCase());
	}
	
	public void setMode(Value mode) {
		if(active != null && active.getSetting() != null) {
			active.getSetting().onDisable();
		}
		active = mode;
		if(parent.isEnabled() && active.getSetting() != null) {
			active.getSetting().onEnable();
		}
	}
}
