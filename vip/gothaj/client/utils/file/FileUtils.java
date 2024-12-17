package vip.gothaj.client.utils.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public abstract class FileUtils {
	
	public static Gson gson = new Gson();
	public static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	public static JsonParser jsonParser = new JsonParser();
	
	private ConfigType type = ConfigType.LOCAL;
	
	public abstract void write();
	public abstract void read();
	
	
	
	public ConfigType getType() {
		return type;
	}
	public void setType(ConfigType type) {
		this.type = type;
	}
	public enum ConfigType {
		LOCAL,
		ONLINE
	}
}
