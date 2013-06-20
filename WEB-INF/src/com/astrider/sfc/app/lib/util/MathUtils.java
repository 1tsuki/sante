package com.astrider.sfc.app.lib.util;

/**
 * 数学計算Util.
 * 
 * @author astrider<br>
 */
public final class MathUtils {
	public static double getAverage(int[] items) {
		int amount = 0;
		for (int i = 0; i < items.length; i++) {
			amount += items[i];
		}
		return amount / items.length;
	}

	public static double getAverage(double[] items) {
		double amount = 0;
		for (int i = 0; i < items.length; i++) {
			amount += items[i];
		}
		return amount / items.length;
	}
}
