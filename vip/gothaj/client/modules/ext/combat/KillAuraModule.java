package vip.gothaj.client.modules.ext.combat;

import java.util.Random;

import de.florianmichael.viamcp.fixes.AttackOrder;
import god.buddy.aot.BCompiler;
import god.buddy.aot.BCompiler.AOT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import vip.gothaj.client.Client;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.EventType;
import vip.gothaj.client.event.events.EventAttack;
import vip.gothaj.client.event.events.EventClick;
import vip.gothaj.client.event.events.EventGameLoop;
import vip.gothaj.client.event.events.EventJump;
import vip.gothaj.client.event.events.EventLook;
import vip.gothaj.client.event.events.EventMotion;
import vip.gothaj.client.event.events.EventMoveFlying;
import vip.gothaj.client.event.events.EventRenderRotation;
import vip.gothaj.client.event.events.EventSendPacket;
import vip.gothaj.client.event.events.EventSilentMoveFix;
import vip.gothaj.client.event.events.EventTick;
import vip.gothaj.client.event.events.EventTimeDelay;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.modules.ext.client.SmoothRotationModule;
import vip.gothaj.client.modules.ext.movement.NoFallModule;
import vip.gothaj.client.modules.ext.player.AnnoyerModule;
import vip.gothaj.client.modules.ext.player.AntiFireModule;
import vip.gothaj.client.modules.ext.player.AutoHealModule;
import vip.gothaj.client.modules.ext.player.ManagerModule;
import vip.gothaj.client.modules.ext.player.ScaffoldModule;
import vip.gothaj.client.utils.client.Timer;
import vip.gothaj.client.utils.client.Tuple;
import vip.gothaj.client.utils.inventory.InventoryUtils;
import vip.gothaj.client.utils.move.MovementUtils;
import vip.gothaj.client.utils.noise.FastNoiseLite;
import vip.gothaj.client.utils.rotations.RotationUtils;
import vip.gothaj.client.utils.target.TargetUtils;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.DescriptionValue;
import vip.gothaj.client.values.settings.ModeValue;
import vip.gothaj.client.values.settings.MultipleBooleanValue;
import vip.gothaj.client.values.settings.NumberValue;
import vip.gothaj.client.values.settings.RangeValue;

public class KillAuraModule extends Mod{

	FastNoiseLite noise = new FastNoiseLite();

	public EntityLivingBase target;

	public int attackTimes;

	public double yawSpeed, pitchSpeed, randomCPS;

	public Timer clickTimer = new Timer();
	public Timer cpsRandomizationTimer = new Timer();
	public Timer rotationRandomizationTimer = new Timer();
	public Timer polarRotationTimer = new Timer();

	public long lastTime;

	public int intaveBlockTicks, attackTick;

	public boolean allowedToWork = false, blockingStatus = false, canSnapRotation, wasMaxTurn;
	//settings
	//"Normal", "Polar", "Polar 2", "Snap", "None"
	public MultipleBooleanValue targets = new MultipleBooleanValue(this, "Targets", new Tuple[] {
			new Tuple<String, Boolean>("Players", true),
			new Tuple<String, Boolean>("Mobs", true),
			new Tuple<String, Boolean>("Animals", true),
			new Tuple<String, Boolean>("Invisibles", true),
			new Tuple<String, Boolean>("Dead", true),
			new Tuple<String, Boolean>("Villagers", true)
	});
	public NumberValue fov = new NumberValue(this, "FOV", 360, 10, 360, 10);
	public ModeValue rotationMode = new ModeValue(this, "Rotations", new Value[] {
			new Value(this, "Normal"), new Value(this, "Polar"), new Value(this, "Polar 2"), new Value(this, "Snap"), new Value(this, "None")
	});
	public ModeValue switchMode = new ModeValue(this, "Mode", new Value[] {
			new Value(this, "Single"), new Value(this, "Switch")
	});
	
	public NumberValue switchDelay = new NumberValue(this, "Switch delay", 100.0D, 10.0D, 1000.0D, 1, () -> switchMode.isMode("switch"));
	public ModeValue MoveFix = new ModeValue(this, "Move fix", new Value[] {
			new Value(this, "Off"), new Value(this, "Strict"), new Value(this, "Silent")
	});
	
	public ModeValue autoBlock = new ModeValue(this, "Auto block", new Value[] {
			new Value(this, "None"), new Value(this, "Vanilla"), new Value(this, "Hypixel 1.12.2"), new Value(this, "Legit"), new Value(this, "Fake")
	});
	
	public NumberValue Range = new NumberValue(this, "Range", 3, 3, 8, 0.01);
	public NumberValue interactRange = new NumberValue(this, "Interact range", 5, 3, 8, 0.01);
	
	public CategoryValue rotationsSpeed = new CategoryValue(this, "Rotations");
	
	public RangeValue yawSpeedVal = new RangeValue(this, "Yaw speed", 120 , 180, 0, 180, 1, () -> !rotationMode.isMode("polar"));
	public RangeValue pitchSpeedVal = new RangeValue(this, "Pitch speed", 120,180, 0, 180, 1, () -> !rotationMode.isMode("polar"));
	
	public BooleanValue disableOnDeath = new BooleanValue(this, "Disable on death", false, null);
	public BooleanValue swingInRange = new BooleanValue(this, "Swing in range", false, null);
	public BooleanValue raytrace = new BooleanValue(this, "Raytrace", false, null);
	public BooleanValue closedInventory = new BooleanValue(this, "Closed inventory", false, null);
	public BooleanValue TroughWalls = new BooleanValue(this, "Trogh walls", false, null);
	
	public CategoryValue Clicks = new CategoryValue(this, "Clicks");
	
	public BooleanValue newCombat = new BooleanValue(this, "1.9+ Combat", false, null);
	public RangeValue cps = new RangeValue(this, "CPS", 12 , 14, 0, 30, 1, () -> !newCombat.isEnabled());
	public BooleanValue reduced = new BooleanValue(this, "Reduced", false, () -> !newCombat.isEnabled(), null);
	public RangeValue reducedCps = new RangeValue(this, "Reduced CPS", 12 , 14, 0, 30, 1, () -> !newCombat.isEnabled() && reduced.isEnabled());
	public BooleanValue forceHit = new BooleanValue(this, "Force hit", false, null);
	
	public ModeValue sort = new ModeValue(this, "Sort by", new Value[] {
			new Value(this, "Distance"), new Value(this, "Health"), new Value(this, "Smart"), new Value(this, "Strongest player")
	});
	
	public KillAuraModule() {
		super("Kill Aura", "Attacks entities near you", null, Category.COMBAT);
		Clicks.addSettings(newCombat, cps, reduced, reducedCps, forceHit);
		rotationsSpeed.addSettings(rotationMode, yawSpeedVal, pitchSpeedVal);
		this.addSettings(
				targets,
				switchMode,
				rotationsSpeed,
				Range,
				interactRange,
				new DescriptionValue(this, "Interacts with entity when its in range"),
				fov,
				Clicks,
				MoveFix,
				autoBlock,
				disableOnDeath,
				swingInRange,
				raytrace,
				new DescriptionValue(this, "Makes you hit entity only when you looking at entity"),
				closedInventory,
				TroughWalls,
				sort
				);
	}

	public void onDisable() {
		allowedToWork = false;
		mc.timer.timerSpeed = 1f;
		target = null;
		unBlock();

		SmoothRotationModule smoothRotation = (SmoothRotationModule) Client.INSTANCE.getModuleManager()
				.getModule(SmoothRotationModule.class);

		if (smoothRotation.isEnabled() && smoothRotation.ka.isEnabled()) {

		} else {
			RotationUtils.customRots = false;
			RotationUtils.serverYaw = mc.thePlayer.rotationYaw;
			RotationUtils.serverPitch = mc.thePlayer.rotationPitch;
		}
	}

	public void onEnable() {
		intaveBlockTicks = 0;
		target = null;
		RotationUtils.serverYaw = mc.thePlayer.rotationYaw;
		RotationUtils.serverPitch = mc.thePlayer.rotationPitch;
		allowedToWork = false;
		blockingStatus = false;
		canSnapRotation = false;

		if (canWork()) {
			calculateRots();
		}

		attackTimes = 0;
		attackTick = 0;
	}

	@EventListener
	public void onSendPacket(EventSendPacket e) {

	}

	@EventListener
	public void onGameLoop(EventGameLoop e) {
		calculateCPS();
	}

	@EventListener
	public void update(EventTick e) {
		setInfo(switchMode.getMode());
		if ((mc.thePlayer.getHealth() <= 0 || mc.thePlayer.ticksExisted <= 5) && disableOnDeath.isEnabled()) {
			this.toggle();
			return;
		}
	}

	@EventListener
	public void onClick(EventClick e) {
		if (canWork()) {
			calculateRots();
			calculateCPS();
			attackLoop();
		}
		attackTick++;
		if (attackTick != 0) {
			attackTimes = 0;
		}
	}

	@EventListener
	public void onTimeDelay(EventTimeDelay e) {
		if (attackTick != 0) {
			attackTick = 0;
			return;
		}
		if (canWork()) {
			calculateRots();
			calculateCPS();
			attackLoop();
		}
	}

	@EventListener
	public void onRotationRender(EventRenderRotation e) {
		if (allowedToWork && RotationUtils.customRots) {
			switch (rotationMode.getMode().toLowerCase()) {
			case "normal":
			case "polar":
				e.setYaw(RotationUtils.serverYaw);
				e.setPitch(RotationUtils.serverPitch);
				break;

			case "snap":
				if (canSnapRotation) {
					e.setYaw(RotationUtils.serverYaw);
					e.setPitch(RotationUtils.serverPitch);
				}

				break;
			}
		}
	}

	@EventListener
	public void onLook(EventLook e) {
		if (allowedToWork && RotationUtils.customRots) {
			EventLook event = (EventLook) e;

			switch (rotationMode.getMode().toLowerCase()) {
			case "normal":
			case "snap":
			case "polar":
				e.setYaw(RotationUtils.serverYaw);
				e.setPitch(RotationUtils.serverPitch);
				break;
			}
		}
	}

	@EventListener
	public void onJump(EventJump e) {
		if (allowedToWork && !MoveFix.getMode().equalsIgnoreCase("Off") && RotationUtils.customRots) {

			switch (rotationMode.getMode().toLowerCase()) {
			case "normal":
				e.setYaw(RotationUtils.serverYaw);
				break;

			case "polar":
				e.setYaw(RotationUtils.serverYaw);
				break;

			case "snap":
				if (canSnapRotation) {
					e.setYaw(RotationUtils.serverYaw);
				}

				break;
			}
		}
	}

	@BCompiler(aot = AOT.AGGRESSIVE)
	@EventListener
	public void onMotion(EventMotion e) {
		if (allowedToWork && RotationUtils.customRots) {
			if (e.getType() == EventType.PRE) {
				
				switch (rotationMode.getMode().toLowerCase()) {
				case "normal":
					e.setYaw(RotationUtils.serverYaw);
					e.setPitch(RotationUtils.serverPitch);
					break;
				case "polar":
					e.setYaw(RotationUtils.serverYaw);
					e.setPitch(RotationUtils.serverPitch);
					break;

				case "snap":
					float rotationYaw = mc.thePlayer.rotationYaw;
					float rotationPitch = mc.thePlayer.rotationPitch;
					mc.thePlayer.rotationYaw = RotationUtils.serverYaw;
					mc.thePlayer.rotationPitch = RotationUtils.serverPitch;
					e.setYaw(mc.thePlayer.rotationYaw);
					e.setPitch(mc.thePlayer.rotationPitch);
					RotationUtils.serverYaw = rotationYaw;
					RotationUtils.serverPitch = rotationPitch;
					break;
				}
			} else {
				switch (rotationMode.getMode().toLowerCase()) {
				case "snap":
					mc.thePlayer.rotationYaw = RotationUtils.serverYaw;
					mc.thePlayer.rotationPitch = RotationUtils.serverPitch;
					break;
				}
				if (allowedToWork) {
					block(target, "Post");
				}
			}
		}
	}

	@EventListener
	public void onMove(EventMoveFlying e) {
		if (allowedToWork && RotationUtils.customRots) {

			if(!MoveFix.isMode("off") && !mc.isSingleplayer()) {
				if (rotationMode.getMode().equalsIgnoreCase("Snap")) {
					if (canSnapRotation) {
						e.setYaw(RotationUtils.serverYaw);
					}
				} else {
					e.setYaw(RotationUtils.serverYaw);
				}
			}
		}
	}
	@EventListener
	public void onSilentMoveFix(EventSilentMoveFix e) {
		if (allowedToWork && RotationUtils.customRots) {
			RotationUtils.applyMoveFix(e, RotationUtils.serverYaw);
		}
	}

	@BCompiler(aot = AOT.AGGRESSIVE)
	public void attackLoop() {

		if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) return;

		block(target, "Before");

		MovingObjectPosition ray = mc.thePlayer.rayTraceCustom(3, mc.timer.renderPartialTicks, RotationUtils.serverYaw,
				RotationUtils.serverPitch);

		if (attackTimes > 0) {
			if (mc.thePlayer.getDistanceToEntity(target) <= Range.getValue()
					|| TargetUtils.getDistanceToEntityBox(target) <= Range.getValue()
					|| mc.objectMouseOver.entityHit == target || ray.entityHit == target
					|| RotationUtils.rayTrace(Range.getValue(),
							new float[] { RotationUtils.serverYaw, RotationUtils.serverPitch }) == target) {

				if (!rotationMode.getMode().equalsIgnoreCase("None")) {
					canSnapRotation = false;

					for (int i = 0; i < attackTimes; i++) {
						EventAttack event = new EventAttack(target);
						Client.INSTANCE.getEventBus().call(event);

						canSnapRotation = true;

						if (raytrace.isEnabled()) {
							Entity rayTracedEntity = RotationUtils.rayTrace(Range.getValue(),
									new float[] { RotationUtils.serverYaw, RotationUtils.serverPitch });
							if (rayTracedEntity == null) {
								rayTracedEntity = ray.entityHit;
							}
							if (rayTracedEntity == null) {
								rayTracedEntity = mc.objectMouseOver.entityHit;
							}

							if (rayTracedEntity == target && target != null) {

								block(target, "Before Attack");
								AttackOrder.sendFixedAttackEvent(mc.thePlayer, target);
							} else {
								if (swingInRange.isEnabled()) {
									if (!newCombat.isEnabled()) {
										mc.thePlayer.swingItem();
									}
								}
							}
						} else {
							block(target, "Before Attack");
							AttackOrder.sendFixedAttackEvent(mc.thePlayer, target);
						}
						event.setType(EventType.POST);
						Client.INSTANCE.getEventBus().call(event);
						block(target, "After Attack");
					}
				} else {
					for (int i = 0; i < attackTimes; i++) {
						block(target, "Before Attack");
						EventAttack event = new EventAttack(target);
						Client.INSTANCE.getEventBus().call(event);

						AttackOrder.sendFixedAttackEvent(mc.thePlayer, target);

						event.setType(EventType.POST);
						Client.INSTANCE.getEventBus().call(event);
						block(target, "After Attack");
					}
				}
			} else {
				if (mc.thePlayer.getDistanceToEntity(target) <= interactRange.getValue()
						|| TargetUtils.getDistanceToEntityBox(target) <= interactRange.getValue()) {
					if (swingInRange.isEnabled()) {
						if (!newCombat.isEnabled()) {
							for (int i = 0; i < attackTimes; i++) {
								mc.thePlayer.swingItem();
							}
						}
					}
				}
			}
		}
		block(target, "After");
	}

	public void calculateCPS() {
		if (newCombat.isEnabled()) {
			double delay = 4.0D;
			if (mc.thePlayer.getHeldItem() != null) {
				Item item = mc.thePlayer.getHeldItem().getItem();
				if (item instanceof net.minecraft.item.ItemSpade || item == Items.golden_axe
						|| item == Items.diamond_axe || item == Items.wooden_hoe || item == Items.golden_hoe)
					delay = 20.0D;
				if (item == Items.wooden_axe || item == Items.stone_axe)
					delay = 25.0D;
				if (item instanceof net.minecraft.item.ItemSword)
					delay = 12.0D;
				if (item instanceof net.minecraft.item.ItemPickaxe)
					delay = 17.0D;
				if (item == Items.iron_axe)
					delay = 22.0D;
				if (item == Items.stone_hoe)
					delay = 10.0D;
				if (item == Items.iron_hoe)
					delay = 7.0D;
			}
			delay *= 50;

			if (clickTimer.hasTimeElapsed(delay, false)) {
				clickTimer.reset();
				attackTimes++;
			}
		} else {
			if (reduced.isEnabled()) {
				advancedClick();
			} else {
				normalClick();
			}
		}

	}

	public void normalClick() {
		if (clickTimer.hasTimeElapsed(calculateCPS(cps.getMinValue(), cps.getMaxValue()), true)) {
			attackTimes++;
			return;
		}
	}

	public void advancedClick() {
		int perfectHitHurtTime = 2;
		boolean stop = false;

		if (forceHit.isEnabled()) {
			Entity rayTracedEntity = RotationUtils.rayTrace(Range.getValue(),
					new float[] { RotationUtils.serverYaw, RotationUtils.serverPitch });

			if (raytrace.isEnabled()) {
				if ((rayTracedEntity == target || mc.objectMouseOver.entityHit == target) && target != null) {
					if (rayTracedEntity instanceof EntityLivingBase) {
						EntityLivingBase entity = (EntityLivingBase) rayTracedEntity;
						if (entity.hurtTime <= perfectHitHurtTime) {
							attackTimes = 1;
							stop = true;
						}
					}
				}
			} else {
				if (target != null) {
					if (target.hurtTime <= perfectHitHurtTime) {
						attackTimes = 1;
						stop = true;
					}
				}
			}
		}
		if (!stop) {
			if (mc.thePlayer.hurtTime == 0) {
				if (clickTimer.hasTimeElapsed(calculateCPS(cps.getMinValue(), cps.getMaxValue()), true)) {
					attackTimes++;
				}
			} else {
				if (clickTimer.hasTimeElapsed(calculateCPS(reducedCps.getMinValue(), reducedCps.getMaxValue()), true)) {
					attackTimes++;
				}
			}
		}
	}

	public double calculateCPS(double min, double max) {
		double minValue = min;
		double maxValue = max;

		if (minValue == 0 && maxValue == 0) {
			return 100000;
		}

		if (minValue > maxValue)
			minValue = maxValue;
		if (maxValue < minValue)
			maxValue = minValue;

		if (cpsRandomizationTimer.hasTimeElapsed(150, true)) {
			randomCPS = minValue + (new Random().nextDouble() * (maxValue - minValue));
		}
		if (randomCPS < minValue) {
			randomCPS = minValue;
		}
		if (randomCPS > maxValue) {
			randomCPS = maxValue;
		}

		double delay = (1000 / randomCPS);

		delay -= 3;

		return delay;
	}

	@BCompiler(aot = AOT.AGGRESSIVE)
	public void calculateRots() {
		boolean wasSet = RotationUtils.customRots;

		if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled())
			return;

		double minYaw = yawSpeedVal.getMinValue() / 2;
		double maxYaw = yawSpeedVal.getMaxValue() / 2;
		double minPitch = pitchSpeedVal.getMinValue() / 2;
		double maxPitch = pitchSpeedVal.getMaxValue() / 2;

		if (minYaw > maxYaw)
			minYaw = maxYaw;
		if (maxYaw < minYaw)
			maxYaw = minYaw;
		if (minPitch > maxPitch)
			minPitch = maxPitch;
		if (maxPitch < minPitch)
			maxPitch = minPitch;

		if (rotationRandomizationTimer.hasTimeElapsed(150, true)) {
			yawSpeed = minYaw + (new Random().nextDouble() * (maxYaw - minYaw));
			pitchSpeed = minPitch + (new Random().nextDouble() * (maxPitch - minPitch));
		}

		if (yawSpeed < minYaw)
			yawSpeed = minYaw;
		if (yawSpeed > maxYaw)
			yawSpeed = maxYaw;
		if (pitchSpeed < minPitch)
			pitchSpeed = minPitch;
		if (pitchSpeed > maxPitch)
			pitchSpeed = maxPitch;

		switch (rotationMode.getMode().toLowerCase()) {
		case "normal": {
			float[] rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch,
					(EntityLivingBase) target, target.posX, target.posY, target.posZ, (float) yawSpeed,
					(float) pitchSpeed, true);

			rots = RotationUtils.getFixedRotation(rots,
					new float[] { RotationUtils.serverYaw, RotationUtils.serverPitch });

			RotationUtils.serverYaw = rots[0];
			RotationUtils.serverPitch = rots[1];
			RotationUtils.customRots = true;
		}
			break;

		case "snap": {
			if (canSnapRotation) {
				float[] rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch,
						(EntityLivingBase) target, target.posX, target.posY, target.posZ, (float) yawSpeed,
						(float) pitchSpeed, true);

				rots = RotationUtils.getFixedRotation(rots,
						new float[] { RotationUtils.serverYaw, RotationUtils.serverPitch });

				RotationUtils.serverYaw = rots[0];
				RotationUtils.serverPitch = rots[1];
				RotationUtils.customRots = true;
			}
		}
			break;
		case "polar": {
			float yawSpeed = (Math.abs(
					noise.GetNoise((mc.thePlayer.ticksExisted * 10) + 0, (mc.thePlayer.ticksExisted * 10) + 200) * 40)
					+ 15);
			float pitchSpeed = (Math.abs(
					noise.GetNoise((mc.thePlayer.ticksExisted * 10) + 50, (mc.thePlayer.ticksExisted * 10) + 100) * 40)
					+ 15);

			int speed = 8;
			int existed = mc.thePlayer.ticksExisted * speed;

			float horizontalScaleDevide = (float) (1.9f
					+ Math.max(-0.65, ((3 - mc.thePlayer.getDistanceToEntity(target)) / 3)));
			float verticalScaleDevide = (float) (1.5f
					+ Math.max(-0.65, ((3 - mc.thePlayer.getDistanceToEntity(target)) / 3)));

			double randomizedX = target.posX + (noise.GetNoise(existed + 50, existed + 250) / horizontalScaleDevide);
			double randomizedY = target.posY + 0.7
					+ (noise.GetNoise(existed + 100, existed + 100) / verticalScaleDevide);
			double randomizedZ = target.posZ + (noise.GetNoise(existed + 0, existed + 150) / horizontalScaleDevide);

			float[] rots = RotationUtils.getNormalRotationsFromPosition(randomizedX, randomizedY, randomizedZ,
					RotationUtils.serverYaw, RotationUtils.serverPitch, yawSpeed, pitchSpeed);

			rots = RotationUtils.getFixedRotation(rots,
					new float[] { RotationUtils.serverYaw, RotationUtils.serverPitch });

			RotationUtils.serverYaw = rots[0];
			RotationUtils.serverPitch = rots[1];
			RotationUtils.customRots = true;
		}
			break;
		case "polar 2": {
			float[] rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch,
					(EntityLivingBase) target, target.posX, target.posY, target.posZ, 180, 180, false);

			float yawSpeed = (Math.abs(
					noise.GetNoise((mc.thePlayer.ticksExisted * 10) + 0, (mc.thePlayer.ticksExisted * 10) + 200) * 40)
					+ 40);
			float pitchSpeed = (Math.abs(
					noise.GetNoise((mc.thePlayer.ticksExisted * 10) + 50, (mc.thePlayer.ticksExisted * 10) + 100) * 40)
					+ 25);

			int speed = 8;
			int existed = mc.thePlayer.ticksExisted * speed;

			double horizontalMotionEffect = (((MovementUtils.getSpeed() / MovementUtils.getBaseMoveSpeed()) * 0.75)
					+ ((MovementUtils.getEntitySpeed(target) / MovementUtils.getBaseMoveSpeed()) * 1.5))
					* (0.5 + (TargetUtils.getDistanceToEntityBox(target) / 10));
			double verticalMotionEffect = (((MovementUtils.getSpeed() / MovementUtils.getBaseMoveSpeed()) * 0.75)
					+ ((MovementUtils.getEntitySpeed(target) / MovementUtils.getBaseMoveSpeed()) * 1.5))
					* (0.75 + (TargetUtils.getDistanceToEntityBox(target) / 10));

			double noiseX = (noise.GetNoise(existed, existed) / 2) * horizontalMotionEffect;
			double noiseY = (noise.GetNoise(existed + 100, existed + 100) / 2) * verticalMotionEffect;
			double noiseZ = (noise.GetNoise(existed + 200, existed + 200) / 2) * horizontalMotionEffect;

			rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch,
					(EntityLivingBase) target, target.posX + noiseX, target.posY + noiseY, target.posZ + noiseZ,
					yawSpeed, pitchSpeed, false);

			float deltaYaw = MathHelper.wrapAngleTo180_float(rots[0] - RotationUtils.serverYaw);
			float deltaPitch = rots[1] - RotationUtils.serverPitch;

			float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float f1 = f * f * f * 8F;

			int deltaX = Math.round(deltaYaw / f1);
			int deltaY = Math.round(deltaPitch / f1);

			float smoothedF2 = (deltaX * f1) / 2.25f;
			float smoothedF3 = (deltaY * f1) / 2.25f;

			rots[0] = ((RotationUtils.serverYaw + smoothedF2) + f1);
			rots[1] = ((RotationUtils.serverPitch + smoothedF3) + f1);

			RotationUtils.serverYaw = rots[0];
			RotationUtils.serverPitch = rots[1];
			RotationUtils.customRots = true;
		}
			break;
		}

		if (RotationUtils.serverPitch > 90) {
			RotationUtils.serverPitch = 90;
		}

		if (RotationUtils.serverPitch < -90) {
			RotationUtils.serverPitch = -90;
		}

		if (!wasSet && RotationUtils.customRots && rotationMode.getMode().equalsIgnoreCase("Polar")) {
			RotationUtils.serverYaw = mc.thePlayer.rotationYaw;
			RotationUtils.serverPitch = mc.thePlayer.rotationPitch;
		}

	}

	public boolean canWork() {
		if (closedInventory.isEnabled()) {
			ManagerModule invManager = (ManagerModule) Client.INSTANCE.getModuleManager()
					.getModule(ManagerModule.class);

			AutoHealModule autoHeal = (AutoHealModule) Client.INSTANCE.getModuleManager()
					.getModule(AutoHealModule.class);
			AntiFireModule antiFire = (AntiFireModule) Client.INSTANCE.getModuleManager()
					.getModule(AntiFireModule.class);
			NoFallModule noFall = (NoFallModule) Client.INSTANCE.getModuleManager().getModule(NoFallModule.class);
			ScaffoldModule scaffold = (ScaffoldModule) Client.INSTANCE.getModuleManager()
					.getModule(ScaffoldModule.class);
			AnnoyerModule annoyer = (AnnoyerModule) Client.INSTANCE.getModuleManager().getModule(AnnoyerModule.class);
			AutoRodModule autoRod = (AutoRodModule) Client.INSTANCE.getModuleManager().getModule(AutoRodModule.class);

			if (mc.currentScreen != null) {
				if (allowedToWork) {
					RotationUtils.customRots = false;
					unBlock();
				}
				allowedToWork = false;
				return false;
			}

			if (((invManager.mode.getMode().equalsIgnoreCase("Spoof")
					|| autoHeal.mode.getMode().equalsIgnoreCase("Spoof")) && InventoryUtils.isInventoryOpen)
					|| autoHeal.cancelAura || antiFire.canWork || noFall.canWork || annoyer.cancel || autoRod.cancel
					|| scaffold.isEnabled()) {
				if (allowedToWork) {
					RotationUtils.customRots = false;
					unBlock();
				}
				allowedToWork = false;
				return false;
			}
		}

		target = TargetUtils.getTarget(Math.max(Range.getValue(), interactRange.getValue()), targets,
				switchMode.getMode(), (int) switchDelay.getValue(),
				Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(), TroughWalls.isEnabled(),
				targets.get("Invisibles"), targets.get("Dead"));
		if (target == null) {
			target = TargetUtils.getTargetBox(Math.max(Range.getValue(), interactRange.getValue()), targets,
					switchMode.getMode(), (int) switchDelay.getValue(),
					Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
					TroughWalls.isEnabled(), targets.get("Invisibles"), targets.get("Dead"));
		}

		if (target == null) {
			if (allowedToWork) {
				RotationUtils.customRots = false;
				unBlock();
			}

			allowedToWork = false;
			return false;
		} else {
			if (!allowedToWork) {
				RotationUtils.serverYaw = mc.thePlayer.rotationYaw;
				RotationUtils.serverPitch = mc.thePlayer.rotationPitch;
			}
			allowedToWork = true;
			return true;
		}
	}

	public void unBlock() {
		mc.gameSettings.keyBindUseItem.pressed = false;
		blockingStatus = false;
	}

	@BCompiler(aot = AOT.AGGRESSIVE)
	public void block(Entity ent, String timing) {
		if (mc.thePlayer == null || mc.theWorld == null) {
			return;
		}

		if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null
				&& mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
			switch (autoBlock.getMode().toLowerCase()) {
			case "legit":
				if (timing.equalsIgnoreCase("Before")) {
					if (mc.thePlayer.hurtTime >= 6) {
						mc.gameSettings.keyBindUseItem.pressed = true;
						blockingStatus = true;
					} else if (mc.thePlayer.hurtTime > 0) {
						mc.gameSettings.keyBindUseItem.pressed = false;
						blockingStatus = false;
					}
				}
				break;

			case "hypixel 1.12.2":
				if (timing.equalsIgnoreCase("Before")) {
					mc.gameSettings.keyBindUseItem.pressed = true;
					blockingStatus = true;
				}
				break;

			case "vanilla":
				mc.gameSettings.keyBindUseItem.pressed = true;
				blockingStatus = true;
				break;

			case "fake":
				mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld, mc.thePlayer);
				blockingStatus = true;
				break;
			}
		}
	}
	
}
