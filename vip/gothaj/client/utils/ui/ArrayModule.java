package vip.gothaj.client.utils.ui;

import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;

public class ArrayModule {

	private Mod module;
	
	private PositionUtils position = new PositionUtils(0, 0, 0, 0, 1);
	
	private Animation animation = new Animation(100, AnimationType.EaseInOutBack);
	
	public ArrayModule(Mod module) {
		this.module = module;
	}

	public Mod getModule() {
		return module;
	}

	public void setModule(Mod module) {
		this.module = module;
	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(PositionUtils position) {
		this.position = position;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
}
