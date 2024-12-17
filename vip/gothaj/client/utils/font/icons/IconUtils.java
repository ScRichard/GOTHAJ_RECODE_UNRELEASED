package vip.gothaj.client.utils.font.icons;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;
import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.font.FontManager;
import vip.gothaj.client.utils.font.FontUtils;

public class IconUtils extends Wrapper{

	//THIS needs rework
	
	private static FontUtils icons = new FontUtils(FontManager.getResource(50, false, new ResourceLocation(FontManager.getLocation("icons"))));
	private static FontUtils icons2 = new FontUtils(FontManager.getResource(50, false, new ResourceLocation(FontManager.getLocation("icons-2"))));
	private static FontUtils icons3 = new FontUtils(FontManager.getResource(50, false, new ResourceLocation(FontManager.getLocation("icons-3"))));
	
	private static HashMap<String, IconInfo> iconNames = new HashMap<String, IconInfo>();
	
	public static void init() {
		
		iconNames.put("combat", new IconInfo(icons, "A"));
		iconNames.put("movement", new IconInfo(icons, "B"));
		iconNames.put("visuals", new IconInfo(icons, "C"));
		iconNames.put("player", new IconInfo(icons, "D"));
		iconNames.put("client", new IconInfo(icons, "E"));
		iconNames.put("arrow", new IconInfo(icons, "F"));
		iconNames.put("check", new IconInfo(icons, "G"));
		iconNames.put("cross", new IconInfo(icons, "H"));
		iconNames.put("script", new IconInfo(icons, "I"));
		iconNames.put("pencil", new IconInfo(icons, "J"));
		
		iconNames.put("folder", new IconInfo(icons2, "A"));
		iconNames.put("download", new IconInfo(icons2, "B"));
		iconNames.put("fix", new IconInfo(icons2, "C"));
		iconNames.put("menu", new IconInfo(icons2, "D"));
		iconNames.put("search", new IconInfo(icons2, "E"));
		iconNames.put("alert", new IconInfo(icons2, "F"));
		iconNames.put("info", new IconInfo(icons2, "G"));
		iconNames.put("l-arrow", new IconInfo(icons2, "H"));
		iconNames.put("r-arrow", new IconInfo(icons2, "I"));
		iconNames.put("time", new IconInfo(icons2, "J"));
		
		iconNames.put("reload", new IconInfo(icons3, "A"));
		iconNames.put("tag", new IconInfo(icons3, "B"));
		iconNames.put("star", new IconInfo(icons3, "C"));
		iconNames.put("plus", new IconInfo(icons3, "D"));
		iconNames.put("attach", new IconInfo(icons3, "E"));
		iconNames.put("onoff", new IconInfo(icons3, "F"));
		iconNames.put("file", new IconInfo(icons3, "G"));
		iconNames.put("exit", new IconInfo(icons3, "H"));
		iconNames.put("clock", new IconInfo(icons3, "I"));
		iconNames.put("trash", new IconInfo(icons3, "J"));
	}
	
	public static void drawIcon(String iconName, double x, double y, int color, double scale) {
		GL11.glPushMatrix();
		GL11.glTranslated(x - x*scale, y- y*scale, scale);
		GL11.glScaled(scale, scale, 1);
		iconNames.get(iconName).font.drawString(iconNames.get(iconName).iconId, x , y, color);
		GL11.glPopMatrix();
	}
	public static void drawIcon(String iconName, double x, double y, int color) {

		iconNames.get(iconName).font.drawString(iconNames.get(iconName).iconId, x , y, color);
	}
	public static double getIconWidth(String iconName, double scale) {
		return iconNames.get(iconName).font.getWidth(iconNames.get(iconName).iconId)*scale;
	}
	public static double getIconWidth(String iconName) {
		return iconNames.get(iconName).font.getWidth(iconNames.get(iconName).iconId);
	}
	public static double getIconHeight(String iconName,double scale) {
		return iconNames.get(iconName).font.getHeight()*scale;
	}
	public static double getIconHeight(String iconName) {
		return iconNames.get(iconName).font.getHeight();
	}
	
	public static class IconInfo{
		public FontUtils font;
		public String iconId;
		
		public IconInfo(FontUtils font, String iconId) {
			this.font = font;
			this.iconId = iconId;
		}
		
	}
	
}
