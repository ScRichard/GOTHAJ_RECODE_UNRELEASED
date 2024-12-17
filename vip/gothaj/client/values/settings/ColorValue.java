package vip.gothaj.client.values.settings;

import java.awt.Color;
import java.util.function.Supplier;

import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.render.ColorUtils;
import vip.gothaj.client.values.Value;

public class ColorValue extends Value{

	private int color1, color2, alpha = 100;
	
	private String mode = "Static";
	
	private String[] modes = new String[] {
			"Static", "Gradient", "Rainbow", "Astolfo"
	};
	
	private double offset = 1, speed = 1;
	
	public ColorValue(Mod parent, String id, String name, int color1, int color2, Supplier visible) {
		super(parent, id, name, visible, null);
		this.color1 = color1;
		this.color2 = color2;
	}
	public ColorValue(Mod parent, String name, int color1, int color2, Supplier visible) {
		super(parent, name, name, visible, null);
		this.color1 = color1;
		this.color2 = color2;
	}

	public ColorValue(Mod parent, String id, String name, int color1, int color2) {
		super(parent, id, name, ()->true, null);
		this.color1 = color1;
		this.color2 = color2;
	}
	public ColorValue(Mod parent, String name, int color1, int color2) {
		super(parent, name, name, ()->true, null);
		this.color1 = color1;
		this.color2 = color2;
	}
	public int getColor1() {
		return color1;
	}
	public void setColor1(int color1) {
		this.color1 = color1;
	}
	public int getColor2() {
		return color2;
	}
	public void setColor2(int color2) {
		this.color2 = color2;
	}
	public int getAlpha() {
		return alpha;
	}
	
	public Color getColor(int milis) {
		return new Color(ColorUtils.getColor(this, milis));
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String[] getModes() {
		return modes;
	}
	public void setModes(String[] modes) {
		this.modes = modes;
	}
	public double getOffset() {
		return offset;
	}
	public void setOffset(double offset) {
		this.offset = offset;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
