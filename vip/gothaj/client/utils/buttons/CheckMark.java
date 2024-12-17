package vip.gothaj.client.utils.buttons;

import net.minecraft.client.renderer.GlStateManager;
import vip.gothaj.client.Client;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.shader.impl.BloomUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;

public class CheckMark extends Button{

	public PositionUtils position = new PositionUtils(0,0,0,0,1);
	
	private boolean enabled;
	
	private Animation animation = new Animation(200, AnimationType.EaseInOutBack);
	
	public CheckMark(double size, boolean enabled) {
		this.position.setWidth(size);
		this.position.setHeight(size);
		this.enabled = enabled;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RoundedUtils.drawRoundedRect(position, Client.INSTANCE.getThemeManager().get("Panel Other Buttons Background"), 2);
		
		animation.reversed = !enabled;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(
				position.getX() + this.position.getWidth()/2 - this.position.getWidth()/2 * animation.calculateAnimation()
						- (position.getX()) * animation.calculateAnimation(),
				position.getY() + this.position.getWidth()/2 - this.position.getWidth()/2 * animation.calculateAnimation()
						- (position.getY()) * animation.calculateAnimation(),
				0);
		GlStateManager.scale(animation.calculateAnimation(), animation.calculateAnimation(), 0);
		RoundedUtils.drawRoundedRect(position, Client.INSTANCE.getThemeManager().get("Panel Active Color"), 2);
		IconUtils.drawIcon("check", this.position.getX()+this.position.getWidth()/2-IconUtils.getIconHeight("check",0.6)/2, this.position.getY()+this.position.getHeight()/2-IconUtils.getIconHeight("check",0.6)/2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), 0.59);

		GlStateManager.popMatrix();
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		
	}

	@Override
	public void onKey(int key, char ch) {

	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(position.isInside(mouseX, mouseY) && button == 0) {
			this.enabled = !enabled;
		}
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
