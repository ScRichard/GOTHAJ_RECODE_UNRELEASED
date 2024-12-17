package vip.gothaj.client.utils.buttons;

import vip.gothaj.client.Client;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;

public class SliderButton extends Button{

	public PositionUtils position = new PositionUtils(0,0,0,0,1);
	
	private boolean enabled;
	
	private Animation animation = new Animation(200, AnimationType.EaseInOutBack);
	
	public SliderButton(double width, double height, boolean enabled) {
		this.position.setWidth(width);
		this.position.setHeight(height);
		this.enabled = enabled;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RoundedUtils.drawRoundedRect(position, Client.INSTANCE.getThemeManager().get("Panel Other Buttons Background"), (float) (position.getHeight()/2) - 1);
		
		animation.reversed = !enabled;
		
		RoundedUtils.drawRoundedRect(this.position.getX()+0.5+(this.position.getWidth()-this.position.getHeight())*animation.calculateAnimation(), this.position.getY()+0.5,this.position.getHeight()-1,this.position.getHeight()-1, Client.INSTANCE.getThemeManager().get("Panel Active Color"), (float) (position.getHeight()/2) - 1);
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(position.isInside(mouseX, mouseY) && button == 0) {
			this.enabled = !enabled;
		}
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {

	}

	@Override
	public void onKey(int key, char ch) {

	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(PositionUtils position) {
		this.position = position;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
}
