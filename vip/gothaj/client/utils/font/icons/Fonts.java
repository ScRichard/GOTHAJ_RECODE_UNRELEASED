package vip.gothaj.client.utils.font.icons;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import vip.gothaj.client.Client;
import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.client.ChatUtils;

public class Fonts extends Wrapper {

	public static void drawString(String text, double x, double y, int color, String fontType) {
		drawString(text, x, y, color, fontType, 1);
	}
	public static void drawString(String text, double x, double y, int color, String fontType, double scale) {
		GL11.glPushMatrix();
		GL11.glTranslated(x - x * scale, y - y * scale, 1);
		GL11.glScaled(scale, scale, 1);
		if(fontType.toLowerCase().equals("minecraft")) {
			mc.fontRendererObj.drawString(text, x, y, color);
		}else {
			Client.INSTANCE.getFontManager().getFont(fontType.toLowerCase()).drawString(text, x, y, color);
		}
		GL11.glScaled(1, 1, 1);
		GL11.glPopMatrix();
		
	}
	
	public static double drawStringBoxed(String text, double x, double y, int color, int maxChars, String fontType, double scale) {
		GL11.glPushMatrix();
		GL11.glTranslated(x - x * scale, y - y * scale, 1);
		GL11.glScaled(scale, scale, 1);
		String[] wrappedText = WordUtils.wrap(text, (int) Math.round(maxChars/scale), null, false).split("\n");
		int i = 0;
		for(String t : wrappedText) {
			if(fontType.toLowerCase().equals("minecraft")) {
				mc.fontRendererObj.drawString(t, x, y+i*getHeight(fontType, scale), color);
			}else {
				Client.INSTANCE.getFontManager().getFont(fontType.toLowerCase()).drawString(t, x, y+i*getHeight(fontType, scale), color);
			}
			i++;
		}
		GL11.glScaled(1, 1, 1);
		GL11.glPopMatrix();
		
		return (double) (i*getHeight(fontType, scale));
	}
	
	public static void drawStringCentered(String text, double x, double y, int color, String fontType, double scale) {
		drawString(text,x-getWidth(text, fontType, scale)/2, y-getHeight(fontType, scale)/2, color, fontType, scale);
	}
	public static void drawStringCentered(String text, double x, double y, int color, String fontType) {
		drawString(text,x-getWidth(text, fontType)/2, y-getHeight(fontType)/2, color, fontType);
	}
	
	public static double getWidth(String text, String fontType, double scale) {
		if(fontType.toLowerCase().equals("minecraft")) {
			return mc.fontRendererObj.getStringWidth(text) * scale;
		}
		return Client.INSTANCE.getFontManager().getFont(fontType.toLowerCase()).getWidth(text) * scale;
	}
	public static double getWidth(String text, String fontType) {
		return getWidth(text, fontType, 1);
	}
	public static double getHeight(String fontType, double scale) {
		if(fontType.toLowerCase().equals("minecraft")) {
			return mc.fontRendererObj.FONT_HEIGHT * scale;
		}

		return Client.INSTANCE.getFontManager().getFont(fontType.toLowerCase()).getHeight() * scale;
	}
	public static double getHeight(String fontType) {
		return getHeight(fontType, 1);
	}
	
}
