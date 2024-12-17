package vip.gothaj.client.ui.clickguis.Panel.screens;

import vip.gothaj.client.ui.clickguis.Panel.ClickGui;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.utils.ui.ScrollBar;

public class Screen extends Button{

	public ClickGui screen;
	
	public ScrollBar scroll;
	
	public PositionUtils contentPosition = new PositionUtils(100,40,298,308,1);

	public Screen(ClickGui screen) {
		this.screen = screen;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {

	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {

	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {

	}

	@Override
	public void onKey(int key, char ch) {

	}

}
