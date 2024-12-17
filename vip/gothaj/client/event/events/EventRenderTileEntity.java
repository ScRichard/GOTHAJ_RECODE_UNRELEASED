package vip.gothaj.client.event.events;

import net.minecraft.tileentity.TileEntity;
import vip.gothaj.client.event.Event;

public class EventRenderTileEntity extends Event {
	TileEntity entity;

	public TileEntity getEntity() {
		return entity;
	}

	public void setEntity(TileEntity entity) {
		this.entity = entity;
	}

	public EventRenderTileEntity(TileEntity entity) {
		this.entity = entity;
	}
}
