package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventSilentMoveFix extends Event {
	
	private float forward, strafe;

	public EventSilentMoveFix(float forward, float strafe) {
		super();
		this.forward = forward;
		this.strafe = strafe;
	}

	public float getForward() {
		return forward;
	}

	public void setForward(float forward) {
		this.forward = forward;
	}

	public float getStrafe() {
		return strafe;
	}

	public void setStrafe(float strafe) {
		this.strafe = strafe;
	}
}
