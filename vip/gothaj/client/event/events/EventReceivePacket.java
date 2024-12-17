package vip.gothaj.client.event.events;

import net.minecraft.network.Packet;
import vip.gothaj.client.event.Event;

public class EventReceivePacket extends Event{
	private Packet packet;

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public EventReceivePacket(Packet packet) {
		this.packet = packet;
	}
}
