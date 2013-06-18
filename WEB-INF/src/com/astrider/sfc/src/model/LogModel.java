package com.astrider.sfc.src.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.helper.Mapper;
import com.astrider.sfc.app.lib.helper.FlashMessage.Type;
import com.astrider.sfc.app.lib.helper.Validator;
import com.astrider.sfc.app.lib.model.BaseModel;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.model.dao.CookLogDao;
import com.astrider.sfc.src.model.dao.MealLogDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.vo.db.CookLogVo;
import com.astrider.sfc.src.model.vo.db.MealLogVo;
import com.astrider.sfc.src.model.vo.db.UserStatsVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.db.WeeklyLogVo;
import com.astrider.sfc.src.model.vo.form.CookCompleteVo;

public class LogModel extends BaseModel {
    public boolean cookComplete(HttpServletRequest request) {
        // ログインユーザー情報取得
        HttpSession session = request.getSession();
        UserVo loginUser = (UserVo) session.getAttribute("loginUser");
        if (loginUser == null) {
            flashMessage.addMessage("ログインユーザーが確認できませんでした");
            flashMessage.setMessageType(Type.WARNING);
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
        cookLog.setUserId(loginUser.getUserId());
        CookLogDao cookLogDao = new CookLogDao();
        if (!cookLogDao.insert(cookLog)) {
            flashMessage.addMessage("調理履歴の登録に失敗しました");
            flashMessage.setMessageType(Type.WARNING);
            return false;
        }

        // 連続調理回数、最大連続調理回数、総調理回数更新
        UserStatsDao userStatsDao = new UserStatsDao();
        UserStatsVo userStats = userStatsDao.selectByUserId(loginUser.getUserId());
        userStats.setTotalCooked(userStats.getTotalCooked() + 1);
        if (1 == cookLogDao.countCookedAtToday(loginUser.getUserId())) {
            userStats.setConsecutivelyCooked(userStats.getConsecutivelyCooked() + 1);
            if (userStats.getMaxConsecutivelyCooked() < userStats.getConsecutivelyCooked()) {
            	userStats.setMaxConsecutivelyCooked(userStats.getConsecutivelyCooked());
            }
        }
        
        cookLogDao.close();

        // 食事別栄養素ログ挿入
        MealLogDao mealNutAmountsDao = new MealLogDao();
        boolean succeed = mealNutAmountsDao.insertFromRecipeNutAmounts(loginUser.getUserId(), cookLog.getRecipeId());
        if (!succeed) {
            flashMessage.addMessage("食事別栄養素の挿入に失敗しました");
            flashMessage.setMessageType(Type.WARNING);
            return false;
        }
        MealLogVo mealNutAmounts = mealNutAmountsDao.selectNewestByUserId(loginUser.getUserId());
        mealNutAmountsDao.close();

        // 週別栄養素ログ更新
        SanteUtils.addMealToCurrentWeekLog(loginUser.getUserId(), mealNutAmounts);
        SanteUtils.updateTotalBalanceOfCurrentWeekLog(loginUser.getUserId());

        // totalBalance更新
        WeeklyLogVo weekLog = SanteUtils.getWeeklyLogOfThisWeek(loginUser.getUserId());
        userStats.setNutrientsBalance(weekLog.getTotalBalance());
        userStatsDao.update(userStats);
        userStatsDao.close();

        return true;
    }

}
