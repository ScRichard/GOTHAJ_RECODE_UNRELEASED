package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.math.NumberUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.settings.NumberValue;

public class PanelNumberSettings extends PanelSettings{

	private NumberValue setting;
	
	private PositionUtils slider = new PositionUtils(0, 0, 140, 3, 1);
	
	private boolean dragging;
	
	public PanelNumberSettings(ModuleButton parent, NumberValue settings) {
		super(parent);
		this.setting = settings;
		this.position.setHeight(20);
		setVisible(setting.getVisibility());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {

		this.slider.setX(this.position.getX()+10);
		slider.setY(this.position.getY()+12);
		
		double diff = Math.min(slider.getWidth(), Math.max(0, mouseX - (slider.getX())));
		Fonts.drawString(setting.getName(), this.position.getX(), this.position.getY(), Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		
		Fonts.drawString(setting.getValue()+"", this.slider.getX2()-Fonts.getWidth(setting.getValue()+"", "roboto-medium"), this.position.getY(), Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		if(dragging) {
			double newValue = NumberUtils.roundToPlace(((diff / slider.getWidth()) * (setting.getMax() - setting.getMin()) + setting.getMin()),
					(int) slider.getWidth());
			setting.setValue((float) newValue);
		}
		RoundedUtils.drawRoundedRect(slider, Client.INSTANCE.getThemeManager().get("Panel Slider Background"), 1);
		
		double xPos1 = (((setting.getValue()-setting.getMin()) / (setting.getMax()-setting.getMin()))) * slider.getWidth();
		
		RoundedUtils.drawRoundedRect(slider.getX(), slider.getY(), xPos1, slider.getHeight(), Client.INSTANCE.getThemeManager().get("Panel Active Color"), 1);

		RoundedUtils.drawRoundedRect(slider.getX()+xPos1-4, slider.getY()+slider.getHeight()/2-4, 8, 8, Client.INSTANCE.getThemeManager().get("Panel Active Color"), 4);
		RoundedUtils.drawRoundedRect(slider.getX()+xPos1-3, slider.getY()+slider.getHeight()/2-3, 6, 6, Client.INSTANCE.getThemeManager().get("Panel Module Background"), 3);
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(slider.isInside(mouseX, mouseY)) {
			dragging = true;
		}
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		dragging = false;
	}
	
	
	

}