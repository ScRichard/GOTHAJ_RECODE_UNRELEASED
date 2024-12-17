package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import java.util.function.Supplier;

import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;

public class PanelSettings extends Button {
	

	ModuleButton parent;
	
	PositionUtils position = new PositionUtils(0, 0, 270, 0, 1);
	
	Supplier<Boolean> visible = () -> true;
	
	public PanelSettings(ModuleButton parent) {
		this.parent = parent;
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

	public ModuleButton getParent() {
		return parent;
	}

	public void setParent(ModuleButton parent) {
		this.parent = parent;
	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(PositionUtils position) {
		this.position = position;
	}

	public boolean isVisible() {
		return visible.get();
	}

	public void setVisible(Supplier<Boolean> visible) {
		this.visible = visible;
	}

}
