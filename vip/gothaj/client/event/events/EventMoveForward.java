package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventMoveForward extends Event {
	boolean reset;

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	public EventMoveForward(boolean reset) {
		this.reset = reset;
	}
}
