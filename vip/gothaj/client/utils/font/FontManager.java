package vip.gothaj.client.utils.font;

import java.awt.Font;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontManager {

	private HashMap<String, FontUtils> fonts = new HashMap();
	
	public FontManager() {
		
		fonts.put("roboto-bold", new FontUtils(getResource(30, false, new ResourceLocation(getLocation("Roboto-Bold")))));
		fonts.put("roboto-medium", new FontUtils(getResource(30, false, new ResourceLocation(getLocation("Roboto-Medium")))));
		fonts.put("roboto-regular", new FontUtils(getResource(30, false, new ResourceLocation(getLocation("Roboto-Regular")))));
		fonts.put("tahoma", new FontUtils(getResource(30, false, new ResourceLocation(getLocation("Tahoma-Bold")))));
		fonts.put("mexcellent", new FontUtils(getResource(30, false, new ResourceLocation(getLocation("Mexcellent")))));
	}
	
	public FontUtils getFont(String font) {
		for(Entry<String, FontUtils> entry : fonts.entrySet()) {
			if(entry.getKey().toLowerCase().equals(font.toLowerCase())) return entry.getValue();
		}
		return null;
	}
	
	public static String getLocation(String name) {
		return "gothaj/fonts/"+ name+".ttf";
	}
	
	public static Font getResource(float size, boolean bold, ResourceLocation fontFile) {
        Font font = null;

        try
        {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                             .getResource(fontFile).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Error loading font, setted to verdana.");
            font = new Font("Verdana", 0, 12);
        }

        return font;
    }
	
}
