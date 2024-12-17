package vip.gothaj.client.ui.clickguis.Panel.extensions.clientsettings;

import vip.gothaj.client.ui.clickguis.Panel.screens.ads.ClientSettingsScreen;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.settings.CategoryValue;

public class ClientSettingsObject extends Button{

	private CategoryValue category;
	
	private ClientSettingsScreen parent;
	
	private PositionUtils position = new PositionUtils(0,0, 150, 282,1);
	
	public ClientSettingsObject(CategoryValue category, ClientSettingsScreen parent) {
		this.category = category;
		this.parent = parent;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RoundedUtils.drawRoundedRect(position,  0xff000000, 4);
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKey(int key, char ch) {
		// TODO Auto-generated method stub
		
	}

	public CategoryValue getCategory() {
		return category;
	}

	public void setCategory(CategoryValue category) {
		this.category = category;
	}

	public ClientSettingsScreen getParent() {
		return parent;
	}

	public void setParent(ClientSettingsScreen parent) {
		this.parent = parent;
	}

	public PositionUtils getPosition() {
		return position;
	}
}
