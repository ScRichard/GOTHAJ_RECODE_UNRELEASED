package vip.gothaj.client.event.events;

import net.minecraft.util.BlockPos;
import vip.gothaj.client.event.Event;


public class EventBlockDamage extends Event {

	float playerRelativeBlockHardness;
	BlockPos pos;
	
	public EventBlockDamage(float playerRelativeBlockHardness, BlockPos pos) {
		this.playerRelativeBlockHardness = playerRelativeBlockHardness;
		this.pos = pos;
	}

	public float getPlayerRelativeBlockHardness() {
		return playerRelativeBlockHardness;
	}

	public void setPlayerRelativeBlockHardness(float playerRelativeBlockHardness) {
		this.playerRelativeBlockHardness = playerRelativeBlockHardness;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}
	
	
}
