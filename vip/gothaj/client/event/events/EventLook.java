package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventLook extends Event{

	private float yaw, pitch;

	public EventLook(float yaw, float pitch) {
		super();
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
}
