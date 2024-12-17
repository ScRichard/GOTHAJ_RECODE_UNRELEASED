package vip.gothaj.client.modules.ext.movement.flys;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.EventType;
import vip.gothaj.client.event.events.EventMotion;
import vip.gothaj.client.event.events.EventSendPacket;
import vip.gothaj.client.modules.ext.movement.FlightModule;
import vip.gothaj.client.utils.move.MovementUtils;
import vip.gothaj.client.values.Setting;

public class BlocksMCFly extends Setting<FlightModule>{

	private Vec3 startPosition;
	
	private boolean packet;
	
	public BlocksMCFly(FlightModule parent) {
		super(parent);
	}
	
	
	
	@Override
	public void onEnable() {
		super.onEnable();
		startPosition = null;
		packet = true;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1;
	}

	@EventListener
	public void onMotion(EventMotion e) {
		if(e.getType() != EventType.PRE) return;
		
		mc.timer.timerSpeed = 0.4F;
		
	}
	
	@EventListener
	public void onSendPacket(EventSendPacket e) {

		
	}

}
