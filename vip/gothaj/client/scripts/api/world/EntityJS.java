package vip.gothaj.client.scripts.api.world;

import net.minecraft.entity.Entity;
import vip.gothaj.client.utils.Wrapper;

public class EntityJS extends Wrapper {
	
	private Entity entity;
	
	public EntityJS(Entity e) {
		this.entity = e;
	}
	public double getX() {
		return entity.posX;
	}
	public double getY() {
		return entity.posY;
	}
	public double getZ() {
		return entity.posZ;
	}
	public double getPrevX() {
		return entity.lastTickPosX;
	}
	public double getPrevY() {
		return entity.lastTickPosY;
	}
	public double getPrevZ() {
		return entity.lastTickPosZ;
	}
	
	public boolean isMe() {
		return this.entity == mc.thePlayer;
	}
	
	public Entity get() {
		return entity;
	}
	
}
