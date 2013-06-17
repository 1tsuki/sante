package com.astrider.sfc.app.model;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.helper.SanteUtils;
import com.astrider.sfc.app.model.dao.MealLogDao;
import com.astrider.sfc.app.model.dao.NutrientDao;
import com.astrider.sfc.app.model.dao.UserStatsDao;
import com.astrider.sfc.app.model.vo.db.NutrientVo;
import com.astrider.sfc.app.model.vo.db.UserStatsVo;
import com.astrider.sfc.app.model.vo.db.UserVo;
import com.astrider.sfc.app.model.vo.db.WeeklyLogVo;
import com.astrider.sfc.lib.helper.DateUtils;
import com.astrider.sfc.lib.model.BaseModel;

public class ApiModel extends BaseModel {

    public boolean getStats(HttpServletRequest request) {
        boolean succeed = false;
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");

        UserStatsVo userStats = null;
        if (user != null) {
            UserStatsDao userStatsDao = new UserStatsDao();
            userStats = userStatsDao.selectByUserId(user.getUserId());
            userStatsDao.close();
            if (userStats != null) {
                succeed = true;
            }
        }

        if (succeed) {
            session.setAttribute("userStats", userStats);
            session.setAttribute("success", true);
            return true;
        } else {
            session.setAttribute("success", false);
            session.setAttribute("message", "failed");
            return false;
        }
    }

    public boolean getNutrients(HttpServletRequest request) {
        boolean succeed = false;
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");

        int dayPassed = 0;
        WeeklyLogVo weekVo = null;
        if (user != null) {
            weekVo = SanteUtils.getWeeklyLogOfThisWeek(user.getUserId());
            Calendar today = Calendar.getInstance();
            Calendar firstDate = Calendar.getInstance();
            firstDate.setTime(weekVo.getFirstDate());
            firstDate.add(Calendar.DATE, 1);
            dayPassed =DateUtils.getDayPassed(firstDate, today);
            succeed = true;
        }

        NutrientDao nutrientDao = new NutrientDao();
        ArrayList<NutrientVo> nutrients = nutrientDao.selectAll();
        nutrientDao.close();

        MealLogDao mealDao = new MealLogDao();
        int mealCount = mealDao.countMealOfThisWeek(user.getUserId());
        mealDao.close();
        if (mealCount < 0) {
            mealCount = 0;
        }

        if (succeed) {
            session.setAttribute("dayPassed", dayPassed);
            session.setAttribute("ingested", weekVo);
            session.setAttribute("desired", nutrients);
            session.setAttribute("mealCount", mealCount);
            session.setAttribute("success", true);
            return true;
        } else {
            session.setAttribute("success", false);
            session.setAttribute("message", "failed");
            return false;
        }
    }

}
