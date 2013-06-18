package com.astrider.sfc.src.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.astrider.sfc.app.model.BaseModel;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.helper.WeeklyLogUtils;
import com.astrider.sfc.src.model.dao.NutrientDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.vo.db.NutrientVo;
import com.astrider.sfc.src.model.vo.db.UserStatsVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.db.WeeklyLogVo;

/**
 * API関連Model.
 * 
 * @author astrider
 * 
 */
public class ApiModel extends BaseModel {

	/**
	 * ユーザーステータス取得API.
	 * 
	 * @param request
	 * @return
	 */
	public boolean getStats(HttpServletRequest request) {
		UserVo user = SanteUtils.getLoginUser(request);
		if (user == null) {
			return returnFailStatus(request);
		}

		UserStatsDao userStatsDao = new UserStatsDao();
		UserStatsVo userStats = userStatsDao.selectByUserId(user.getUserId());
		userStatsDao.close();
		if (userStats == null) {
			return returnFailStatus(request);
		}

		request.setAttribute("userStats", userStats);
		request.setAttribute("success", true);
		return true;
	}

	/**
	 * 栄養素取得API.
	 * 
	 * @param request
	 * @return
	 */
	public boolean getNutrients(HttpServletRequest request) {
		UserVo user = SanteUtils.getLoginUser(request);
		if (user == null) {
			return returnFailStatus(request);
		}

		WeeklyLogVo weekVo = WeeklyLogUtils.getCurrentLog(user.getUserId());
		NutrientDao nutrientDao = new NutrientDao();
		ArrayList<NutrientVo> nutrients = nutrientDao.selectAll();
		nutrientDao.close();

		request.setAttribute("ingested", weekVo);
		request.setAttribute("desired", nutrients);
		request.setAttribute("success", true);
		return true;
	}

	/**
	 * 円グラフ用API.
	 * 
	 * @param request
	 * @return
	 */
	public boolean getChartSource(HttpServletRequest request) {
		UserVo user = SanteUtils.getLoginUser(request);
		if (user == null) {
			return returnFailStatus(request);
		}

		// requestParametersからweekAgoを取得
		int weekAgo = 0;
		try {
			weekAgo = Integer.valueOf(request.getParameter("weekAgo"));
			if (weekAgo < 0) {
				weekAgo = 0;
			}
		} catch (NumberFormatException e) {
			weekAgo = 0;
		}

		double[] items = SanteUtils.getNutrientBalances(user.getUserId(), weekAgo);
		if (items == null) {
			return returnFailStatus(request);
		}

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
