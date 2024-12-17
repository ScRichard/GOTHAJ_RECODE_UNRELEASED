package vip.gothaj.client.values;

import java.util.function.Supplier;

import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Mod;

public class Value<T> {

	private String name, id;
	
	public Mod parent;
	
	public Setting setting;
	
	private Supplier<Boolean> isVisible = () -> true;

	
	public Value(Mod parent, String id, String name, Supplier<Boolean> visible, Setting setting) {
		this.id = id;
		this.name = name;
		this.setting = setting;
		this.isVisible = visible;
		this.parent = parent;
	}
	public Value(Mod parent, String name, Setting setting) {
		this.id = name;
		this.name = name;
		this.setting = setting;
		this.parent = parent;
	}
	public Value(Mod parent, String name) {
		this.id = name;
		this.name = name;
		this.parent = parent;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Mod getParent() {
		return parent;
	}


	public void setParent(Mod parent) {
		this.parent = parent;
	}


	public Setting getSetting() {
		return setting;
	}


	public void setSetting(Setting setting) {
		this.setting = setting;
	}


	public Supplier<Boolean> getVisibility() {
		return isVisible;
	}


	public void setIsVisible(Supplier<Boolean> isVisible) {
		this.isVisible = isVisible;
	}
}
