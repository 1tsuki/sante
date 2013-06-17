package com.astrider.sfc.app.model;

import java.util.ArrayList;

import com.astrider.sfc.app.helper.SanteUtils;
import com.astrider.sfc.app.model.dao.CookLogDao;
import com.astrider.sfc.app.model.dao.MealLogDao;
import com.astrider.sfc.app.model.dao.RecipeDao;
import com.astrider.sfc.app.model.dao.RecipeNutAmountsDao;
import com.astrider.sfc.app.model.dao.UserDao;
import com.astrider.sfc.app.model.dao.UserStatsDao;
import com.astrider.sfc.app.model.dao.WeeklyLogDao;
import com.astrider.sfc.app.model.vo.db.CookLogVo;
import com.astrider.sfc.app.model.vo.db.MealLogVo;
import com.astrider.sfc.app.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.app.model.vo.db.RecipeVo;
import com.astrider.sfc.app.model.vo.db.UserStatsVo;
import com.astrider.sfc.app.model.vo.db.UserVo;
import com.astrider.sfc.app.model.vo.db.WeeklyLogVo;
import com.astrider.sfc.lib.model.BaseModel;

public class InternalModel extends BaseModel {
    public void execDailyBatch() {
        UserDao userDao = new UserDao();
        ArrayList<UserVo> users = userDao.selectAll();
        userDao.close();
        for (UserVo user : users) {
            checkDailyCookLogs(user);
//            updateWeeklyNutAmounts(user);
        }
    }

    private void checkDailyCookLogs(UserVo user) {
        CookLogDao cookLogDao = new CookLogDao();
        ArrayList<CookLogVo> cookLogs = cookLogDao.selectCookedAtYesterday(user.getUserId());
        cookLogDao.close();

        UserStatsDao userStatsDao = new UserStatsDao();
        UserStatsVo userStatsVo = userStatsDao.selectByUserId(user.getUserId());
        if (0 < cookLogs.size()) {
            // 連続調理日数の更新
            int consecutivelyCooked = userStatsVo.getConsecutivelyCooked() + 1;
            userStatsVo.setConsecutivelyCooked(consecutivelyCooked);
            // 最大連続調理日数の更新
            if (userStatsVo.getMaxConsecutivelyCooked() < consecutivelyCooked) {
                userStatsVo.setMaxConsecutivelyCooked(consecutivelyCooked);
            }
        } else {
            userStatsVo.setConsecutivelyCooked(0);
        }
        userStatsDao.update(userStatsVo);
        userStatsDao.close();
    }

    @SuppressWarnings("unused")
    private void updateWeeklyNutAmounts(UserVo user) {
        WeeklyLogDao weekNutAmountsDao = new WeeklyLogDao();
        WeeklyLogVo weekNutAmounts = weekNutAmountsDao.selectItemOfThisWeek(user.getUserId());
        if (weekNutAmounts == null) {
            weekNutAmounts = new WeeklyLogVo();
            weekNutAmounts.setUserId(user.getUserId());
            weekNutAmountsDao.insert(weekNutAmounts);
        }

        MealLogDao mealNutAmountsDao = new MealLogDao();
        ArrayList<MealLogVo> mealNutAmounts = mealNutAmountsDao.selectMealAtYesterday(user.getUserId());
        for (MealLogVo mealNutAmount : mealNutAmounts) {
            SanteUtils.joinWeeklyAndMealLog(weekNutAmounts, mealNutAmount);
        }
        weekNutAmountsDao.update(weekNutAmounts);

        weekNutAmountsDao.close();
        mealNutAmountsDao.close();
    }

    public void generateRecipeNutAmounts() {
        RecipeDao recipeDao = new RecipeDao();
        ArrayList<RecipeVo> recipes = recipeDao.selectAll();
        recipeDao.close();

        RecipeNutAmountsDao recipeNutDao = new RecipeNutAmountsDao();
        for (RecipeVo recipe : recipes) {
            RecipeNutAmountsVo amounts = recipeNutDao.selectById(recipe.getRecipeId());
            if (amounts == null) {
                amounts = SanteUtils.generateRecipeNutrientAmounts(recipe.getRecipeId());
                recipeNutDao.insert(amounts);
            }
        }
        recipeNutDao.close();
    }
}
