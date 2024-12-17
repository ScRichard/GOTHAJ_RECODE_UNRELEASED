package vip.gothaj.client.utils.rotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.util.vector.Vector2f;

import com.google.common.base.Predicates;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.optifine.reflect.Reflector;
import vip.gothaj.client.event.events.EventSilentMoveFix;
import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.math.Vector3d;

public class RotationUtils extends Wrapper {

	public static boolean customRots;
	public static float serverYaw, serverPitch;
	
	public static float interpolateRotation(final float current, final float predicted, float percentage) {
		final float f = MathHelper.wrapAngleTo180_float(predicted - current);
		if (f <= 10.0f && f >= -10.0f) {
			percentage = 1.0f;
		}
		return current + percentage * f;
	}

	public static double fovFromEntity(Entity en) {
		return ((double) (mc.thePlayer.rotationYaw - fovToEntity(en)) % 360.0D + 540.0D) % 360.0D - 180.0D;
	}

	public static double fovFromPosition(double[] pos) {
		return ((double) (mc.thePlayer.rotationYaw - fovToPosition(pos)) % 360.0D + 540.0D) % 360.0D - 180.0D;
	}

	public static float fovToEntity(Entity ent) {
		double x = ent.posX - mc.thePlayer.posX;
		double z = ent.posZ - mc.thePlayer.posZ;
		double yaw = (float) (MathHelper.atan2(z, x) * 180.0 / 3.141592653589793 - 90F);
		;
		return (float) (yaw);
	}

	public static float fovToPosition(double[] pos) {
		double x = pos[0] - mc.thePlayer.posX;
		double z = pos[1] - mc.thePlayer.posZ;
		double yaw = (float) (MathHelper.atan2(z, x) * 180.0 / 3.141592653589793 - 90F);
		;
		return (float) (yaw);
	}

	public static Entity rayTrace(double range, float[] rotations) {
		if (mc.objectMouseOver.entityHit != null) {
			return mc.objectMouseOver.entityHit;
		}

		Vec3 vec3 = Minecraft.getMinecraft().thePlayer.getPositionEyes(1.0f);

		Vec3 vec31 = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
		Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

		Entity pointedEntity = null;

		float f = 1.0F;
		List<?> list = Minecraft.getMinecraft().theWorld.getEntitiesInAABBexcluding(
				Minecraft.getMinecraft().getRenderViewEntity(),
				Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox()
						.addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range).expand(f, f, f),
				Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
		double d2 = range;

		for (Object o : list) {
			Entity entity1 = (Entity) o;
			float f1 = entity1.getCollisionBorderSize();
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
			MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

			if (axisalignedbb.isVecInside(vec3)) {
				if (d2 >= 0.0D) {
					pointedEntity = entity1;
					d2 = 0.0D;
				}
			} else if (movingobjectposition != null) {
				double d3 = vec3.distanceTo(movingobjectposition.hitVec);

				if (d3 < d2 || d2 == 0.0D) {
					boolean flag2 = false;

					if (Reflector.ForgeEntity_canRiderInteract.exists()) {
						flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
					}

					if (entity1 == Minecraft.getMinecraft().getRenderViewEntity().ridingEntity && !flag2) {
						if (d2 == 0.0D) {
							pointedEntity = entity1;
						}
					} else {
						pointedEntity = entity1;
						d2 = d3;
					}
				}
			}
		}

		return pointedEntity;
	}

	public static float[] positionRotation(final double posX, final double posY, final double posZ,
			final float[] lastRots, final float yawSpeed, final float pitchSpeed, final boolean random) {
		final double x = posX - mc.thePlayer.posX;
		final double y = posY - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		final double z = posZ - mc.thePlayer.posZ;
		final float calcYaw = (float) (MathHelper.atan2(z, x) * 180.0 / 3.141592653589793 - 90.0);
		final float calcPitch = (float) (-(MathHelper.atan2(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0
				/ 3.141592653589793));
		float yaw = updateRotation(lastRots[0], calcYaw, yawSpeed);
		float pitch = updateRotation(lastRots[1], calcPitch, pitchSpeed);
		if (random) {
			yaw += (float) ThreadLocalRandom.current().nextGaussian();
			pitch += (float) ThreadLocalRandom.current().nextGaussian();
		}
		return new float[] { yaw, pitch };
	}

	public static boolean lookingAtBlock(BlockPos blockPos, float yaw, float pitch, EnumFacing enumFacing,
			boolean strict) {
		MovingObjectPosition movingObjectPosition = mc.thePlayer
				.rayTraceCustom(mc.playerController.getBlockReachDistance(), mc.timer.renderPartialTicks, yaw, pitch);

		if (movingObjectPosition == null)
			return false;

		Vec3 hitVec = movingObjectPosition.hitVec;
		if (hitVec == null)
			return false;

		return movingObjectPosition.getBlockPos().equals(blockPos)
				&& (!strict || (movingObjectPosition.sideHit == enumFacing && movingObjectPosition.sideHit != null));
	}

	public static float[] getRotationsToBlock(BlockPos blockPos, EnumFacing enumFacing) {
		double x = blockPos.getX() + 0.5 - mc.thePlayer.posX + (double) enumFacing.getFrontOffsetX() / 2;
		double z = blockPos.getZ() + 0.5 - mc.thePlayer.posZ + (double) enumFacing.getFrontOffsetZ() / 2;
		double y = (blockPos.getY() + 0.5);
		double dist = mc.thePlayer.getDistance(blockPos.getX() + 0.5 + (double) enumFacing.getFrontOffsetX() / 2,
				blockPos.getY(), blockPos.getZ() + 0.5 + (double) enumFacing.getFrontOffsetZ() / 2);
		y += 0.5;
		double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - y;
		double d3 = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90;
		float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
		if (yaw < 0.0F) {
			yaw += 360f;
		}
		return new float[] { yaw, pitch };
	}

	public static float getYawBasedPitch(BlockPos blockPos, EnumFacing facing, float currentYaw, float lastPitch) {
		float validPitch = lastPitch;

		float maxPitch = 84f;

		if (facing == EnumFacing.UP) {
			maxPitch = 84f;
		}

		float increment = (float) Math.random();

		for (float i = maxPitch; i > 65; i -= increment) {
			MovingObjectPosition ray = rayCast(1, new float[] { currentYaw, i },
					mc.playerController.getBlockReachDistance(), 2);
			if (ray.getBlockPos().equalsBlockPos(blockPos) && ray.sideHit == facing) {
				return i;
			}
		}

		return validPitch;
	}

	public static MovingObjectPosition rayCast(final float partialTicks, final float[] rots, final double range,
			final double hitBoxExpand) {
		MovingObjectPosition objectMouseOver = null;
		final Entity entity = mc.getRenderViewEntity();
		if (entity != null && mc.theWorld != null) {
			mc.mcProfiler.startSection("pick");
			mc.pointedEntity = null;
			double d0 = range;
			objectMouseOver = entity.rayTraceCustom(d0, partialTicks, rots[0], rots[1]);
			double d2 = d0;
			final Vec3 vec3 = entity.getPositionEyes(partialTicks);
			boolean flag = false;
			final boolean flag2 = true;
			if (mc.playerController.extendedReach()) {
				d0 = 6.0;
				d2 = 6.0;
			} else {
				if (d0 > 3.0) {
					flag = true;
				}
			}
			if (objectMouseOver != null) {
				d2 = objectMouseOver.hitVec.distanceTo(vec3);
			}
			final Vec3 vec4 = entity.getLookCustom(partialTicks, rots[0], rots[1]);
			final Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
			Entity pointedEntity = null;
			Vec3 vec6 = null;
			final float f = 1.0f;
			final List list = mc.theWorld
					.getEntitiesInAABBexcluding(
							entity, entity.getEntityBoundingBox()
									.addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand(f, f, f),
							Predicates.and(EntitySelectors.NOT_SPECTATING));
			double d3 = d2;
			final AxisAlignedBB realBB = null;
			for (int i = 0; i < list.size(); ++i) {
				final Entity entity2 = (Entity) list.get(i);
				final float f2 = (float) (entity2.getCollisionBorderSize() + hitBoxExpand);
				final AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand(f2, f2, f2);
				final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
				if (axisalignedbb.isVecInside(vec3)) {
					if (d3 >= 0.0) {
						pointedEntity = entity2;
						vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.hitVec);
						d3 = 0.0;
					}
				} else if (movingobjectposition != null) {
					final double d4 = vec3.distanceTo(movingobjectposition.hitVec);
					if (d4 < d3 || d3 == 0.0) {
						boolean flag3 = false;
						if (Reflector.ForgeEntity_canRiderInteract.exists()) {
							flag3 = Reflector.callBoolean(entity2, Reflector.ForgeEntity_canRiderInteract,
									new Object[0]);
						}
						if (entity2 == entity.ridingEntity && !flag3) {
							if (d3 == 0.0) {
								pointedEntity = entity2;
								vec6 = movingobjectposition.hitVec;
							}
						} else {
							pointedEntity = entity2;
							vec6 = movingobjectposition.hitVec;
							d3 = d4;
						}
					}
				}
			}
			if (pointedEntity != null && flag && vec3.distanceTo(vec6) > range) {
				pointedEntity = null;
				objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec6, null,
						new BlockPos(vec6));
			}
			if (pointedEntity != null && (d3 < d2 || objectMouseOver == null)) {
				objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
			}
		}
		return objectMouseOver;
	}

	// set anges retard dejvak

	public static float[] getFixedRotation(final float[] rotations, final float[] lastRotations) {
		float yaw = rotations[0];
		float pitch = rotations[1];

		float lastYaw = lastRotations[0];
		float lastPitch = lastRotations[1];

		float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
		float f1 = f * f * f * 8F;

		float deltaYaw = yaw - lastYaw;
		float deltaPitch = pitch - lastPitch;

		float fixedDeltaYaw = deltaYaw - (deltaYaw % f1);
		float fixedDeltaPitch = deltaPitch - (deltaPitch % f1);

		float fixedYaw = lastYaw + fixedDeltaYaw;
		float fixedPitch = lastPitch + fixedDeltaPitch;

		return new float[] { fixedYaw, fixedPitch };
	}

	public static double getDirectionWrappedTo90() {
		float rotationYaw = mc.thePlayer.rotationYaw;

		if (mc.thePlayer.moveForward < 0F && mc.thePlayer.moveStrafing == 0F)
			rotationYaw += 180F;

		final float forward = 1F;

		if (mc.thePlayer.moveStrafing > 0F)
			rotationYaw -= 90F * forward;
		if (mc.thePlayer.moveStrafing < 0F)
			rotationYaw += 90F * forward;

		return Math.toRadians(rotationYaw);
	}

	public static float[] getDirectionToBlock(final double x, final double y, final double z,
			final EnumFacing enumfacing) {
		final EntityEgg face = new EntityEgg(mc.theWorld);
		face.posX = x + 0.5D;
		face.posY = y + 0.5D;
		face.posZ = z + 0.5D;
		face.posX += (double) enumfacing.getDirectionVec().getX() * 0.5D;
		face.posY += (double) enumfacing.getDirectionVec().getY() * 0.5D;
		face.posZ += (double) enumfacing.getDirectionVec().getZ() * 0.5D;
		return getRotationFromPosition(face.posX, face.posY, face.posZ);
	}

	public static double getMouseGCD() {
		float sens = (Minecraft.getMinecraft()).gameSettings.mouseSensitivity * 0.6F + 0.2F;
		float pow = sens * sens * sens * 8.0F;
		return pow * 0.15D;
	}

	public static float[] getNormalAuraRotations(float yaw, float pitch, Entity target, double x, double y, double z,
			float yawSpeed, float pitchSpeed, boolean hitVec) {
		if (target == null) {
			yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
			pitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
		} else {
			pitchSpeed *= 0.5;

			if (yawSpeed < 0) {
				yawSpeed *= -1;
			}

			if (pitchSpeed < 0) {
				pitchSpeed *= -1;
			}

			float sYaw = (float) updateRotation((float) yaw, (float) getInstantTargetRotation(target, x, y, z, hitVec)[0],
					yawSpeed);
			float sPitch = (float) updateRotation((float) pitch, (float) getInstantTargetRotation(target, x, y, z, hitVec)[1],
					pitchSpeed);
			yaw = updateRotation(yaw, sYaw, 360);
			pitch = updateRotation(pitch, sPitch, 360);

			if (pitch > 90) {
				pitch = 90;
			} else if (pitch < -90) {
				pitch = -90;
			}
		}

		return new float[] { yaw, pitch };
	}

	public static float updateRotation(float current, float intended, float factor) {
		float var4 = MathHelper.wrapAngleTo180_float(intended - current);

		if (var4 > factor) {
			var4 = factor;
		}

		if (var4 < -factor) {
			var4 = -factor;
		}

		return current + var4;
	}

	public static float[] getInstantTargetRotation(Entity ent, double x, double y, double z, boolean hitVec) {
		double eyeHeight = ent.getEyeHeight();
		double playerY = mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
		if (playerY >= y + eyeHeight) {
			y = y + eyeHeight;
			y -= 0.4;
		} else if (playerY < y) {

		} else {
			y = playerY;
			y -= 0.4;
		}
		
		if(hitVec) {

		Vec3 best = getBestHitVec(ent);

		double nearest = 15.0;
		AxisAlignedBB boundingBox = ent.getEntityBoundingBox();
		for (double x1 = boundingBox.minX; x1 <= boundingBox.maxX; x1 += 0.07) {
			for (double z1 = boundingBox.minZ; z1 <= boundingBox.maxZ; z1 += 0.07) {
				for (double y1 = boundingBox.minY; y1 <= boundingBox.maxY; y1 += 0.07) {
					final Vec3 pos = new Vec3(x1, y1, z1);
					if (mc.thePlayer.canPosBeSeen(pos)) {
						Vec3 eyes = mc.thePlayer.getPositionEyes(1.0f);
						double dist = Math.sqrt(Math.pow(x1 - eyes.xCoord, 2.0) + Math.pow(y1 - eyes.yCoord, 2.0)
								+ Math.pow(z1 - eyes.zCoord, 2.0));
						if (dist <= nearest) {
							nearest = dist;
							best = pos;
						}
					}
				}
			}
		}

		return getRotationFromPosition(best.xCoord, y, best.zCoord);
		}else {
			return getRotationFromPosition(x, y, z);
		}
	}

	public static Vec3 getBestHitVec(final Entity entity) {
		final Vec3 positionEyes = mc.thePlayer.getPositionEyes(1f);
		final float f11 = entity.getCollisionBorderSize();
		final AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(f11, f11, f11);
		final double ex = MathHelper.clamp_double(positionEyes.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
		final double ey = MathHelper.clamp_double(positionEyes.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
		final double ez = MathHelper.clamp_double(positionEyes.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);
		return new Vec3(ex, ey - 0.4, ez);
	}

	public static float[] getRotationFromPosition(double x, double y, double z) {
		double xDiff = x - (Minecraft.getMinecraft()).thePlayer.posX;
		double zDiff = z - (Minecraft.getMinecraft()).thePlayer.posZ;
		double yDiff = y - (Minecraft.getMinecraft()).thePlayer.posY - 1.2D;
		double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
		return new float[] { yaw, pitch };
	}

	public static Vec3 getVec3(final BlockPos pos, final EnumFacing face) {
		double x = pos.getX() + 0.500;
		double y = pos.getY() + 0.500;
		double z = pos.getZ() + 0.500;
		x += face.getFrontOffsetX() / 2.0;
		z += face.getFrontOffsetZ() / 2.0;
		y += face.getFrontOffsetY() / 2.0;
		if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
			x += (new Random().nextDouble() / 2) - 0.25;
			z += (new Random().nextDouble() / 2) - 0.25;
		} else {
			y += (new Random().nextDouble() / 2) - 0.25;
		}
		if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
			z += (new Random().nextDouble() / 2) - 0.25;
		}
		if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
			x += (new Random().nextDouble() / 2) - 0.25;
		}
		return new Vec3(x, y, z);
	}

	public static float[] getRotationsFromPositionToPosition(double startX, double startY, double startZ,
			final double posX, final double posY, final double posZ) {
		Vector3d to = new Vector3d(posX, posY, posZ);
		Vector3d from = new Vector3d(startX, startY, startZ);
		final Vector3d diff = to.subtract(from);
		final double distance = Math.hypot(diff.getX(), diff.getZ());
		final float yaw = (float) (Math.toDegrees(MathHelper.atan2(diff.getZ(), diff.getX()))) - 90.0F;
		final float pitch = (float) (-(Math.toDegrees(MathHelper.atan2(diff.getY(), distance))));
		return new float[] { yaw, pitch };
	}

	public static float[] getNormalRotationsFromPosition(double x, double y, double z, float currentYaw,
			float currentPitch, float yawSpeed, float pitchSpeed) {
		if (yawSpeed < 0) {
			yawSpeed *= -1;
		}

		if (pitchSpeed < 0) {
			pitchSpeed *= -1;
		}

		float sYaw = (float) updateRotation((float) currentYaw, (float) getRotationFromPosition(x, y, z)[0], yawSpeed);
		float sPitch = (float) updateRotation((float) currentPitch, (float) getRotationFromPosition(x, y, z)[1],
				pitchSpeed);
		currentYaw = updateRotation(currentYaw, sYaw, 360);
		currentPitch = updateRotation(currentPitch, sPitch, 360);

		if (currentPitch > 90) {
			currentPitch = 90;
		} else if (currentPitch < -90) {
			currentPitch = -90;
		}

		return new float[] { currentYaw, currentPitch };
	}
	
	public static void applyMoveFix(EventSilentMoveFix e, float serverYaw) {
		float fov = ((mc.thePlayer.rotationYaw - serverYaw) % 360.0F + 540.0F) % 360.0F - 180.0F;
		
		int dir = Math.round(fov / 45);
		
		float forward = 0;
		float strafe = 0;
		
		switch(dir) {
		case 0:
			forward += e.getForward();
			strafe += e.getStrafe();
			break;
		case 1:
			forward += e.getForward();
			strafe -= e.getForward();
			forward += e.getStrafe();
			strafe += e.getStrafe();
			break;
		case 2:
			strafe -= e.getForward();
			forward += e.getStrafe();
			break;
		case 3:
			forward -= e.getForward();
			strafe -= e.getForward();
			forward += e.getStrafe();
			strafe -= e.getStrafe();
			break;
		case -1:
			forward += e.getForward();
			strafe += e.getForward();
			forward -= e.getStrafe();
			strafe += e.getStrafe();
			break;
		case -2:
			forward = -e.getStrafe();
			strafe = e.getForward();
			break;
		case -3:
			forward -= e.getForward();
			strafe += e.getForward();
			forward -= e.getStrafe();
			strafe -= e.getStrafe();
			break;
		case -4:
		case 4:
			forward = -e.getForward();
			strafe = -e.getStrafe();
			break;
		}
		
		e.setStrafe(strafe);
		e.setForward(forward);
	}
	
	
	
}
