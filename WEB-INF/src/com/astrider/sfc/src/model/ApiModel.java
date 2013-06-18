package com.astrider.sfc.src.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.model.BaseModel;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.model.dao.NutrientDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.vo.db.NutrientVo;
import com.astrider.sfc.src.model.vo.db.UserStatsVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.db.WeeklyLogVo;

public class ApiModel extends BaseModel {

    public boolean getStats(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");
        
        if (user == null) {
        	return returnFailStatus(session);
        }

        UserStatsVo userStats = null;
        UserStatsDao userStatsDao = new UserStatsDao();
        userStats = userStatsDao.selectByUserId(user.getUserId());
        userStatsDao.close();
        if (userStats == null) {
            return returnFailStatus(session);
        }

        session.setAttribute("userStats", userStats);
        session.setAttribute("success", true);
        return true;
    }

    public boolean getNutrients(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");
        
        if (user == null) {
        	return returnFailStatus(session);
        }

        WeeklyLogVo weekVo = null;
    	weekVo = SanteUtils.getWeeklyLogOfThisWeek(user.getUserId());
        NutrientDao nutrientDao = new NutrientDao();
        ArrayList<NutrientVo> nutrients = nutrientDao.selectAll();
        nutrientDao.close();

        session.setAttribute("ingested", weekVo);
        session.setAttribute("desired", nutrients);
        session.setAttribute("success", true);
        return true;
    }

	public boolean getChartSource(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo user = (UserVo) session.getAttribute("loginUser");
		
		if (user == null) {
        	return returnFailStatus(session);
        }

		
		double[] items = SanteUtils.getNutrientBalances(user.getUserId());
        session.setAttribute("items", items);
        session.setAttribute("success", true);
        return true;
	}

	private boolean returnFailStatus(HttpSession session) {
		session.setAttribute("success", false);
        session.setAttribute("message", "failed");
        return false;
	}
}
