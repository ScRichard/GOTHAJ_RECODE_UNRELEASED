package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.ModeValue;

public class PanelModeSettings extends PanelSettings{
	private ModeValue setting;
	
	private LinkedHashMap<Value, PositionUtils> modes = new LinkedHashMap<Value, PositionUtils>();
	
	public PanelModeSettings(ModuleButton parent, ModeValue setting) {
		super(parent);
		this.setting = setting;
		this.position.setHeight(15);
		
		for(Value s : setting.getModes()) {
			modes.put(s, new PositionUtils(0,0,Fonts.getWidth(s.getName(), "roboto-bold", 0.9)+4, Fonts.getHeight("roboto-bold", 0.9)+4, 1));
		}
		setVisible(setting.getVisibility());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		Fonts.drawString(setting.getName(), this.position.getX(), this.position.getY()+7.5-Fonts.getHeight("roboto-medium")/2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		
		double posOffset = Fonts.getWidth(setting.getName(),"roboto-medium")+5;
		double posY = 0;
		
		for(Entry<Value, PositionUtils> entry : modes.entrySet()) {
			entry.getValue().setX(this.position.getX()+posOffset);
			entry.getValue().setY(this.position.getY()+7.5-entry.getValue().getHeight()/2+posY);
			
			RoundedUtils.drawRoundedRect(entry.getValue(), setting.getActiveMode() == entry.getKey() ? Client.INSTANCE.getThemeManager().get("Panel Active Color"): Client.INSTANCE.getThemeManager().get("Panel Other Buttons Background"), 1);
			Fonts.drawString(entry.getKey().getName(), entry.getValue().getX()+1.5, entry.getValue().getY()+2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-bold", 0.9);
			
			if(this.position.getX()+posOffset < this.position.getX2()-20)
				posOffset+= 5 + entry.getValue().getWidth();
			else {
				posY += Fonts.getHeight("roboto-bold", 0.9)+8;
				posOffset = 20;
			}
		}
		this.position.setHeight(15+posY+3);
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		
		if(button != 0) return;
		
		for(Entry<Value, PositionUtils> entry : modes.entrySet()) {
			if(entry.getValue().isInside(mouseX, mouseY)) {
				setting.setMode(entry.getKey());
			}
		}
	}

}
