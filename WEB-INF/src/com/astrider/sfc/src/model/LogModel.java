package com.astrider.sfc.src.model;

import javax.servlet.http.HttpServletRequest;

import com.astrider.sfc.app.lib.BaseModel;
import com.astrider.sfc.app.lib.Mapper;
import com.astrider.sfc.app.lib.Validator;
import com.astrider.sfc.app.lib.FlashMessage.Type;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.helper.WeeklyLogUtils;
import com.astrider.sfc.src.model.dao.CookLogDao;
import com.astrider.sfc.src.model.dao.MealLogDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.vo.db.CookLogVo;
import com.astrider.sfc.src.model.vo.db.MealLogVo;
import com.astrider.sfc.src.model.vo.db.UserStatsVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.form.CookCompleteVo;

/**
 * 調理履歴関連Model.
 * 
 * @author astrider
 * 
 */
public class LogModel extends BaseModel {
	/**
	 * 調理完了登録.
	 * 
	 * @param request
	 * @return <p>
	 *         cookLog更新、userStats更新、mealLog更新、weekLog更新
	 *         </p>
	 */
	public boolean cookComplete(HttpServletRequest request) {
		// ログインユーザー情報取得
		UserVo user = SanteUtils.getLoginUser(request);
		if (user == null) {
			flashMessage.addMessage("ログインユーザーが確認できませんでした");
			return false;
		}

		// 入力値バリデーション
		Mapper<CookCompleteVo> mapper = new Mapper<CookCompleteVo>();
		CookCompleteVo cookComplete = mapper.fromHttpRequest(request);
		Validator<CookCompleteVo> validator = new Validator<CookCompleteVo>(cookComplete);
		if (!validator.valid()) {
			flashMessage.addMessage(validator.getFlashMessage());
			return false;
		}

		// 調理ログ挿入
		CookLogVo cookLog = new CookLogVo();
		cookLog.setRecipeId(cookComplete.getRecipeId());
		cookLog.setUserId(user.getUserId());
		CookLogDao cookLogDao = new CookLogDao();
		if (!cookLogDao.insert(cookLog)) {
			flashMessage.addMessage("調理履歴の登録に失敗しました");
			return false;
		}

		// 連続調理回数、最大連続調理回数、総調理回数更新
		UserStatsDao userStatsDao = new UserStatsDao();
		UserStatsVo userStats = userStatsDao.selectByUserId(user.getUserId());
		userStats.setTotalCooked(userStats.getTotalCooked() + 1);
		if (1 == cookLogDao.countCookedAtToday(user.getUserId())) {
			userStats.setConsecutivelyCooked(userStats.getConsecutivelyCooked() + 1);
			if (userStats.getMaxConsecutivelyCooked() < userStats.getConsecutivelyCooked()) {
				userStats.setMaxConsecutivelyCooked(userStats.getConsecutivelyCooked());
			}
		}
		userStatsDao.update(userStats);
		userStatsDao.close();
		cookLogDao.close();

		// 食事別栄養素ログ挿入
		MealLogDao mealNutAmountsDao = new MealLogDao();
		boolean succeed = mealNutAmountsDao.insertFromRecipeNutAmounts(user.getUserId(), cookLog.getRecipeId());
		if (!succeed) {
			flashMessage.addMessage("食事別栄養素の挿入に失敗しました");
			return false;
		}
		MealLogVo mealNutAmounts = mealNutAmountsDao.selectNewestByUserId(user.getUserId());
		mealNutAmountsDao.close();

		// 週別栄養素ログ更新
		WeeklyLogUtils.addMealToCurrentLog(user.getUserId(), mealNutAmounts);
		WeeklyLogUtils.updateCurrentTotalBalance(user.getUserId());

		flashMessage.addMessage("調理完了おめでとうございます！");
		flashMessage.setMessageType(Type.INFO);
		return true;
	}

}
