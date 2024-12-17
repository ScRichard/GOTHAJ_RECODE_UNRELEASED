package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventJump extends Event{
	
	private float yaw, motion;

	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	public EventJump(float yaw, float f) {
		this.yaw = yaw;
		this.motion = f;
	}
	public float getMotion() {
		return motion;
	}
	public void setMotion(float motion) {
		this.motion = motion;
	}
	
	
	
}
