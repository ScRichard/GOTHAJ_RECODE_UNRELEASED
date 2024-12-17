package vip.gothaj.client.ui.clickguis.Panel.extensions.script;

import org.lwjgl.opengl.GL11;

import vip.gothaj.client.Client;
import vip.gothaj.client.scripts.Script;
import vip.gothaj.client.ui.clickguis.Panel.screens.ads.ScriptScreen;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;

public class ScriptComponent extends Button{
	
	private Script script;
	
	private ScriptScreen parent;
	
	private PositionUtils position = new PositionUtils(0, 0, 290, 15, 1), reload = new PositionUtils(0, 0, 13, 13, 1);

	private Animation animation = new Animation(500, AnimationType.EaseInOutBack);
	
	public ScriptComponent(Script script, ScriptScreen parent) {
		this.script = script;
		this.parent = parent;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RoundedUtils.drawRoundedRect(position, 0xff10161d, 4);
		IconUtils.drawIcon("script", this.position.getX()+3, this.position.getY()+1, Client.INSTANCE.getThemeManager().get("Panel Active Color"));
		Fonts.drawString(script.getName(), this.position.getX() + 32, this.position.getY() + 3, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-bold", 1.05);
		
		Fonts.drawString(script.getAuthor(), this.position.getX() + 32 + Fonts.getWidth(script.getName(),  "roboto-bold", 1.05)+3, this.position.getY() + 3, Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"), "roboto-medium", 1.00);
		
		animation.reversed = !reload.isInside(mouseX, mouseY);
		
		reload.setX(this.position.getX2()-15);
		reload.setY(this.position.getY()+1);
		GL11.glPushMatrix();
		GL11.glTranslated(this.reload.getX()+this.reload.getWidth()/2, this.reload.getY()+this.reload.getHeight()/2, 0.);
		GL11.glRotated(-animation.calculateAnimation() * 360, 0, 0, 1);
		IconUtils.drawIcon("reload", -this.reload.getWidth()/2+1, -this.reload.getHeight()/2+1, Client.INSTANCE.getThemeManager().get("Panel Text Color"), 0.9);
		GL11.glPopMatrix();
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {

	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {

	}

	@Override
	public void onKey(int key, char ch) {
		
	}
	
	public void setPosition(double x, double y) {
		this.position.setX(x);
		this.position.setY(y);
	}

	public Script getScript() {
		return script;
	}

	public void setScript(Script script) {
		this.script = script;
	}

	public ScriptScreen getParent() {
		return parent;
	}

	public void setParent(ScriptScreen parent) {
		this.parent = parent;
	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(PositionUtils position) {
		this.position = position;
	}

	public PositionUtils getReload() {
		return reload;
	}

	public void setReload(PositionUtils reload) {
		this.reload = reload;
	}
	
}
