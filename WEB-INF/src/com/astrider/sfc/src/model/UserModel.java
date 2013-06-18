package com.astrider.sfc.src.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.astrider.sfc.app.lib.model.BaseModel;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.helper.SanteUtils.InsufficientNutrients;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.UserVo;

/**
 * ユーザー関連Model.
 * 
 * @author astrider
 * 
 */
public class UserModel extends BaseModel {

	/**
	 * マイページ用.
	 * 
	 * @param request
	 * @return
	 */
	public boolean getRecommendedRecipes(HttpServletRequest request) {
		UserVo user = SanteUtils.getLoginUser(request);
		if (user == null) {
			return false;
		}

		InsufficientNutrients nutrients = SanteUtils.getInsufficientNutrients(user.getUserId());
		request.setAttribute("nutrients", nutrients);

		ArrayList<RecipeVo> recipes = SanteUtils.getRecommendedRecipes(user.getUserId(), 4);
		request.setAttribute("recommendedRecipes", recipes);
		return true;
	}
}
