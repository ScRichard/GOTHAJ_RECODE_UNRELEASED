package vip.gothaj.client.scripts.api;

import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.move.MovementUtils;

public class PlayerJS extends Wrapper{
	
	public static boolean isMoving() {
		return MovementUtils.isMoving();
	}
	
	public static void jump() {
		mc.thePlayer.jump();
	}
	
	public static void setMotionX(double motionX) {
		mc.thePlayer.motionX = motionX;
	}
	
	public static void setMotionY(double motionY) {
		mc.thePlayer.motionY = motionY;
	}
	
	public static void setMotionZ(double motionZ) {
		mc.thePlayer.motionZ = motionZ;
	}
	
	public static boolean isOnGround() {
		return mc.thePlayer.onGround;
	}
	
	public static double getMotionX() {
		return mc.thePlayer.motionX;
	}
	
	public static double getMotionY() {
		return mc.thePlayer.motionY;
	}
	
	public static double getMotionZ() {
		return mc.thePlayer.motionZ;
	}
	public static double getX() {
		return mc.thePlayer.posX;
	}
	public static double getY() {
		return mc.thePlayer.posY;
	}
	public static double getZ() {
		return mc.thePlayer.posZ;
	}
	public static double getPrevX() {
		return mc.thePlayer.posX;
	}
	public static double getPrevY() {
		return mc.thePlayer.posY;
	}
	public static double getPrevZ() {
		return mc.thePlayer.posZ;
	}
}
