package vip.gothaj.client.values.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.client.Tuple;
import vip.gothaj.client.values.Value;

public class MultipleBooleanValue extends Value {

	private ArrayList<Tuple<String, Boolean>> values = new ArrayList();
	
	public MultipleBooleanValue(Mod parent, String id, String name, Tuple<String, Boolean>[] values, Supplier visible) {
		super(parent, id, name, visible, null);
		this.values.addAll(Arrays.asList(values));
	}
	
	public MultipleBooleanValue(Mod parent,  String name, Tuple<String, Boolean>[] values, Supplier visible) {
		super(parent, name, name, visible, null);
		this.values.addAll(Arrays.asList(values));
	}
	
	public MultipleBooleanValue(Mod parent, String name, Tuple<String, Boolean>[] values) {
		super(parent, name, name, () -> true, null);
		this.values.addAll(Arrays.asList(values));
	}
	
	public MultipleBooleanValue(Mod parent, String id, String name, Tuple<String, Boolean>[] values) {
		super(parent, id, name, () -> true, null);
		this.values.addAll(Arrays.asList(values));
	}
	
	public ArrayList<Tuple<String, Boolean>> getValues() {
		return values;
	}
	
	public boolean get(String name) {
		return values.stream().filter(v -> v.getFirst().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null).getSecond();
	}
	
	public List<Tuple<String, Boolean>> getEnabledValues() {
		return values.stream().filter(v -> v.getSecond()).collect(Collectors.toList());
	}
	
	public void setValues(ArrayList<Tuple<String, Boolean>> values) {
		this.values = values;
	}

}
