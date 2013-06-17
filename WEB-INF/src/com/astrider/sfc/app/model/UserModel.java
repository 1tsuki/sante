package com.astrider.sfc.app.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.helper.SanteUtils;
import com.astrider.sfc.app.model.vo.db.RecipeVo;
import com.astrider.sfc.app.model.vo.db.UserVo;
import com.astrider.sfc.lib.model.BaseModel;

public class UserModel extends BaseModel {

    public boolean getRecommendedRecipes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");
        if (user == null) {
            return false;
        }

        ArrayList<RecipeVo> recipes = SanteUtils.getRecommendedRecipes(user.getUserId());
        session.setAttribute("recommendedRecipes", recipes);
        return true;
    }

}
