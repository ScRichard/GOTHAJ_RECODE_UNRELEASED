package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventGround extends Event{
	
	boolean onGround;
	
	public EventGround(boolean b) {
		onGround = b;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	

}
