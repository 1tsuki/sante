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
        	return returnFailStatus(request);
        }

        UserStatsVo userStats = null;
        UserStatsDao userStatsDao = new UserStatsDao();
        userStats = userStatsDao.selectByUserId(user.getUserId());
        userStatsDao.close();
        if (userStats == null) {
        	return returnFailStatus(request);
        }

        request.setAttribute("userStats", userStats);
        request.setAttribute("success", true);
        return true;
    }

    public boolean getNutrients(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");
        
        if (user == null) {
        	return returnFailStatus(request);
        }

        WeeklyLogVo weekVo = null;
    	weekVo = SanteUtils.getWeeklyLogOfThisWeek(user.getUserId());
        NutrientDao nutrientDao = new NutrientDao();
        ArrayList<NutrientVo> nutrients = nutrientDao.selectAll();
        nutrientDao.close();

        request.setAttribute("ingested", weekVo);
        request.setAttribute("desired", nutrients);
        request.setAttribute("success", true);
        return true;
    }

	public boolean getChartSource(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo user = (UserVo) session.getAttribute("loginUser");
		
		if (user == null) {
        	return returnFailStatus(request);
        }

		int week = 0;
		try {
			week = Integer.valueOf(request.getParameter("week"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		double[] items = SanteUtils.getChartSource(user.getUserId(), week);
        request.setAttribute("items", items);
        request.setAttribute("success", true);
        return true;
	}

	private boolean returnFailStatus(HttpServletRequest request) {
		request.setAttribute("success", false);
        request.setAttribute("message", "failed");
        return false;
	}
}
