package vip.gothaj.client.utils.rotations;

import org.apache.commons.lang3.RandomUtils;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.client.ChatUtils;

public class KillauraUtils extends Wrapper {
	
	public static float[] getRotationForPosition(double x, double y, double z) {
		
		double diffX = x - mc.thePlayer.posX;
		double diffY = y - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		double diffZ = z - mc.thePlayer.posZ;
		
		float yaw = (float) ((float) Math.atan2(diffZ, diffX) * 180F / Math.PI - 90F);
		float pitch = (float) (-((float) Math.atan2(diffY, Math.sqrt(diffX*diffX + diffZ*diffZ))) * 180F / Math.PI);
		
		return new float[] { yaw, pitch};
		
	}
	
	public static float[] getRotationForPosition(double x, double y, double z, float lastYaw, float lastPitch, double yawSpeed, double pitchSpeed, String smooth ,double smoothSpeedYaw, double smoothSpeedPitch) {
		
		float[] rots = getRotationForPosition(x,y,z);
		
		rots[0] = updateRotation((float) lastYaw, rots[0], (float) yawSpeed);
		rots[1] = updateRotation((float) lastPitch, rots[1], (float) pitchSpeed);

		float[] fov = fovToPosition(new float[] {
				lastYaw, lastPitch
		},x,y,z);
		
		switch(smooth.toLowerCase()) {
		case "interpolation":
			rots[0] = (float) (lastYaw + (rots[0] - lastYaw) * smoothSpeedYaw);
			rots[1] = (float) (lastPitch + (rots[1] - lastPitch) * smoothSpeedPitch);
			break;
		case "linear":
			
			
			rots[0] = lastYaw;
			rots[1] = lastPitch;
			
			if(fov[0] < -1) rots[0] += smoothSpeedYaw;
			if(fov[0] > 1) rots[0] -= smoothSpeedYaw;

			if(fov[1] < -1) rots[1] += smoothSpeedPitch;
			if(fov[1] > 1) rots[1] -= smoothSpeedPitch;
			
			
			if(fov[0] < 1 && fov[0] > -1) {
				rots[0] = lastYaw;
			}
			if(fov[1] < 1 && fov[1] > -1) {
				rots[1] = lastPitch;
			}
			
			break;
		case "bezier":
			
			if(fov[0] < -1) rots[0] = (float) (lastYaw+(Math.abs(fov[0]) / 180) * (Math.abs(fov[0]) / 180) * (3.0f - 2.0f *(Math.abs(fov[0]) / 180)) * smoothSpeedYaw*15);
			if(fov[0] > 1) rots[0] = (float) (lastYaw-(Math.abs(fov[0]) / 180) * (Math.abs(fov[0]) / 180) * (3.0f - 2.0f *(Math.abs(fov[0]) / 180)) * smoothSpeedYaw*15);
			
			
			if(fov[1] < -1) rots[1] = (float) (lastPitch+(Math.abs(fov[1]) / 90) * (Math.abs(fov[1]) / 90) * (3.0f - 2.0f *(Math.abs(fov[1]) / 90)) * smoothSpeedPitch*15);
			if(fov[1] > 1) rots[1] = (float) (lastPitch-(Math.abs(fov[1]) / 90) * (Math.abs(fov[1]) / 90) * (3.0f - 2.0f *(Math.abs(fov[1]) / 90)) * smoothSpeedPitch*15);
			
			break;
		}
		
		return rots;
	}
	
	public static float[] getRotationForEntityBest(final Entity e, float lastYaw, float lastPitch, double yawSpeed, double pitchSpeed, String smooth,double smoothSpeedYaw, double smoothSpeedPitch) {
		Vec3 vector = getBestHitVec(e);
		return getRotationForPosition(vector.xCoord, vector.yCoord, vector.zCoord, lastYaw, lastPitch, yawSpeed, pitchSpeed, smooth, smoothSpeedYaw, smoothSpeedPitch);
	}
	
	public static Vec3 getBestHitVec(final Entity entity) {
		final Vec3 positionEyes = mc.thePlayer.getPositionEyes(1f);
		final float f11 = entity.getCollisionBorderSize();
		final AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(f11, f11, f11);
		final double ex = MathHelper.clamp_double(positionEyes.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
		final double ey = MathHelper.clamp_double(positionEyes.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
		final double ez = MathHelper.clamp_double(positionEyes.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);
		return new Vec3(ex, ey, ez);
	}
	
	public static float[] fovToPosition(float[] lastRots,double x, double y, double z) {
		
		float[] rots = getRotationForPosition(x,y,z);
		
		float xAxis = ((lastRots[0] - rots[0]) % 360.0F + 540.0F) % 360.0F - 180.0F;
		float yAxis = lastRots[1] - rots[1];
		
		return new float[] {
				xAxis, yAxis
		};
	}
	
	private static float updateRotation(float last, float calculated, float speed) {
        float f = MathHelper.wrapAngleTo180_float(calculated - last);

        if (f > speed)
        {
            f = speed;
        }
        if (f < -speed)
        {
            f = -speed;
        }
        
        return last + f;
    }
	
	public static long calculateButterfly(int min, int max) {
		
		int diff = max-min;
		
		double devided = diff/2;
		
		double first = RandomUtils.nextDouble(min, min+diff);
		double last = RandomUtils.nextDouble(min+diff, max);
		
		return (long) (min + Math.floor(last-first));
	}

}
