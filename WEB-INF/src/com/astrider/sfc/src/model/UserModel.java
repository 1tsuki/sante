package com.astrider.sfc.src.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.model.BaseModel;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.helper.SanteUtils.InsufficientNutrients;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.UserVo;

public class UserModel extends BaseModel {

    public boolean getRecommendedRecipes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");
        if (user == null) {
            return false;
        }

        InsufficientNutrients nutrients = SanteUtils.getInsufficientNutrients(user.getUserId());
        session.setAttribute("nutrients", nutrients);

        ArrayList<RecipeVo> recipes = SanteUtils.getRecommendedRecipes(user.getUserId());
        session.setAttribute("recommendedRecipes", recipes);
        return true;
    }
}
