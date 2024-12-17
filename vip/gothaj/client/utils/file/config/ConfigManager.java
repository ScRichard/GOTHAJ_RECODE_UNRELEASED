package vip.gothaj.client.utils.file.config;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager {
	
	private String onlineConfigs = "";
	
	private static String documentType = ".gothaj";
	
	private ArrayList<ConfigFile> configs = new ArrayList();
	
	private ConfigFile activeConfig = new ConfigFile("default");
	
	private static File directory = new File("Gothaj/Configs");
	
	public ConfigManager() {
		configs.clear();
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		
		for(File f : directory.listFiles()) {
			String type = f.getName().substring(f.getName().lastIndexOf("."), f.getName().length());
			
			if(!type.equals(documentType)) continue;
			
			configs.add(new ConfigFile(f.getName().substring(0, f.getName().lastIndexOf("."))));
		}

		activeConfig.read();
		
	}
	
	public void onShutdown() {
		activeConfig.write();
	}

	public String getOnlineConfigs() {
		return onlineConfigs;
	}

	public void setOnlineConfigs(String onlineConfigs) {
		this.onlineConfigs = onlineConfigs;
	}

	public static String getDocumentType() {
		return documentType;
	}

	public static void setDocumentType(String documentType) {
		ConfigManager.documentType = documentType;
	}

	public ArrayList<ConfigFile> getConfigs() {
		return configs;
	}
	public ConfigFile getConfig(String name) {
		return configs.stream().filter(c -> c.getName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
	}

	public void setConfigs(ArrayList<ConfigFile> configs) {
		this.configs = configs;
	}

	public static File getDirectory() {
		return directory;
	}

	public static void setDirectory(File directory) {
		ConfigManager.directory = directory;
	}

	public ConfigFile getActiveConfig() {
		return activeConfig;
	}

	public void setActiveConfig(ConfigFile activeConfig) {
		this.activeConfig = activeConfig;
	}
	
	
}
