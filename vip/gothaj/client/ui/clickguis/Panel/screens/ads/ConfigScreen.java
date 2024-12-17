package vip.gothaj.client.ui.clickguis.Panel.screens.ads;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.ClickGui;
import vip.gothaj.client.ui.clickguis.Panel.extensions.config.ConfigComponent;
import vip.gothaj.client.ui.clickguis.Panel.screens.Screen;
import vip.gothaj.client.utils.buttons.CheckMark;
import vip.gothaj.client.utils.file.FileUtils.ConfigType;
import vip.gothaj.client.utils.file.config.ConfigFile;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.render.ColorUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.ScrollBar;

public class ConfigScreen extends Screen {

	public ArrayList<ConfigComponent> configuration = new ArrayList();
	public ArrayList<ConfigComponent> cleared = new ArrayList();

	private CheckMark loadVisuals = new CheckMark(11, false);
	private CheckMark onlyMadeByYou = new CheckMark(11, false);
	private CheckMark showOnlyOnlineConfigs = new CheckMark(11, false);

	public ConfigScreen(ClickGui screen) {
		super(screen);

		for (ConfigFile config : Client.INSTANCE.getConfigManager().getConfigs()) {
			configuration.add(new ConfigComponent(this, config));
		}
		this.contentPosition.setHeight(270);
		this.scroll = new ScrollBar(270, 0, 4, 270);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RoundedUtils.drawRoundedRect(contentPosition,
				Client.INSTANCE.getThemeManager().get("Panel Screen Backround Color"), 0, 0, 3, 3);

		RoundedUtils.drawRoundedRect(contentPosition.getX(), contentPosition.getY() - 38, contentPosition.getWidth(),
				38, 0xff121921, 3, 3, 0, 0);
		
		Fonts.drawString("Configs", contentPosition.getX()+5, contentPosition.getY() - 33, -1, "tahoma", 1.3);

		loadVisuals.getPosition().setX(contentPosition.getX2() - 14);
		loadVisuals.getPosition().setY(contentPosition.getY() - 31);
		loadVisuals.drawScreen(mouseX, mouseY);

		Fonts.drawString("Load visuals",
				loadVisuals.getPosition().getX() - Fonts.getWidth("Load visuals", "roboto-medium", 0.95) - 5,
				loadVisuals.getPosition().getY() + 1,
				ColorUtils.mix(Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"),
						Client.INSTANCE.getThemeManager().get("Panel Text Color"),
						loadVisuals.getAnimation().calculateAnimation()),
				"roboto-medium", 0.95);

		onlyMadeByYou.getPosition().setX(contentPosition.getX2() - 14);
		onlyMadeByYou.getPosition().setY(contentPosition.getY() - 18);
		onlyMadeByYou.drawScreen(mouseX, mouseY);

		Fonts.drawString("Only made by you",
				onlyMadeByYou.getPosition().getX() - Fonts.getWidth("Only made by you", "roboto-medium", 0.95) - 5,
				onlyMadeByYou.getPosition().getY() + 1,
				ColorUtils.mix(Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"),
						Client.INSTANCE.getThemeManager().get("Panel Text Color"),
						onlyMadeByYou.getAnimation().calculateAnimation()),
				"roboto-medium", 0.95);
		
		showOnlyOnlineConfigs.getPosition().setX(contentPosition.getX() + 4);
		showOnlyOnlineConfigs.getPosition().setY(contentPosition.getY() - 18);
		showOnlyOnlineConfigs.drawScreen(mouseX, mouseY);
		Fonts.drawString("Show online configs",
				showOnlyOnlineConfigs.getPosition().getX2() + 5,
				showOnlyOnlineConfigs.getPosition().getY() + 1,
				ColorUtils.mix(Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"),
						Client.INSTANCE.getThemeManager().get("Panel Text Color"),
						showOnlyOnlineConfigs.getAnimation().calculateAnimation()),
				"roboto-medium", 0.95);

		this.scroll.track.setX(contentPosition.getX2() - 4);
		this.scroll.track.setY(contentPosition.getY());

		double height = 0;
		RenderUtils.enableScisor();
		RenderUtils.scissor(new ScaledResolution(Minecraft.getMinecraft()), contentPosition.getX(),
				contentPosition.getY(), contentPosition.getWidth(), contentPosition.getHeight());
		int i = 0;
		for (ConfigComponent comp : configuration) {
			
			if(showOnlyOnlineConfigs.isEnabled() && comp.getConfig().getType() != ConfigType.ONLINE) continue;
			else if(!showOnlyOnlineConfigs.isEnabled() && comp.getConfig().getType() == ConfigType.ONLINE) continue;
			
			comp.getPosition().setX(this.contentPosition.getX() + 2 + (comp.getPosition().getWidth() + 2) * i);
			comp.getPosition().setY(this.contentPosition.getY() + 2 + height - this.scroll.scroll);
			i++;
			if (i == 3) {
				i = 0;
				height += comp.getPosition().getHeight() + 2;
			}

			comp.drawScreen(mouseX, mouseY);
		}

		RenderUtils.disableScisor();

		configuration.removeAll(cleared);

		cleared.clear();
		this.scroll.max = height + 2;
		this.scroll.update(mouseY);
		RoundedUtils.drawRoundedRect(this.scroll.thumb,
				Client.INSTANCE.getThemeManager().get("Panel Screen Scroll Thumb Color"), 0.5f);

		if (contentPosition.isInside(mouseX, mouseY))
			this.scroll.scrollMouseInput();
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		loadVisuals.onClick(mouseX, mouseY, button);
		onlyMadeByYou.onClick(mouseX, mouseY, button);
		showOnlyOnlineConfigs.onClick(mouseX, mouseY, button);
		if (contentPosition.isInside(mouseX, mouseY)) {
			for (ConfigComponent c : configuration) {
				c.onClick(mouseX, mouseY, button);
			}
		}
	}

}
