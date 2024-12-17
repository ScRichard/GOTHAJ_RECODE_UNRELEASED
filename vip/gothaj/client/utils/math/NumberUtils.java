package vip.gothaj.client.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import vip.gothaj.client.utils.Wrapper;

public class NumberUtils extends Wrapper{
	public static double roundToPlace(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
