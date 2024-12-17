package vip.gothaj.client.values.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.Setting;
import vip.gothaj.client.values.Value;

public class CategoryValue extends Value {

	private List<Value> settings = new ArrayList();
	
	public CategoryValue(Mod parent, String id, String name, Supplier visible) {
		super(parent, id, name, visible, null);
	}
	public CategoryValue(Mod parent, String name, Supplier visible) {
		super(parent, name, name, visible, null);
	}
	public CategoryValue(Mod parent, String name) {
		super(parent, name, name, () -> true, null);
	}
	public CategoryValue(Mod parent, String id, String name) {
		super(parent, id, name, () -> true, null);
	}
	
	public void addSettings(Value...values) {
		settings.addAll(Arrays.asList(values));
	}
	public List<Value> getSettings() {
		return settings;
	}
	public void setSettings(List<Value> settings) {
		this.settings = settings;
	}

}
