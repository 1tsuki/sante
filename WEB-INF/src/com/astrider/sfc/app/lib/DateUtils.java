package com.astrider.sfc.app.lib;

import java.util.Calendar;

/**
 * 日付関連Utils.
 * 
 * @author astrider<br>
 */
public final class DateUtils {
	/**
	 * ２つの日付の間で経過した日数を取得.
	 * 
	 * @param 比較対象１
	 * @param 比較対象２
	 * @return 経過日数
	 */
	public static int getDayPassed(Calendar comparer, Calendar comparee) {
		// compareeのほうが古かった場合値を入れ替え
		if (comparer.after(comparee)) {
			Calendar tmp = comparer;
			comparer = comparee;
			comparee = tmp;
		}

		// 経過日数を取得
		int dayPassed = 0;
		while (comparer.before(comparee)) {
			comparer.add(Calendar.DATE, 1);
			dayPassed++;
		}
		return dayPassed;
	}
}
