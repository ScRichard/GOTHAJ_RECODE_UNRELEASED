package vip.gothaj.client.event.events;

import net.minecraft.util.BlockPos;
import vip.gothaj.client.event.Event;

public class EventBlockBreak extends Event {

	BlockPos location;
	
	public EventBlockBreak(BlockPos loc) {
		this.location = loc;
	}

	public BlockPos getLocation() {
		return location;
	}

	public void setLocation(BlockPos location) {
		this.location = location;
	}

	
}
