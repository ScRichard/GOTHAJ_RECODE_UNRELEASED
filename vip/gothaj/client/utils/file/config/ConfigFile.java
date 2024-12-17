package vip.gothaj.client.utils.file.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.client.Tuple;
import vip.gothaj.client.utils.file.FileUtils;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.ColorValue;
import vip.gothaj.client.values.settings.LocationValue;
import vip.gothaj.client.values.settings.ModeValue;
import vip.gothaj.client.values.settings.MultipleBooleanValue;
import vip.gothaj.client.values.settings.NumberValue;
import vip.gothaj.client.values.settings.RangeValue;

public class ConfigFile extends FileUtils{
	
	private String createdBy = "", date = "";
	
	private String name, description = "";
	
	private JsonObject content;

	public ConfigFile(String name, String content) {
		
	}
	
	public ConfigFile(String name, JsonObject content) {
		this.name = name;
		this.content = content;
	}
	
	public ConfigFile(String fileName) {
		this.name = fileName;
		
		File f = new File(ConfigManager.getDirectory(), fileName + ConfigManager.getDocumentType());
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			write();
		}
		
		try {
			JsonParser parser = new JsonParser();
			content = (JsonObject) parser.parse(org.apache.commons.io.FileUtils.readFileToString(f, StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.description = this.content.get("description") == null ? "" : this.content.get("description").getAsString();
		
		this.setType(ConfigType.LOCAL);
	}
	
	public void write() {
		File f = new File(ConfigManager.getDirectory(), name + ConfigManager.getDocumentType());

		JsonObject file = new JsonObject();
		
		file.addProperty("description", description);
		
		JsonObject modules = new JsonObject();
		
		for(Mod m : Client.INSTANCE.getModuleManager().getModules()) {
			JsonObject mod = new JsonObject();
			
			mod.addProperty("enabled", m.isEnabled());
			
			JsonObject settings = new JsonObject();

			for(Value s : m.getSettings()) {
				if(s instanceof RangeValue) {
					RangeValue setting = (RangeValue) s;
					JsonObject range = new JsonObject();
					
					range.addProperty("Min Value", setting.getMinValue());
					range.addProperty("Max Value", setting.getMaxValue());
					
					settings.add(s.getId(), range);
				}

				if(s instanceof NumberValue) {
					settings.addProperty(s.getId(), ((NumberValue) s).getValue());
				}
				if(s instanceof ModeValue) {
					settings.addProperty(s.getId(), ((ModeValue) s).getActiveMode().getName());
				}
				if(s instanceof BooleanValue) {
					settings.addProperty(s.getId(), ((BooleanValue) s).isEnabled());
				}
				if(s instanceof LocationValue) {
					LocationValue setting = (LocationValue) s;
					
					JsonObject location = new JsonObject();
					location.addProperty("Position X", setting.getX());
					location.addProperty("Position Y", setting.getY());
					
					settings.add(setting.getId(), location);
				}
				if(s instanceof ColorValue) {
					ColorValue setting = (ColorValue) s;
					JsonObject color = new JsonObject();
					
					color.addProperty("Color 1", setting.getColor1());
					color.addProperty("Color 2", setting.getColor2());
					color.addProperty("Alpha", setting.getAlpha());
					color.addProperty("Mode", setting.getMode());
					color.addProperty("Offset", setting.getOffset());
					color.addProperty("Speed", setting.getSpeed());
					
					settings.add(setting.getId(), color);
				}
				
				if(s instanceof MultipleBooleanValue) {
					MultipleBooleanValue setting = (MultipleBooleanValue) s;
					JsonObject bools = new JsonObject();
					
					for(Tuple<String, Boolean> tuple : setting.getValues()) {
						bools.addProperty(tuple.getFirst(), tuple.getSecond());
					}
					settings.add(setting.getId(), bools);
				}
				if(s instanceof CategoryValue) {
					settings.add(s.getId(), saveCategorySetting((CategoryValue) s));
				}
			}
			mod.add("settings", settings);
			
			modules.add(m.getName(), mod);
		}
		
		file.add("modules", modules);
		
		content = file;
		
		try {
			PrintWriter s = new PrintWriter(new FileWriter(f));
			s.println(FileUtils.prettyGson.toJson(content));
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void loadCategorySetting(CategoryValue parent, JsonObject settings) {
		for(Value s : parent.getSettings()) {
			JsonElement setting = settings.get(s.getId());
			
			if (setting == null) continue;
			
			if(s instanceof RangeValue) {
				RangeValue sett = (RangeValue) s;
				
				JsonObject obj = (JsonObject) setting;
				
				sett.setMinValue(obj.get("Min Value").getAsDouble());
				sett.setMaxValue(obj.get("Max Value").getAsDouble());
			}

			if(s instanceof NumberValue) {
				NumberValue sett = (NumberValue) s;
				sett.setValue(setting.getAsDouble());
			}
			if(s instanceof ModeValue) {
				ModeValue sett = (ModeValue) s;
				if(sett.getMode(setting.getAsString()) == null) continue;
				sett.setMode(sett.getMode(setting.getAsString()));
			}
			if(s instanceof BooleanValue) {
				BooleanValue sett = (BooleanValue) s;
				sett.setEnabled(setting.getAsBoolean());
			}
			if(s instanceof LocationValue) {
				LocationValue sett = (LocationValue) s;
				
				JsonObject obj = (JsonObject) setting;
				sett.setPosX(obj.get("Position X").getAsDouble());
				sett.setPosY(obj.get("Position Y").getAsDouble());
				
			}
			if(s instanceof ColorValue) {
				ColorValue sett = (ColorValue) s;
				JsonObject obj = (JsonObject) setting;
				
				sett.setColor1(obj.get("Color 1").getAsInt());
				sett.setColor2(obj.get("Color 2").getAsInt());
				sett.setAlpha(obj.get("Alpha").getAsInt());
				
				sett.setMode(obj.get("Mode").getAsString());
				sett.setOffset(obj.get("Offset").getAsDouble());
				sett.setSpeed(obj.get("Speed").getAsDouble());
			}
			
			if(s instanceof MultipleBooleanValue) {
				MultipleBooleanValue sett = (MultipleBooleanValue) s;
				JsonObject obj = (JsonObject) setting;
				
				for(Tuple<String, Boolean> tuple : sett.getValues()) {
					
					if(obj.get(tuple.getFirst()) == null) continue;
					
					tuple.setSecond(obj.get(tuple.getFirst()).getAsBoolean());
				}
			}
			
			if(s instanceof CategoryValue) {
				loadCategorySetting((CategoryValue) s, (JsonObject) setting);
			}
			
		}
	}
	public JsonObject saveCategorySetting(CategoryValue sett) {
		JsonObject category = new JsonObject();
		for(Value s : sett.getSettings()) {
			if(s instanceof RangeValue) {
				RangeValue setting = (RangeValue) s;
				JsonObject range = new JsonObject();
				
				range.addProperty("Min Value", setting.getMinValue());
				range.addProperty("Max Value", setting.getMaxValue());
				
				category.add(setting.getId(), range);
			}

			if(s instanceof NumberValue) {
				category.addProperty(s.getId(), ((NumberValue) s).getValue());
			}
			if(s instanceof ModeValue) {
				category.addProperty(s.getId(), ((ModeValue) s).getActiveMode().getName());
			}
			if(s instanceof BooleanValue) {
				category.addProperty(s.getId(), ((BooleanValue) s).isEnabled());
			}
			if(s instanceof LocationValue) {
				LocationValue setting = (LocationValue) s;
				
				JsonObject location = new JsonObject();
				location.addProperty("Position X", setting.getX());
				location.addProperty("Position Y", setting.getY());
				
				category.add(setting.getId(), location);
			}
			if(s instanceof MultipleBooleanValue) {
				MultipleBooleanValue setting = (MultipleBooleanValue) s;
				JsonObject bools = new JsonObject();
				
				for(Tuple<String, Boolean> tuple : setting.getValues()) {
					bools.addProperty(tuple.getFirst(), tuple.getSecond());
				}
				category.add(setting.getId(), bools);
			}
			if(s instanceof ColorValue) {
				ColorValue setting = (ColorValue) s;
				JsonObject color = new JsonObject();
				
				color.addProperty("Color 1", setting.getColor1());
				color.addProperty("Color 2", setting.getColor2());
				color.addProperty("Alpha", setting.getAlpha());
				color.addProperty("Mode", setting.getMode());
				color.addProperty("Offset", setting.getOffset());
				color.addProperty("Speed", setting.getSpeed());
				
				category.add(setting.getId(), color);
			}
			if(s instanceof CategoryValue) {
				category.add(s.getId(), saveCategorySetting((CategoryValue) s));
			}
		}
		return category;
	}
	
	public void read() {
		
		JsonObject modules = (JsonObject) content.get("modules");
		
		for(Entry<String, JsonElement> entry : modules.entrySet()) {
			Mod m = Client.INSTANCE.getModuleManager().getModule(entry.getKey());
			
			if(m == null) continue;
			
			JsonObject values = (JsonObject) entry.getValue();
			
			boolean enabled = (values.get("enabled") != null) ? values.get("enabled").getAsBoolean() : false;
			
			if((enabled && !m.isEnabled()) || (!enabled && m.isEnabled())) m.toggle();
			
			JsonObject settings = (JsonObject) values.get("settings");

			for(Value s : m.getSettings()) {
				JsonElement setting = settings.get(s.getId());
				
				if (setting == null) continue;
				
				if(s instanceof RangeValue) {
					RangeValue sett = (RangeValue) s;
					
					JsonObject obj = (JsonObject) setting;
					
					sett.setMinValue(obj.get("Min Value").getAsDouble());
					sett.setMaxValue(obj.get("Max Value").getAsDouble());
				}

				if(s instanceof NumberValue) {
					NumberValue sett = (NumberValue) s;
					sett.setValue(setting.getAsDouble());
				}
				if(s instanceof ModeValue) {
					ModeValue sett = (ModeValue) s;
					System.out.println(sett.getName() + " " + sett);
					
					if(sett.getMode(setting.getAsString()) == null) continue;
					sett.setMode(sett.getMode(setting.getAsString()));
				}
				if(s instanceof BooleanValue) {
					BooleanValue sett = (BooleanValue) s;
					sett.setEnabled(setting.getAsBoolean());
				}
				if(s instanceof LocationValue) {
					LocationValue sett = (LocationValue) s;
					
					JsonObject obj = (JsonObject) setting;
					sett.setPosX(obj.get("Position X").getAsDouble());
					sett.setPosY(obj.get("Position Y").getAsDouble());
					
				}
				if(s instanceof ColorValue) {
					ColorValue sett = (ColorValue) s;
					JsonObject obj = (JsonObject) setting;
					
					sett.setColor1(obj.get("Color 1").getAsInt());
					sett.setColor2(obj.get("Color 2").getAsInt());
					sett.setAlpha(obj.get("Alpha").getAsInt());
					
					sett.setMode(obj.get("Mode").getAsString());
					sett.setOffset(obj.get("Offset").getAsDouble());
					sett.setSpeed(obj.get("Speed").getAsDouble());
				}
				
				if(s instanceof MultipleBooleanValue) {
					MultipleBooleanValue sett = (MultipleBooleanValue) s;
					JsonObject obj = (JsonObject) setting;
					
					for(Tuple<String, Boolean> tuple : sett.getValues()) {
						
						if(obj.get(tuple.getFirst()) == null) continue;
						
						tuple.setSecond(obj.get(tuple.getFirst()).getAsBoolean());
					}
				}
				
				if(s instanceof CategoryValue) {
					try {
						loadCategorySetting((CategoryValue) s, (JsonObject) setting);
					}catch(Exception e) {
						
					}
					
				}
			}

		}
	}
	
	public void remove() {
		File f = new File(ConfigManager.getDirectory(), name + ConfigManager.getDocumentType());
		if(!f.exists()) return;
		
		f.delete();
		
		Client.INSTANCE.getConfigManager().getConfigs().remove(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JsonObject getContent() {
		return content;
	}

	public void setContent(JsonObject content) {
		this.content = content;
	}
	
}
