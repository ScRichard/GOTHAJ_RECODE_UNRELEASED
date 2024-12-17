package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.math.NumberUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.resource.ResourceUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.settings.ColorValue;

public class PanelColorSettings extends PanelSettings{

	private ColorValue setting;
	
	private boolean draggingColor1, draggingColor2, draggingHue1, draggingHue2, draggingAlpha;
	
	private PositionUtils color1 = new PositionUtils(0,0,60,50,1);
	private PositionUtils color2 = new PositionUtils(0,0,60,50,1);
	
	private PositionUtils hue1 = new PositionUtils(0,0,60,4,1);
	private PositionUtils hue2 = new PositionUtils(0,0,60,4,1);
	
	private PositionUtils alpha = new PositionUtils(0,0,130,4,1);
	
	
	private float h1 = 0,h2 = 0, s1 = 0,s2 = 0,b1 = 0,b2 = 0;
	
	private LinkedHashMap<String, PositionUtils> modes = new LinkedHashMap<String, PositionUtils>();
	
	public PanelColorSettings(ModuleButton parent, ColorValue s) {
		super(parent);
		this.setting =s;
		this.position.setHeight(83);
		Color c1 = new Color(s.getColor1());
		Color c2 = new Color(s.getColor2());
		
		h1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null)[0];
		h2 = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null)[0];
		s1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null)[1];
		s2 = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null)[1];
		b1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null)[2];
		b2 = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null)[2];
		this.visible = s.getVisibility();
		
		for(String m : s.getModes()) {
			modes.put(m, new PositionUtils(0,0,Fonts.getWidth(m, "roboto-bold", 0.9)+4, Fonts.getHeight("roboto-bold", 0.9)+4, 1));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {

		color1.setX(this.position.getX()+20);
		color1.setY(this.position.getY()+15);
		
		color2.setX(this.position.getX()+90);
		color2.setY(this.position.getY()+15);
		
		hue1.setX(this.position.getX()+20);
		hue1.setY(this.position.getY()+69);
		hue2.setX(this.position.getX()+90);
		hue2.setY(this.position.getY()+69);
		
		alpha.setX(this.position.getX()+20);
		alpha.setY(this.position.getY()+76);
		
		RoundedUtils.drawRoundedRect(this.position.getX()+this.position.getWidth()/2-2-10, this.position.getY()+2.5, 10, 10, setting.getColor1() ,4);
		RoundedUtils.drawRoundedRect(this.position.getX()+this.position.getWidth()/2+2, this.position.getY()+2.5, 10, 10, setting.getColor2() ,4);
		
		Fonts.drawString(setting.getName(), this.position.getX()+20, this.position.getY()+3, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		
		Fonts.drawString(setting.getAlpha()+"§b§l%", this.position.getX2()-Fonts.getWidth(setting.getAlpha()+"%", "roboto-regular")-10, this.position.getY()+3, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-regular");
		
		Fonts.drawString("Mode", this.position.getX()+160, this.position.getY()+15, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		double posOffset = 0;
		double posY = 0;
		for(Entry<String, PositionUtils> entry : modes.entrySet()) {
			entry.getValue().setX(this.position.getX()+160+posOffset);
			entry.getValue().setY(this.position.getY()+posY+25);
			
			RoundedUtils.drawRoundedRect(entry.getValue(),setting.getMode().equals(entry.getKey()) ? Client.INSTANCE.getThemeManager().get("Panel Active Color"): Client.INSTANCE.getThemeManager().get("Panel Other Buttons Background"), 1);
			Fonts.drawString(entry.getKey(), entry.getValue().getX()+1.5, entry.getValue().getY()+2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-bold", 0.9);
			
			if(this.position.getX()+160+posOffset < this.position.getX2()-70)
				posOffset+= 3 + entry.getValue().getWidth();
			else {
				posY += Fonts.getHeight("roboto-bold", 0.9)+8;
				posOffset = 0;
			}
		}
		
		
		RenderUtils.drawColorPicker(color1, h1);
		RenderUtils.drawColorPicker(color2, h2);
		
		RenderUtils.drawColorSlider(hue1);
		RenderUtils.drawColorSlider(hue2);
		
		RenderUtils.drawImage(this.alpha.getX(), this.alpha.getY(), this.alpha.getWidth(), this.alpha.getHeight(), ResourceUtils.getResource("alpha-panel"), -1);
		RenderUtils.drawGradientRect(this.alpha.getX(), this.alpha.getY(), this.alpha.getX2(), this.alpha.getY2(), 0x000000, 0xff000000, 0xff000000, 0x00000);
		
		if(draggingColor1) {
			double diff = Math.min(color1.getWidth(), Math.max(0, mouseX - (color1.getX())));
			double diffY = Math.min(color1.getHeight(), Math.max(0, mouseY - (color1.getY())));
			
			s1 = (float) NumberUtils.roundToPlace(((diff / color1.getWidth()) * (100) / 100), (int) color1.getWidth());
			b1 = (float) (1-NumberUtils.roundToPlace(((diffY / color1.getHeight()) * (100) / 100), (int) color1.getHeight()));
			
			this.setting.setColor1(Color.HSBtoRGB(h1, s1, b1));
		}

		if(draggingColor2) {
			double diff = Math.min(color2.getWidth(), Math.max(0, mouseX - (color2.getX())));
			double diffY = Math.min(color2.getHeight(), Math.max(0, mouseY - (color2.getY())));
			
			s2 = (float) NumberUtils.roundToPlace(((diff / color2.getWidth()) * (100) / 100), (int) color2.getWidth());
			b2 = (float) (1-NumberUtils.roundToPlace(((diffY / color2.getHeight()) * (100) / 100), (int) color2.getHeight()));
			
			this.setting.setColor2(Color.HSBtoRGB(h2, s2, b2));
		}
		if(draggingHue1) {
			double diff = Math.min(hue1.getWidth(), Math.max(0, mouseX - (hue1.getX())));
			
			h1 = (float) NumberUtils.roundToPlace(((diff / hue1.getWidth()) * (360F) / 360), (int) hue1.getWidth());
		}
		
		if(draggingHue2) {
			double diff = Math.min(hue2.getWidth(), Math.max(0, mouseX - (hue2.getX())));
			
			h2 = (float) NumberUtils.roundToPlace(((diff / hue2.getWidth()) * (360) / 360), (int) hue2.getWidth());
		}
		
		if(draggingAlpha) {
			double diff = Math.min(alpha.getWidth(), Math.max(0, mouseX - (alpha.getX())));
			
			double a = (float) NumberUtils.roundToPlace(((diff / alpha.getWidth()) * (100) ), (int) alpha.getWidth());
			
			this.setting.setAlpha((int) a);
		}
		
		//Cursors
		RoundedUtils.drawRoundedRect(color1.getX()+color1.getWidth()*s1-3.5,color1.getY()+color1.getHeight()*(1-b1)-3.5,7,7, Client.INSTANCE.getThemeManager().get("Panel Cursors Background"), 2);
		RoundedUtils.drawRoundedRect(color1.getX()+color1.getWidth()*s1-3,color1.getY()+color1.getHeight()*(1-b1)-3,6,6, this.setting.getColor1(), 2);
		
		RoundedUtils.drawRoundedRect(color2.getX()+color2.getWidth()*s2-3.5,color2.getY()+color2.getHeight()*(1-b2)-3.5,7,7, Client.INSTANCE.getThemeManager().get("Panel Cursors Background"), 2);
		RoundedUtils.drawRoundedRect(color2.getX()+color2.getWidth()*s2-3,color2.getY()+color2.getHeight()*(1-b2)-3,6,6, this.setting.getColor2(), 2);
	
		RoundedUtils.drawRoundedRect(hue1.getX()+hue1.getWidth()*h1-2.5, hue1.getY()-1,5,6, Client.INSTANCE.getThemeManager().get("Panel Cursors Background"), 1);
		
		RoundedUtils.drawRoundedRect(hue2.getX()+hue2.getWidth()*h2-2.5, hue2.getY()-1,5,6, Client.INSTANCE.getThemeManager().get("Panel Cursors Background"), 1);
		
		RoundedUtils.drawRoundedRect(alpha.getX()+alpha.getWidth()*this.setting.getAlpha()/100-2.5, alpha.getY()-1,5,6, Client.INSTANCE.getThemeManager().get("Panel Cursors Background"), 1);
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		
		if(color1.isInside(mouseX, mouseY) && button == 0) {
			draggingColor1 = true;
		}
		if(color2.isInside(mouseX, mouseY) && button == 0) {
			draggingColor2 = true;
		}
		if(hue1.isInside(mouseX, mouseY) && button == 0) {
			draggingHue1 = true;
		}
		if(hue2.isInside(mouseX, mouseY) && button == 0) {
			draggingHue2 = true;
		}
		if(alpha.isInside(mouseX, mouseY) && button == 0) {
			draggingAlpha = true;
		}
		
		for(Entry<String, PositionUtils> entry : modes.entrySet()) {
			if(entry.getValue().isInside(mouseX, mouseY) && button == 0) {
				this.setting.setMode(entry.getKey());
			}
		}

	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		draggingColor1 = false;
		draggingColor2 = false;
		draggingHue1 = false;
		draggingHue2 = false;
		draggingAlpha = false;
	}

	@Override
	public void onKey(int key, char ch) {

	}

	
	
}
