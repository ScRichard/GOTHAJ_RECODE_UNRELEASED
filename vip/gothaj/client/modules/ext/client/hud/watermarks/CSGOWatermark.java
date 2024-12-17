package vip.gothaj.client.modules.ext.client.hud.watermarks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventRenderGui;
import vip.gothaj.client.modules.ext.client.HudModule;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.move.MovementUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.values.Setting;

public class CSGOWatermark extends Setting<HudModule> {

	public CSGOWatermark(HudModule parent) {
		super(parent);
	}

	@EventListener
	public void onrenderGui(EventRenderGui e) {
		if (!this.getParent().watermarkEnabled.isEnabled())
			return;

		String ip = mc.getCurrentServerData() != null ? mc.getCurrentServerData().serverIP : "Unknown";
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);

		double width = 5 + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10 + Fonts.getWidth(ip, "roboto-medium") + 10
				+ Fonts.getWidth(formattedDate, "roboto-medium") + 5 + 10
				+ Fonts.getWidth(MovementUtils.getBlocksPerSecond() + "", "roboto-medium");

		RoundedUtils.drawRoundedRect(this.getParent().watermarkLocation.getPosX(),
				this.getParent().watermarkLocation.getPosY(), width, 15, 0x90000000, 2);

		Fonts.drawString("GOTHAJ", this.getParent().watermarkLocation.getPosX() + 5,
				this.getParent().watermarkLocation.getPosY() + 7.5 - Fonts.getHeight("tahoma", 1.1) / 2, -1, "tahoma",
				1.1);

		Fonts.drawString(ip,
				this.getParent().watermarkLocation.getPosX() + 5 + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10,
				this.getParent().watermarkLocation.getPosY() + 7.5 - Fonts.getHeight("roboto-medium") / 2, -1,
				"roboto-medium");
		Fonts.drawString(formattedDate,
				this.getParent().watermarkLocation.getPosX() + 5 + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10
						+ Fonts.getWidth(ip, "roboto-medium") + 10,
				this.getParent().watermarkLocation.getPosY() + 7.5 - Fonts.getHeight("roboto-medium") / 2, -1,
				"roboto-medium");

		Fonts.drawString(MovementUtils.getBlocksPerSecond() + "",
				this.getParent().watermarkLocation.getPosX() + 5 + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10
						+ Fonts.getWidth(ip, "roboto-medium") + 10 + Fonts.getWidth(formattedDate, "roboto-medium")
						+ 10,
				this.getParent().watermarkLocation.getPosY() + 7.5 - Fonts.getHeight("roboto-medium") / 2, -1,
				"roboto-medium");

		RenderUtils.drawLine(
				this.getParent().watermarkLocation.getPosX() + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10 + Fonts.getWidth(ip, "roboto-medium") + 10, this.getParent().watermarkLocation.getPosY()+3,
				this.getParent().watermarkLocation.getPosX() + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10 + Fonts.getWidth(ip, "roboto-medium") + 10, this.getParent().watermarkLocation.getPosY()+12, 0x30ffffff,
				-1, 0.5);
		RenderUtils.drawLine(
				this.getParent().watermarkLocation.getPosX() + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10, this.getParent().watermarkLocation.getPosY()+3,
				this.getParent().watermarkLocation.getPosX() + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10, this.getParent().watermarkLocation.getPosY()+12, 0x30ffffff,
				-1, 0.5);
		RenderUtils.drawLine(
				this.getParent().watermarkLocation.getPosX() + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10 + Fonts.getWidth(ip, "roboto-medium") + 10
				+ Fonts.getWidth(formattedDate, "roboto-medium") +10, this.getParent().watermarkLocation.getPosY()+3,
				this.getParent().watermarkLocation.getPosX() + Fonts.getWidth("GOTHAJ", "tahoma", 1.1) + 10 + Fonts.getWidth(ip, "roboto-medium") + 10
				+ Fonts.getWidth(formattedDate, "roboto-medium")+10, this.getParent().watermarkLocation.getPosY()+12, 0x30ffffff,
				-1, 0.5);

	}

}
