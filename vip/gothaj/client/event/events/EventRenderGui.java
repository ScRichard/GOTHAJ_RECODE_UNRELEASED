package vip.gothaj.client.event.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vip.gothaj.client.event.Event;

public class EventRenderGui extends Event{

	private ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

	public EventRenderGui(ScaledResolution sr) {
		this.sr = sr;
	}

	public ScaledResolution getScaledResolution() {
		return sr;
	}

	public void setScaledResolution(ScaledResolution sr) {
		this.sr = sr;
	}
	
}
