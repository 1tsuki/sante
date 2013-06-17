package com.astrider.sfc.app.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.helper.SanteUtils;
import com.astrider.sfc.app.model.dao.CookLogDao;
import com.astrider.sfc.app.model.dao.MealLogDao;
import com.astrider.sfc.app.model.dao.UserStatsDao;
import com.astrider.sfc.app.model.dao.WeeklyLogDao;
import com.astrider.sfc.app.model.vo.db.CookLogVo;
import com.astrider.sfc.app.model.vo.db.MealLogVo;
import com.astrider.sfc.app.model.vo.db.UserStatsVo;
import com.astrider.sfc.app.model.vo.db.UserVo;
import com.astrider.sfc.app.model.vo.db.WeeklyLogVo;
import com.astrider.sfc.lib.helper.FlashMessage.Type;
import com.astrider.sfc.lib.helper.Mapper;
import com.astrider.sfc.lib.model.BaseModel;

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

        // 調理ログ挿入
        Mapper<CookLogVo> mapper = new Mapper<CookLogVo>();
        CookLogVo cookLog = mapper.fromHttpRequest(request);
        cookLog.setUserId(loginUser.getUserId());
        CookLogDao cookLogDao = new CookLogDao();
        if (!cookLogDao.insert(cookLog)) {
            flashMessage.addMessage("調理履歴の登録に失敗しました");
            flashMessage.setMessageType(Type.WARNING);
            return false;
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
        WeeklyLogVo currentWeek = SanteUtils.getWeeklyLogOfThisWeek(loginUser.getUserId());
        WeeklyLogDao weeklyNutAmountsDao = new WeeklyLogDao();
        SanteUtils.joinWeeklyAndMealLog(currentWeek, mealNutAmounts);
        weeklyNutAmountsDao.update(currentWeek);
        weeklyNutAmountsDao.close();

        // 総調理回数更新
        UserStatsDao userStatsDao = new UserStatsDao();
        UserStatsVo userStats = userStatsDao.selectByUserId(loginUser.getUserId());
        if (userStats == null) {
            flashMessage.addMessage("ログインユーザーが確認できませんでした");
            flashMessage.setMessageType(Type.WARNING);
            return false;
        }
        userStats.setTotalCooked(userStats.getTotalCooked() + 1);
        userStatsDao.update(userStats);
        userStatsDao.close();

        return true;
    }

}
