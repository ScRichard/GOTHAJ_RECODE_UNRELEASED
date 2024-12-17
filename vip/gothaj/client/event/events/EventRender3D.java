package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventRender3D extends Event{

	private float partialTicks;

	public EventRender3D(float partialTicks) {
		super();
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}
	
	
	
}
