package vip.gothaj.client.modules.ext.client.hud.watermarks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventRenderGui;
import vip.gothaj.client.modules.ext.client.HudModule;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.move.MovementUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.resource.ResourceUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.values.Setting;

public class LogoWatermark extends Setting<HudModule>{

	public LogoWatermark(HudModule parent) {
		super(parent);
	}

	
	@EventListener
	public void onrenderGui(EventRenderGui e) {
		if (!this.getParent().watermarkEnabled.isEnabled())
			return;
		
		RenderUtils.drawImage(parent.watermarkLocation.getPosX(), parent.watermarkLocation.getPosY(), 120, 50, ResourceUtils.getResource("logo_long"), -1);

	}
}
