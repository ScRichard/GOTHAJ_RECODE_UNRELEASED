package vip.gothaj.client.utils.target;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import vip.gothaj.client.Client;
import vip.gothaj.client.modules.ext.combat.KillAuraModule;
import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.client.Timer;
import vip.gothaj.client.utils.inventory.InventoryUtils;
import vip.gothaj.client.utils.rotations.RotationUtils;
import vip.gothaj.client.values.settings.MultipleBooleanValue;

public class TargetUtils extends Wrapper {
	public static int size = 0;

	public static Timer timer = new Timer();
	public static EntityLivingBase getTarget(double range, MultipleBooleanValue targetmod, String attackMode, int switchTimer,
			boolean teams, boolean troughWalls, boolean dead, boolean invisible) {

		EntityLivingBase target = null;
		List<Entity> targets = (List<Entity>) (mc).theWorld.loadedEntityList.stream()
				.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
		targets = (List<Entity>) targets.stream()
				.filter(entity -> (mc.thePlayer.getDistanceToEntity(entity) < range && entity != (mc).thePlayer)
						&& !(entity instanceof EntityArmorStand))
				.collect(Collectors.toList());

		targets.removeIf(entity -> (!invisible && entity.isInvisible()));
		targets.removeIf(entity -> (!dead && ((((EntityLivingBase) entity).getHealth() <= 0) || entity.isDead)));
		targets.removeIf(entity -> (!targetmod.get("Players") && entity instanceof EntityPlayer) || (!targetmod.get("Animals") && entity instanceof EntityAnimal) || (!targetmod.get("Mobs") && entity instanceof EntityMob) || (!targetmod.get("Villagers") && entity instanceof EntityVillager));

		KillAuraModule ka = (KillAuraModule) Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
		targets.removeIf(
				entity -> (Math
						.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0]
								- Minecraft.getMinecraft().thePlayer.rotationYaw)
						% 360.0F > 180.0F
								? 360.0F - Math.abs(
										RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0]
												- Minecraft.getMinecraft().thePlayer.rotationYaw)
										% 360.0F
								: Math.abs(
										RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0]
												- Minecraft.getMinecraft().thePlayer.rotationYaw)
										% 360.0F) > ka.fov.getValue());

		/*
		if (Client.INSTANCE.getModuleManager().getModule(ReverseFriendsModule.class).isEnabled()) {
			targets.removeIf(entity -> !ReverseFriendsModule.allowed.contains(entity.getName()));
		} else {
			if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
				for (String friend : FriendsCommand.friends) {
					targets.removeIf(entity -> entity.getName().equalsIgnoreCase(friend));
				}
			}
			targets.removeIf(entity -> (teams && mc.thePlayer.isOnSameTeam((EntityLivingBase) entity)));
			try {
				targets.removeIf(
						entity -> (teams && entity instanceof EntityPlayer && isInSameTeam((EntityPlayer) entity)));
			} catch (Exception ex) {

			}
		}*/

		targets.removeIf(entity -> (!troughWalls && !mc.thePlayer.canEntityBeSeen(entity)));
		switch (ka.sort.getMode().toLowerCase()) {
		case "smart":
			if (targets.size() > 1) {
				boolean isPlayer = false;
				for (int i = 0; i < targets.size(); i++) {
					Entity ent = targets.get(i);
					if (ent != null) {
						if (!(ent instanceof EntityPlayer)) {
							if (isPlayer) {
								targets.remove(ent);
							}
						} else {
							isPlayer = true;
						}
					}
				}
			}
			targets.sort(Comparator.comparingDouble(entity -> getSmartSort((Entity) entity)));
			break;
		case "strongest player":
			if (targets.size() > 1) {
				boolean isPlayer = false;
				for (int i = 0; i < targets.size(); i++) {
					Entity ent = targets.get(i);
					if (ent != null) {
						if (!(ent instanceof EntityPlayer)) {
							if (isPlayer) {
								targets.remove(ent);
							}
						} else {
							isPlayer = true;
						}
					}
				}
			}
			targets.sort(Comparator.comparingDouble(entity -> getStrongestPlayerSort((Entity) entity)));
			break;
		case "distance":
			targets.sort(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)));
			break;
		case "health":
			targets.sort(Comparator
					.comparingDouble(entity -> entity instanceof EntityPlayer ? ((EntityPlayer) entity).getHealth()
							: mc.thePlayer.getDistanceToEntity(entity)));
			break;
		}

		if (!targets.isEmpty()) {
			switch ((attackMode).toLowerCase()) {
			case "off":
				target = (EntityLivingBase) targets.get(0);
				break;
			case "timer":

				if (timer.hasTimeElapsed(switchTimer, true))
					size++;

				if (targets.size() > 0 && size >= targets.size())
					size = 0;

				target = (EntityLivingBase) targets.get(size);
				break;
			case "hurt time":
				if (targets.size() > 0) {
					targets.sort(Comparator.comparingDouble(entity -> entity.hurtResistantTime));
					target = (EntityLivingBase) targets.get(0);
				}
				break;
			}
		}

		return target;
	}

	public static EntityLivingBase getTargetBox(double range, MultipleBooleanValue targetmod, String attackMode, int switchTimer,
			boolean teams, boolean troughWalls, boolean dead, boolean invisible) {

		EntityLivingBase target = null;
		List<Entity> targets = (List<Entity>) (mc).theWorld.loadedEntityList.stream()
				.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
		targets = (List<Entity>) targets.stream().filter(entity -> getDistanceToEntityBox(entity) < range
				&& entity != (mc).thePlayer && !(entity instanceof EntityArmorStand)).collect(Collectors.toList());

		targets.removeIf(entity -> (!invisible && entity.isInvisible()));
		targets.removeIf(entity -> (!dead && ((((EntityLivingBase) entity).getHealth() <= 0) || entity.isDead)));
		targets.removeIf(entity -> (!targetmod.get("Players") && entity instanceof EntityPlayer) || (!targetmod.get("Animals") && entity instanceof EntityAnimal) || (!targetmod.get("Mobs") && entity instanceof EntityMob) || (!targetmod.get("Villagers") && entity instanceof EntityVillager));

		KillAuraModule ka = (KillAuraModule) Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
		targets.removeIf(
				entity -> (Math
						.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0]
								- Minecraft.getMinecraft().thePlayer.rotationYaw)
						% 360.0F > 180.0F
								? 360.0F - Math.abs(
										RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0]
												- Minecraft.getMinecraft().thePlayer.rotationYaw)
										% 360.0F
								: Math.abs(
										RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0]
												- Minecraft.getMinecraft().thePlayer.rotationYaw)
										% 360.0F) > ka.fov.getValue());

		/*
		
		if (Client.INSTANCE.getModuleManager().getModule(ReverseFriendsModule.class).isEnabled()) {
			targets.removeIf(entity -> !ReverseFriendsModule.allowed.contains(entity.getName()));
		} else {
			if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
				for (String friend : FriendsCommand.friends) {
					targets.removeIf(entity -> entity.getName().equalsIgnoreCase(friend));
				}
			}
			targets.removeIf(entity -> (teams && mc.thePlayer.isOnSameTeam((EntityLivingBase) entity)));
			try {
				targets.removeIf(
						entity -> (teams && entity instanceof EntityPlayer && isInSameTeam((EntityPlayer) entity)));
			} catch (Exception ex) {

			}
		}*/
		targets.removeIf(entity -> (!troughWalls && !mc.thePlayer.canEntityBeSeen(entity)));
		switch (ka.sort.getMode().toLowerCase()) {
		case "smart":
			if (targets.size() > 1) {
				boolean isPlayer = false;
				for (int i = 0; i < targets.size(); i++) {
					Entity ent = targets.get(i);
					if (ent != null) {
						if (!(ent instanceof EntityPlayer)) {
							if (isPlayer) {
								targets.remove(ent);
							}
						} else {
							isPlayer = true;
						}
					}
				}
			}
			targets.sort(Comparator.comparingDouble(entity -> getSmartSort((Entity) entity)));
			break;
		case "strongest player":
			if (targets.size() > 1) {
				boolean isPlayer = false;
				for (int i = 0; i < targets.size(); i++) {
					Entity ent = targets.get(i);
					if (ent != null) {
						if (!(ent instanceof EntityPlayer)) {
							if (isPlayer) {
								targets.remove(ent);
							}
						} else {
							isPlayer = true;
						}
					}
				}
			}
			targets.sort(Comparator.comparingDouble(entity -> getStrongestPlayerSort((Entity) entity)));
			break;
		case "distance":
			targets.sort(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)));
			break;
		case "health":
			targets.sort(Comparator
					.comparingDouble(entity -> entity instanceof EntityPlayer ? ((EntityPlayer) entity).getHealth()
							: mc.thePlayer.getDistanceToEntity(entity)));
			break;
		}

		if (!targets.isEmpty()) {
			switch ((attackMode).toLowerCase()) {
			case "off":
				target = (EntityLivingBase) targets.get(0);
				break;
			case "timer":

				if (timer.hasTimeElapsed(switchTimer, true))
					size++;

				if (targets.size() > 0 && size >= targets.size())
					size = 0;

				target = (EntityLivingBase) targets.get(size);
				break;
			case "hurt time":
				if (targets.size() > 0) {
					targets.sort(Comparator.comparingDouble(entity -> entity.hurtResistantTime));
					target = (EntityLivingBase) targets.get(0);
				}
				break;
			}
		}

		return target;
	}

	public static boolean isInSameTeam(EntityPlayer player) {
		try {
			String[] name = mc.thePlayer.getDisplayName().getUnformattedText().split("");
			String[] parts = player.getDisplayName().getUnformattedText().split("");

			boolean b = (Arrays.asList(name).contains("§") && Arrays.asList(parts).contains("§"))
					&& (Arrays.asList(name).get(Arrays.asList(name).indexOf("§") + 1)
							.equals(Arrays.asList(parts).get(Arrays.asList(parts).indexOf("§") + 1)));

			return b;
		} catch (Exception ex) {

		}
		return false;
	}

	public static double getDistanceToEntityBox(Entity entity) {
		Vec3 eyes = mc.thePlayer.getPositionEyes(1f);
		Vec3 pos = RotationUtils.getBestHitVec(entity);
		double xDist = Math.abs(pos.xCoord - eyes.xCoord);
		double yDist = Math.abs(pos.yCoord - eyes.yCoord);
		double zDist = Math.abs(pos.zCoord - eyes.zCoord);
		return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2) + Math.pow(zDist, 2));
	}
	public static double getDistanceToEntityBoxFromPosition(double posX, double posY, double posZ, Entity entity) {
		Vec3 eyes = mc.thePlayer.getPositionEyes(1f);
		Vec3 pos = RotationUtils.getBestHitVec(entity);
		double xDist = Math.abs(pos.xCoord - posX);
		double yDist = Math.abs(pos.yCoord - posY + mc.thePlayer.getEyeHeight());
		double zDist = Math.abs(pos.zCoord - posZ);
		return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2) + Math.pow(zDist, 2));
	}

	public static double getSmartSort(Entity entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			double playerDamage = 0;
			double targetDamage = 0;

			if (mc.thePlayer.getHeldItem() != null) {
				playerDamage = Math.max(0, InventoryUtils.getItemDamage(mc.thePlayer.getHeldItem()));
			}
			if (player.getHeldItem() != null) {
				targetDamage = Math.max(0, InventoryUtils.getItemDamage(player.getHeldItem()));
			}

			playerDamage = (playerDamage * 20) / (player.getTotalArmorValue() * 4);

			if (mc.thePlayer.fallDistance > 0) {
				playerDamage *= 1.5;
			}

			if (playerDamage >= player.getHealth()) {
				return -100000000;
			}
			return targetDamage * -1;
		}
		return mc.thePlayer.getDistanceToEntity(entity);
	}

	public static double getStrongestPlayerSort(Entity entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			double targetDamage = 0;
			if (player.getHeldItem() != null) {
				targetDamage = Math.max(0, InventoryUtils.getItemDamage(player.getHeldItem()));
			}
			return targetDamage * -1;
		}
		return mc.thePlayer.getDistanceToEntity(entity);
	}
}
