package vip.gothaj.client.utils.file.themes;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import vip.gothaj.client.utils.file.FileUtils;
import vip.gothaj.client.utils.file.config.ConfigManager;

public class Theme extends FileUtils{


	
	private String name;
	
	private JsonObject theme;
	
	private LinkedHashMap<String, Integer> colors = new LinkedHashMap<String, Integer>();

	public Theme(String name) {
		this.name = name;
		setDefaults();
		
		File f = new File(ThemeManager.getDirectory(), name + ThemeManager.getDocumentType());
		
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
			theme = (JsonObject) parser.parse(org.apache.commons.io.FileUtils.readFileToString(f, StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	
	public static int hexToRgba(String hexColor) {
		if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
        }
		if(hexColor.length() == 6) {
			int red = Integer.parseInt(hexColor.substring(0, 2), 16);
	        int green = Integer.parseInt(hexColor.substring(2, 4), 16);
	        int blue = Integer.parseInt(hexColor.substring(4, 6), 16);
	        return new Color(red, green, blue).getRGB();
	        
		} else if (hexColor.length() == 8) {
			int alpha = Integer.parseInt(hexColor.substring(0, 2), 16);
			int red = Integer.parseInt(hexColor.substring(2, 4), 16);
	        int green = Integer.parseInt(hexColor.substring(4, 6), 16);
	        int blue = Integer.parseInt(hexColor.substring(6, 8), 16);
	        return new Color(red, green, blue, alpha).getRGB();
		}
		
		return -1;
	}
	
	@Override
	public void write() {
		File f = new File(ThemeManager.getDirectory(), name + ThemeManager.getDocumentType());
		
		JsonObject json = new JsonObject();
		
		for(Entry<String, Integer> values : colors.entrySet()) {
			json.addProperty(values.getKey(), rgbaToHex(values.getValue()));
		}
		
		theme = json;
		
		try {
			PrintWriter s = new PrintWriter(new FileWriter(f));
			s.println(FileUtils.prettyGson.toJson(json));
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void read() {
		for(Entry<String, Integer> values : colors.entrySet()) {
			if(theme.get(values.getKey()) == null) continue;
			
			values.setValue(hexToRgba(theme.get(values.getKey()).getAsString()));
		}
	}
	
	public static String rgbaToHex(int color) {
		Color c = new Color(color);
		
		String alpha = (Integer.toHexString(c.getAlpha()).length() == 1 ? "0"+Integer.toHexString(c.getAlpha()) : Integer.toHexString(c.getAlpha()));
		
		if(alpha.toLowerCase().equals("ff")) alpha = "";

        String hexColor = "#" +alpha
        		+ (Integer.toHexString(c.getRed()).length() == 1 ? "0"+Integer.toHexString(c.getRed()) : Integer.toHexString(c.getRed()))
        		+ (Integer.toHexString(c.getGreen()).length() == 1 ? "0"+Integer.toHexString(c.getGreen()) : Integer.toHexString(c.getGreen()))
        		+ (Integer.toHexString(c.getBlue()).length() == 1 ? "0"+Integer.toHexString(c.getBlue()) : Integer.toHexString(c.getBlue()));
        
        return hexColor.toLowerCase();
    }
	public void setDefaults() {
		colors.clear();
		colors.put("Panel Background Color", 0xff0F151B);
		colors.put("Panel Screen Backround Color", 0xff171F29);
		colors.put("Panel Screen Scroll Thumb Color", 0xff2C3C5A);
		colors.put("Panel Content Button Color", 0xff171F29);
		colors.put("Panel Module Background", 0xff0e151c);
		colors.put("Panel Active Color", 0xff5f94d4);
		colors.put("Panel Category Setting Line", 0x70394556);
		colors.put("Panel Other Buttons Background",  0xff162029);
		colors.put("Panel Cursors Background", 0xffffffff);
		colors.put("Panel Location Box Outline", 0x25aaaaaa);
		colors.put("Panel Slider Background", 0x90000000);
		colors.put("Panel Slider Background Filled", 0xffaaaaaa);
		colors.put("Panel Slider Cursor", -1);
		colors.put("Panel Search Text Color", 0xaaffffff);
		colors.put("Panel Search Cursor Color", -1);
		colors.put("Panel Text Color", -1);
		colors.put("Panel Descriptions Color", 0xff636F7F);
	}
	
	public int get(String name) {
		return colors.get(name);
	}
}
