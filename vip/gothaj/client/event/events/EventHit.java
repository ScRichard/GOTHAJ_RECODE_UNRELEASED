package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventHit extends Event {
	boolean forced;

	public EventHit(boolean forced) {
		super();
		this.forced = forced;
	}

	public void setForced(boolean forced) {
		this.forced = forced;
	}

	public boolean isForced() {
		return this.forced;
	}
}
