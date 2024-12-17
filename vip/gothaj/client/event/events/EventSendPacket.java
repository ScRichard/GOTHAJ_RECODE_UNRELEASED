package vip.gothaj.client.event.events;

import net.minecraft.network.Packet;
import vip.gothaj.client.event.Event;

public class EventSendPacket extends Event {
	
	private Packet packet;

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public EventSendPacket(Packet packet) {
		this.packet = packet;
	}
	
}
