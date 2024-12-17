package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.client.ChatUtils;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.math.NumberUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.settings.RangeValue;

public class PanelRangeSettings extends PanelSettings {

	RangeValue setting;

	private PositionUtils slider = new PositionUtils(0, 0, 140, 3, 1);
	
	private boolean draggingMin, draggingMax;

	public PanelRangeSettings(ModuleButton parent, RangeValue setting) {
		super(parent);
		this.setting = setting;
		this.position.setHeight(20);
		setVisible(setting.getVisibility());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {

		slider.setX(this.position.getX()+10);
		slider.setY(this.position.getY()+12);

		Fonts.drawString(setting.getName(), this.position.getX(), this.position.getY(), Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		
		Fonts.drawString(setting.getMinValue()+" - "+setting.getMaxValue(), this.slider.getX2()-Fonts.getWidth(setting.getMinValue()+" - "+setting.getMaxValue(), "roboto-medium"), this.position.getY(), Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		
		RoundedUtils.drawRoundedRect(slider, Client.INSTANCE.getThemeManager().get("Panel Slider Background"), 1);
		
		double xPos1 = slider.getX() + ((setting.getMinValue()-setting.getMin()) / (setting.getMax()-setting.getMin())) * slider.getWidth();
		double xPos2 = slider.getX() + ((setting.getMaxValue()-setting.getMin()) / (setting.getMax()-setting.getMin())) * slider.getWidth();

		RoundedUtils.drawRoundedRect(xPos1, slider.getY(), xPos2-xPos1, slider.getHeight(), Client.INSTANCE.getThemeManager().get("Panel Active Color"), 1);
		
		RoundedUtils.drawRoundedRect(xPos1-4, slider.getY()+slider.getHeight()/2-4, 8, 8, Client.INSTANCE.getThemeManager().get("Panel Active Color"), 4);
		
		RoundedUtils.drawRoundedRect(xPos1-3, slider.getY()+slider.getHeight()/2-3, 6, 6, Client.INSTANCE.getThemeManager().get("Panel Module Background"), 3);
		
		RoundedUtils.drawRoundedRect(xPos2-4, slider.getY()+slider.getHeight()/2-4, 8, 8, Client.INSTANCE.getThemeManager().get("Panel Active Color"), 4);
		RoundedUtils.drawRoundedRect(xPos2-3, slider.getY()+slider.getHeight()/2-3, 6, 6, Client.INSTANCE.getThemeManager().get("Panel Module Background"), 3);
		
		double diff = Math.min(slider.getWidth(), Math.max(0, mouseX - (slider.getX())));
		if (draggingMin) {
			double newValue = NumberUtils.roundToPlace(((diff / slider.getWidth()) * (setting.getMax() - setting.getMin()) + setting.getMin()),
					(int) slider.getWidth());
			setting.setMinValue((float) newValue);
		}
		if (draggingMax) {
			double newValue = NumberUtils.roundToPlace(((diff / slider.getWidth()) * (setting.getMax() - setting.getMin()) + setting.getMin()),
					(int) slider.getWidth());
			setting.setMaxValue((float) newValue);
		}
		
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		
		if(slider.isInside(mouseX, mouseY) && button == 0) {
			double xPos1 = slider.getX() + (setting.getMinValue() / setting.getMax()) * slider.getWidth();
			double xPos2 = slider.getX() + (setting.getMaxValue() / setting.getMax()) * slider.getWidth();
			
			draggingMin = false;
			draggingMax = false;
			
			double diff = (xPos2- xPos1)/2;
			
			if(slider.getX() <= mouseX && xPos1+diff/2 > mouseX) {
				draggingMin = true;
			}
			if(slider.getX2() >= mouseX && xPos2-diff/2 < mouseX) {
				draggingMax = true;
			}
		}
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		draggingMin = false;
		draggingMax = false;
		ChatUtils.debug("Released");
	}

	public RangeValue getSetting() {
		return setting;
	}

	public void setSetting(RangeValue setting) {
		this.setting = setting;
	}

}
