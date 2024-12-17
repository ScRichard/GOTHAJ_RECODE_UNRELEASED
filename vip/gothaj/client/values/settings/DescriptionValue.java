package vip.gothaj.client.values.settings;

import java.util.function.Supplier;

import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.Setting;
import vip.gothaj.client.values.Value;

public class DescriptionValue extends Value{

	public DescriptionValue(Mod parent, String name, Supplier visible) {
		super(parent, name, name, visible, null);
	}
	public DescriptionValue(Mod parent, String name) {
		super(parent, name, name, () -> true, null);
	}

}
