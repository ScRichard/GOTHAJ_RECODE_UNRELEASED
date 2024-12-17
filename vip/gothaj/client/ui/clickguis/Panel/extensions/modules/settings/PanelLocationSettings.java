package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.math.NumberUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.settings.LocationValue;

public class PanelLocationSettings extends PanelSettings{
	private LocationValue setting;
	
	private boolean dragging;
	
	private PositionUtils location = new PositionUtils(0, 0, 100, 65, 1);
	
	public PanelLocationSettings(ModuleButton parent, LocationValue setting) {
		super(parent);
		this.setting = setting;
		this.visible = setting.getVisibility();
		this.position.setHeight(75);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		this.location.setX(this.position.getX()+5);
		this.location.setY(this.position.getY()+5);
		
		if(dragging) {
			double diff = Math.min(location.getWidth(), Math.max(0, mouseX - (location.getX())));
			double diffY = Math.min(location.getHeight(), Math.max(0, mouseY - (location.getY())));
			double newValue = NumberUtils.roundToPlace(((diff / location.getWidth()) * (setting.getMaxX() - setting.getMinX()) + setting.getMinX()), (int) location.getWidth());
			setting.setX((float) newValue);
			newValue = NumberUtils.roundToPlace(((diffY / location.getHeight()) * (setting.getMaxY() - setting.getMinY()) + setting.getMinY()), (int) location.getHeight());
			setting.setY((float) newValue);
		}
		
		double posX = ((setting.getX() - setting.getMinX()) / (setting.getMaxX() - setting.getMinX())) * this.location.getWidth();
		double posY = ((setting.getY() - setting.getMinY()) / (setting.getMaxY() - setting.getMinY())) * this.location.getHeight();
		
		RenderUtils.drawOutlinedRect(this.location, Client.INSTANCE.getThemeManager().get("Panel Location Box Outline"), 2);
		
		RoundedUtils.drawRoundedRect(this.location.getX()+posX-3, this.location.getY()+posY-3, 6, 6, Client.INSTANCE.getThemeManager().get("Panel Cursors Background"), 2);
		
		Fonts.drawStringCentered(setting.getName(), this.location.getX()+ this.location.getWidth()/2,  this.location.getY()+ this.location.getHeight()/2, -1, "mexcellent", 1.2);
		
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(location.isInside(mouseX, mouseY) && button == 0) {
			dragging = true;
		}
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		dragging = false;
	}

}
