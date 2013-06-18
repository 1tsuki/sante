package com.astrider.sfc.src.helper;

import com.astrider.sfc.app.lib.helper.MathUtils;
import com.astrider.sfc.src.model.dao.WeeklyLogDao;
import com.astrider.sfc.src.model.vo.db.MealLogVo;
import com.astrider.sfc.src.model.vo.db.WeeklyLogVo;

/**
 * @author Itsuki Sakitsu 週間ログ周辺のUtil集
 */
public final class WeeklyLogUtils {
	private static final int NUT_COUNT = 11;

	/**
	 * @param userId
	 * @return 最新のWeeklyLogVo 最新のWeeklyLogVoを取得。週が変わっていた場合は新しく作成。
	 */
	public static WeeklyLogVo getCurrentLog(int userId) {
		WeeklyLogDao weeklyLogDao = new WeeklyLogDao();
		WeeklyLogVo vo = weeklyLogDao.selectCurrentWeek(userId);
		if (vo == null) {
			vo = new WeeklyLogVo();
			vo.setUserId(userId);
			weeklyLogDao.insert(vo);
		}
		weeklyLogDao.close();
		return vo;
	}

	/**
	 * @param userId
	 * @param meal
	 *            食事ログの内容を週間ログに反映させる
	 */
	public static void addMealToCurrentLog(int userId, MealLogVo meal) {
		WeeklyLogVo week = getCurrentLog(userId);
		joinValues(week, meal);

		WeeklyLogDao weeklyLogDao = new WeeklyLogDao();
		weeklyLogDao.update(week);
		weeklyLogDao.close();
	}

	/**
	 * @param userId
	 *            現在の栄養バランス数値を再計算する
	 */
	public static void updateCurrentTotalBalance(int userId) {
		int[] ingested = SanteUtils.getIngestedNutrients(userId, 0);
		int[] desired = SanteUtils.getDesiredNutrients();
		double ingestedAverage = MathUtils.getAverage(ingested);
		double desiredAverage = MathUtils.getAverage(desired);

		// 係数を揃える
		for (int i = 0; i < ingested.length; i++) {
			double coefficient = desiredAverage / ingestedAverage;
			ingested[i] = (int) (ingested[i] * coefficient);
		}

		// 差の二乗検定
		double[] diffs = new double[NUT_COUNT];
		for (int j = 0; j < ingested.length; j++) {
			diffs[j] = 0;
			if (desired[j] != 0) {
				diffs[j] = Math.sqrt(Math.pow(desired[j] - ingested[j], 2))
						/ desired[j];
			}
		}
		double balance = (1 - MathUtils.getAverage(diffs)) * 100;

		WeeklyLogVo weekLog = getCurrentLog(userId);
		weekLog.setTotalBalance((int) balance);
		WeeklyLogDao dao = new WeeklyLogDao();
		dao.update(weekLog);
		dao.close();
	}

	private static void joinValues(WeeklyLogVo week, MealLogVo meal) {
		week.setMilk(week.getMilk() + meal.getMilk());
		week.setEgg(week.getEgg() + meal.getEgg());
		week.setMeat(week.getMeat() + meal.getMeat());
		week.setBean(week.getBean() + meal.getBean());
		week.setVegetable(week.getVegetable() + meal.getVegetable());
		week.setFruit(week.getFruit() + meal.getFruit());
		week.setMineral(week.getMineral() + meal.getMineral());
		week.setCrop(week.getCrop() + meal.getCrop());
		week.setPotato(week.getPotato() + meal.getPotato());
		week.setFat(week.getFat() + meal.getFat());
		week.setSuguar(week.getSuguar() + meal.getSuguar());
	}
}