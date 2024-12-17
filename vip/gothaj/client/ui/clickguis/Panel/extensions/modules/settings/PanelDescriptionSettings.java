package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import org.apache.commons.lang3.text.WordUtils;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.values.settings.DescriptionValue;

public class PanelDescriptionSettings extends PanelSettings{

	private DescriptionValue setting;
	
	public PanelDescriptionSettings(ModuleButton parent, DescriptionValue setting) {
		super(parent);
		this.setting = setting;
		
		this.position.setHeight(10);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		Fonts.drawString(setting.getName(), this.position.getX()+5, this.position.getY()+1, Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"), "roboto-medium");
	}
}
