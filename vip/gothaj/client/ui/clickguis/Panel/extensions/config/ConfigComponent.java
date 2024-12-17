package vip.gothaj.client.ui.clickguis.Panel.extensions.config;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.screens.ads.ConfigScreen;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.file.FileUtils.ConfigType;
import vip.gothaj.client.utils.file.config.ConfigFile;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.render.ColorUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;

public class ConfigComponent extends Button {
	
	private ConfigFile config;
	
	private ConfigScreen parent;
	
	private PositionUtils position = new PositionUtils(0, 0, 95, 45, 1);
	

	private Animation saveHover = new Animation(250, AnimationType.EaseOutQuad);
	private Animation loadHover = new Animation(250, AnimationType.EaseOutQuad);
	private Animation removeHover = new Animation(250, AnimationType.EaseOutQuad);
	
	private PositionUtils save = new PositionUtils(0, 0, 15, 15, 1), load = new PositionUtils(0, 0, 15, 15, 1), remove = new PositionUtils(0, 0, 15, 15, 1);

	public ConfigComponent(ConfigScreen parent, ConfigFile config) {
		this.config = config;
		this.parent = parent;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RoundedUtils.drawRoundedRect(position, 0xff10161d, 4);
		
		Fonts.drawString(getMaximumLenghtOfText(config.getName(), position.getWidth()-15, "tahoma", 1.05, 10), position.getX()+4, position.getY()+4, -1, "tahoma", 1.05);
		
		String user = "user";
		
		Fonts.drawString("@"+user, position.getX()+7, position.getY()+15, Client.INSTANCE.getThemeManager().get("Panel Active Color"), "roboto-medium");
		Fonts.drawString("3 days ago", position.getX()+11 +Fonts.getWidth("@"+user, "roboto-medium") , position.getY()+15, Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"), "roboto-regular", 0.9);
		
		IconUtils.drawIcon("info", position.getX()+2, position.getY2()-IconUtils.getIconHeight("info", 0.65)-2, Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"), 0.65);
		
		save.setX(position.getX2()-17-17-17);
		save.setY(position.getY2()-17);
		load.setX(position.getX2()-17-17);
		load.setY(position.getY2()-17);
		remove.setX(position.getX2()-17);
		remove.setY(position.getY2()-17);

		
		String hoverText = "";
		
		saveHover.reversed = !save.isInside(mouseX, mouseY);
		loadHover.reversed = !load.isInside(mouseX, mouseY);
		removeHover.reversed = !remove.isInside(mouseX, mouseY);
		
		RoundedUtils.drawRoundedRect(save, ColorUtils.mix(0xff10161d, 0xff0c1015, saveHover.calculateAnimation()), 3);
		RoundedUtils.drawRoundedRect(load, ColorUtils.mix(0xff10161d, 0xff0c1015, loadHover.calculateAnimation()), 3);
		RoundedUtils.drawRoundedRect(remove, ColorUtils.mix(0xff10161d, 0xff0c1015, removeHover.calculateAnimation()), 3);
		
		IconUtils.drawIcon("download", save.getX()+3, save.getY()+3, 0xffbbbbbb, 0.7);
		IconUtils.drawIcon("check", load.getX()+3, load.getY()+3, 0xffbbbbbb, 0.7);
		IconUtils.drawIcon("cross", remove.getX()+4, remove.getY()+4, 0xffbbbbbb, 0.6);
		
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(button == 0) {
			if(save.isInside(mouseX, mouseY)) {
				config.write();
			}
			if(load.isInside(mouseX, mouseY)) {
				config.read();
			}
			if(remove.isInside(mouseX, mouseY)) {
				if(config.getType() == ConfigType.LOCAL) {
					parent.cleared.add(this);
					config.remove();
				}
			}
		}
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		
	}
	
	public String getMaximumLenghtOfText(String text, double maxWidth,String font, double scale, int charsEndIndex) {
		
		if(Fonts.getWidth(text, font, scale) > maxWidth) {
			return text.substring(0, charsEndIndex) + "...";
		}
		
		return text;
	}

	@Override
	public void onKey(int key, char ch) {
		
	}

	public ConfigFile getConfig() {
		return config;
	}

	public void setConfig(ConfigFile config) {
		this.config = config;
	}

	public ConfigScreen getParent() {
		return parent;
	}

	public void setParent(ConfigScreen parent) {
		this.parent = parent;
	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(PositionUtils position) {
		this.position = position;
	}
	
}
