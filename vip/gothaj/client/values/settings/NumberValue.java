package vip.gothaj.client.values.settings;

import java.util.function.Supplier;

import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.Setting;
import vip.gothaj.client.values.Value;

public class NumberValue extends Value{

	private double value, min, max, increment;
	
	public NumberValue(Mod parent, String id, String name, double value, double min, double max, double increment, Supplier visible) {
		super(parent, id, name, visible, null);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}
	public NumberValue(Mod parent, String name, double value, double min, double max, double increment, Supplier visible) {
		super(parent, name, name, visible, null);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}
	public NumberValue(Mod parent, String id, String name, double value, double min, double max, double increment) {
		super(parent, id, name, () -> true, null);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}
	public NumberValue(Mod parent, String name, double value, double min, double max, double increment) {
		super(parent, name, name, () -> true, null);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		double precision = 1 / increment;
		this.value = Math.round(Math.max(min, Math.min(max, value)) * precision) / precision;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getIncrement() {
		return increment;
	}

	public void setIncrement(double increment) {
		this.increment = increment;
	}

	
}
