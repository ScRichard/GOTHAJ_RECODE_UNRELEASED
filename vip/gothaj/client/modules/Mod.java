package vip.gothaj.client.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import vip.gothaj.client.Client;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.ModeValue;

public class Mod {

	public static Minecraft mc = Minecraft.getMinecraft();

	private String name, description, info ="";

	private Bind bind;

	private Category category;

	private List<Value> settings = new ArrayList();

	private boolean enabled;

	public Mod(String name, String description, Bind bind, Category category) {
		this.name = name;
		this.description = description;
		this.bind = bind;
		this.category = category;
	}

	public void onEnable() {}
	public void onDisable() {}
	
	public void addSettings(Value...settings) {
		this.settings.addAll(Arrays.asList(settings));
	}
	
	public Value getSettingByID(String id) {
		return this.settings.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
	}

	public List<Value> getSettings() {
		return settings;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Bind getBind() {
		return bind;
	}

	public void resetBind() {
		bind = null;
	}

	public Category getCategory() {
		return category;
	}

	public void setBind(Bind bind) {
		this.bind = bind;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void toggle() {
		this.enabled = !this.enabled;
		if (enabled) {
			Client.INSTANCE.getEventBus().register(this);
			if(mc.theWorld != null)
				this.onEnable();
			for(Value<?> v : settings) {
				if(v instanceof BooleanValue) {
					
					if(((BooleanValue) v).isEnabled() && v.getSetting() != null) {
						v.getSetting().onEnable();
					}
					
				}
				if(v instanceof ModeValue) {
					if(((ModeValue) v).getActiveMode().getSetting() != null) {
						
						((ModeValue) v).getActiveMode().getSetting().onEnable();
					}
				}
				if(v instanceof CategoryValue) {
					registerCategory((CategoryValue) v);
				}
			}
			
		} else {
			Client.INSTANCE.getEventBus().unregister(this);
			if(mc.theWorld != null) {
				this.onDisable();
			}
			for(Value<?> v : settings) {
				if(v instanceof BooleanValue) {
					
					if(((BooleanValue) v).isEnabled() && v.getSetting() != null) {
						v.getSetting().onDisable();
					}
					
				}
				if(v instanceof ModeValue) {
					if(((ModeValue) v).getActiveMode().getSetting() != null) {
						
						((ModeValue) v).getActiveMode().getSetting().onDisable();
					}
				}
				if(v instanceof CategoryValue) {
					unregisterCategory((CategoryValue) v);
				}
			}
		}
	}
	
	private void registerCategory(CategoryValue value) {
		for(Value<?> v : value.getSettings()) {
			if(v instanceof BooleanValue) {
				
				if(((BooleanValue) v).isEnabled() && v.getSetting() != null) {
					v.getSetting().onEnable();
				}
				
			}
			if(v instanceof ModeValue) {
				if(((ModeValue) v).getActiveMode().getSetting() != null) {
					
					((ModeValue) v).getActiveMode().getSetting().onEnable();
				}
			}
			if(v instanceof CategoryValue) {
				registerCategory((CategoryValue) v);
			}
		}
	}
	private void unregisterCategory(CategoryValue value) {
		for(Value<?> v : value.getSettings()) {
			if(v instanceof BooleanValue) {
				
				if(((BooleanValue) v).isEnabled() && v.getSetting() != null) {
					v.getSetting().onDisable();
				}
				
			}
			if(v instanceof ModeValue) {
				if(((ModeValue) v).getActiveMode().getSetting() != null) {
					
					((ModeValue) v).getActiveMode().getSetting().onDisable();
				}
			}
			if(v instanceof CategoryValue) {
				unregisterCategory((CategoryValue) v);
			}
		}
	}

}
