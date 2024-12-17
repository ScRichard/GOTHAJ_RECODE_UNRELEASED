package vip.gothaj.client.scripts;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import vip.gothaj.client.Client;
import vip.gothaj.client.event.Event;

public class ScriptManager {
	
	private ArrayList<Script> scripts = new ArrayList();
	
	private File directory = new File("Gothaj/Scripts");

	public ScriptManager() {
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		loadAll();
		
	}
	
	public void loadAll() {
		for(Script sc : scripts) {
			Client.INSTANCE.getModuleManager().getModules().removeIf(m -> m == sc.getModule());
		}
		
		scripts.clear();
		for(File f : directory.listFiles()) {
			
			if(!f.getName().endsWith(".js")) {
				continue;
			}
			String inside;
			try {
				inside = org.apache.commons.io.FileUtils.readFileToString(f, StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error: Something went wrong with loading a scipt: " + f.getName());
				continue;
			}
			
			Script script = new Script(f, inside);
			
			scripts.add(script);
			script.callEvent("load", new Event[] {});
		}
		
		Client.INSTANCE.getModuleManager().getModules().sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
	}

	public ArrayList<Script> getScripts() {
		return scripts;
	}

	public void setScripts(ArrayList<Script> scripts) {
		this.scripts = scripts;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}
	

}
