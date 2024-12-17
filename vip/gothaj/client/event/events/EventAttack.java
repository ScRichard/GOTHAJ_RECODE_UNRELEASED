package vip.gothaj.client.event.events;

import net.minecraft.entity.Entity;
import vip.gothaj.client.event.Event;

public class EventAttack extends Event{
	
	private Entity entity;

	public EventAttack(Entity entity) {
		super();
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	

}
