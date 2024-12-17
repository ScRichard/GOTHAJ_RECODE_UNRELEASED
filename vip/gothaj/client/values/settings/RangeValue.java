package vip.gothaj.client.values.settings;

import java.util.function.Supplier;

import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.values.Setting;
import vip.gothaj.client.values.Value;

public class RangeValue extends Value{
	public double min, max, minValue, maxValue, increment;
	
	public RangeValue(Mod parent, String id, String name, double minValue, double maxValue, double min, double max, double increment,Supplier visible) {
		super(parent, id, name, visible, null);
		this.min = min;
		this.max = max;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.increment = increment;
	}
	public RangeValue(Mod parent, String name, double minValue, double maxValue, double min, double max, double increment,Supplier visible) {
		super(parent, name, name, visible, null);
		this.min = min;
		this.max = max;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.increment = increment;
	}
	public RangeValue(Mod parent, String id, String name, double minValue, double maxValue, double min, double max, double increment) {
		super(parent, id, name, () -> true, null);
		this.min = min;
		this.max = max;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.increment = increment;
	}
	public RangeValue(Mod parent, String name, double minValue, double maxValue, double min, double max, double increment) {
		super(parent, name, name, () -> true, null);
		this.min = min;
		this.max = max;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.increment = increment;
	}

	public void setMinValue(double value) {
		double precision = 1 / increment;
		this.minValue = Math.min(Math.round(Math.max(min, Math.min(max, value)) * precision) / precision, this.maxValue);

	}
	public void setMaxValue(double value) {
		double precision = 1 / increment;
		this.maxValue = Math.max(this.minValue ,Math.round(Math.max(min, Math.min(max, value)) * precision) / precision);
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
	public double getMinValue() {
		return minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
}
