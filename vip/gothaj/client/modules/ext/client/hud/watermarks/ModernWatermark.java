package vip.gothaj.client.modules.ext.client.hud.watermarks;

import vip.gothaj.client.Client;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventRenderGui;
import vip.gothaj.client.modules.ext.client.HudModule;
import vip.gothaj.client.utils.client.ClientUtils;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.resource.ResourceUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.values.Setting;

public class ModernWatermark extends Setting<HudModule> {

	public ModernWatermark(HudModule parent) {
		super(parent);
	}

	
	@EventListener
	public void onRenderGui(EventRenderGui e) {
		
		if (!parent.watermarkEnabled.isEnabled())
			return;

		String ip = mc.getCurrentServerData() != null ? mc.getCurrentServerData().serverIP : "Unknown";
		
		double width = 10+5 + Fonts.getWidth("Gothaj", "roboto-bold") + 5
				+ Fonts.getWidth(ClientUtils.getVersion(), "roboto-medium", 0.9) + 5 +  Fonts.getWidth(mc.debugFPS+"", "roboto-medium", 0.9)+5 +  Fonts.getWidth(ip, "roboto-medium", 0.9)+5;
		
		RoundedUtils.drawRoundedRect(parent.watermarkLocation.getPosX(),
				parent.watermarkLocation.getPosY(), width, 15, 0x90000000, 2);
		RenderUtils.drawImage(parent.watermarkLocation.getPosX(), parent.watermarkLocation.getPosY(), 15, 15, ResourceUtils.getResource("logo"), -1);

		Fonts.drawString("Gothaj", parent.watermarkLocation.getPosX() + 15,
				parent.watermarkLocation.getPosY() + 15 / 2 - Fonts.getHeight("roboto-bold") / 2, -1,
				"roboto-bold");
		Fonts.drawString(ClientUtils.getVersion(),
				parent.watermarkLocation.getPosX() + 15 + Fonts.getWidth("Gothaj", "roboto-bold") + 5,
				parent.watermarkLocation.getPosY() + 15 / 2 - Fonts.getHeight("roboto-medium", 0.9) / 2, -1,
				"roboto-medium", 0.9);
		Fonts.drawString(mc.debugFPS+"",
				parent.watermarkLocation.getPosX() + 15 + Fonts.getWidth("Gothaj", "roboto-bold") + 5 + Fonts.getWidth(ClientUtils.getVersion(), "roboto-medium", 0.9) + 5,
				parent.watermarkLocation.getPosY() + 15 / 2 - Fonts.getHeight("roboto-medium", 0.9) / 2, -1,
				"roboto-medium", 0.9);
		Fonts.drawString(ip,
				parent.watermarkLocation.getPosX() + 15 + Fonts.getWidth("Gothaj", "roboto-bold") + 5 + Fonts.getWidth(ClientUtils.getVersion(), "roboto-medium", 0.9) + 5+ Fonts.getWidth(mc.debugFPS+"", "roboto-medium", 0.9)+5,
				parent.watermarkLocation.getPosY() + 15 / 2 - Fonts.getHeight("roboto-medium", 0.9) / 2, -1,
				"roboto-medium", 0.9);
		
		if(mc.currentScreen == null) {
			Client.INSTANCE.getBloom().runnable.add(() -> {
				RoundedUtils.drawRoundedRect(parent.watermarkLocation.getPosX(),
						parent.watermarkLocation.getPosY(), width, 15, 0x90000000, 2);
			});
		}
	}

}
