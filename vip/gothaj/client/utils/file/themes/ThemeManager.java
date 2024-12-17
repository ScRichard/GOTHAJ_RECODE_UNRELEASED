
package vip.gothaj.client.utils.file.themes;

import java.io.File;
import java.util.ArrayList;

import vip.gothaj.client.utils.file.config.ConfigFile;

public class ThemeManager {

	private static String documentType = ".json";
	
	private ArrayList<Theme> themes = new ArrayList();
	
	private Theme activeTheme = new Theme("Dark");
	
	private static File directory = new File("Gothaj/Themes");
	
	public ThemeManager() {
		themes.clear();
		
		if(!directory.exists()) {
			directory.mkdirs();
		}

		for(File f : directory.listFiles()) {
			String type = f.getName().substring(f.getName().lastIndexOf("."), f.getName().length());
			
			if(!type.equals(documentType)) continue;
			
			themes.add(new Theme(f.getName().substring(0, f.getName().lastIndexOf("."))));
		}
		activeTheme.read();
	}

	public int get(String name) {
		return activeTheme.get(name);
	}
	
	public ArrayList<Theme> getThemes() {
		return themes;
	}

	public void setThemes(ArrayList<Theme> themes) {
		this.themes = themes;
	}

	public Theme getActiveTheme() {
		return activeTheme;
	}

	public void setActiveTheme(Theme activeTheme) {
		this.activeTheme = activeTheme;
	}

	public static String getDocumentType() {
		return documentType;
	}

	public static void setDocumentType(String documentType) {
		ThemeManager.documentType = documentType;
	}

	public static File getDirectory() {
		return directory;
	}

	public static void setDirectory(File directory) {
		ThemeManager.directory = directory;
	}
	
}