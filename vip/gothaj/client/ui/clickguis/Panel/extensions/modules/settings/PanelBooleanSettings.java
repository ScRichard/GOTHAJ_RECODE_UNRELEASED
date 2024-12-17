package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.buttons.CheckMark;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.values.settings.BooleanValue;

public class PanelBooleanSettings extends PanelSettings{

	private BooleanValue settings;
	
	private CheckMark checkmark = new CheckMark(12, false);
	
	public PanelBooleanSettings(ModuleButton parent, BooleanValue s) {
		super(parent);
		this.settings = s;
		this.position.setHeight(20);
		setVisible(s.getVisibility());
	}
	

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		checkmark.setEnabled(settings.isEnabled());
		Fonts.drawString(settings.getName(), this.position.getX(), this.position.getY()+10-Fonts.getHeight("roboto-medium")/2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		
		checkmark.position.setX(this.position.getX()+Fonts.getWidth(settings.getName(), "roboto-medium")+8);
		checkmark.position.setY(this.position.getY()+4);
		checkmark.drawScreen(mouseX, mouseY);
	}
	
	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(checkmark.position.isInside(mouseX, mouseY) && button == 0) {
			settings.setEnabled(!settings.isEnabled());
		}
	}

}
