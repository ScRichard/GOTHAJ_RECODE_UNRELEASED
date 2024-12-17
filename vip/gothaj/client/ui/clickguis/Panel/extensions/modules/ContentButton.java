package vip.gothaj.client.ui.clickguis.Panel.extensions.modules;

import net.minecraft.client.gui.GuiScreen;
import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.ClickGui;
import vip.gothaj.client.ui.clickguis.Panel.screens.Screen;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;

public class ContentButton extends Button {
	private String name, icon;
	
	private Screen contentScreent;
	
	private ClickGui parent;
	
	private PositionUtils position = new PositionUtils(0,0,90, 20,1);
	private Animation animation = new Animation(200, AnimationType.EaseInOutSine);
	
	public ContentButton(String name, String icon, Screen contentScreent, ClickGui parent) {
		this.name = name;
		this.icon = icon;
		this.contentScreent = contentScreent;
		this.parent = parent;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		animation.reversed = !position.isInside(mouseX, mouseY) && parent.active != this;
		RoundedUtils.drawRoundedRect(position, Client.INSTANCE.getThemeManager().get("Panel Content Button Color"), 3);
		RoundedUtils.drawRoundedRect(position.getX(), position.getY(), position.getWidth()*animation.calculateAnimation(), position.getHeight(), Client.INSTANCE.getThemeManager().get("Panel Active Color"), 3);
		
		
		
		IconUtils.drawIcon(icon, position.getX()+5, position.getY()+position.getHeight()/2-IconUtils.getIconHeight(icon,0.7)/2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), 0.7);
		
		Fonts.drawString(name, position.getX()+5+15, position.getY()+position.getHeight()/2-Fonts.getHeight("roboto-medium")/2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(position.isInside(mouseX, mouseY) && button == 0) parent.active = this;
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {

	}

	@Override
	public void onKey(int key, char ch) {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	

	public Screen getContentScreent() {
		return contentScreent;
	}

	public void setContentScreent(Screen contentScreent) {
		this.contentScreent = contentScreent;
	}

	public GuiScreen getParent() {
		return parent;
	}

	public void setParent(ClickGui parent) {
		this.parent = parent;
	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(double x, double y) {
		this.position.setX(x);
		this.position.setY(y);
	}
	
	

}
