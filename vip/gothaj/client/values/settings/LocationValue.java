package vip.gothaj.client.values.settings;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.Value;

public class LocationValue extends Value {

	private double x, y, objectWidth, objectHeight;
	
	public LocationValue(Mod parent, String id, String name, double x, double y, Supplier visible) {
		super(parent, id, name, visible, null);
		this.x = x;
		this.y = y;
	}
	public LocationValue(Mod parent, String name, double x, double y, Supplier visible) {
		super(parent, name, name, visible, null);
		this.x = x;
		this.y = y;
	}
	public LocationValue(Mod parent, String name, double x, double y) {
		super(parent, name, name, ()-> true, null);
		this.x = x;
		this.y = y;
	}
	public LocationValue(Mod parent, String id, String name, double x, double y) {
		super(parent, id, name, ()-> true, null);
		this.x = x;
		this.y = y;
	}
	
	public void setPosX(double value) {
		this.x = Math.min(Math.round(Math.max(0, Math.min(1000, value))), 1000);
	}
	public void setPosY(double value) {
		this.y = Math.min(Math.round(Math.max(0, Math.min(600, value))), 600);
	}
	public double getPosX() {
		double width = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth_double();
		return width / 1000 * x;
	}
	public double getPosY() {
		double height = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight_double();
		return height / 600 * y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getObjectWidth() {
		return objectWidth;
	}
	
	public void setObjectWidth(double objectWidth) {
		this.objectWidth = objectWidth;
	}
	
	public double getObjectHeight() {
		return objectHeight;
	}
	
	public void setObjectHeight(double objectHeight) {
		this.objectHeight = objectHeight;
	}
	
	public double getMinX() {
		return 0;
	}
	public double getMaxX() {
		return 1000;
	}
	public double getMinY() {
		return 0;
	}
	public double getMaxY() {
		return 600;
	}

}
