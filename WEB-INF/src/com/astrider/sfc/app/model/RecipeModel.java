package com.astrider.sfc.app.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.model.dao.RecipeDao;
import com.astrider.sfc.app.model.vo.db.MaterialQuantityVo;
import com.astrider.sfc.app.model.vo.db.RecipeVo;
import com.astrider.sfc.app.model.vo.db.StepVo;
import com.astrider.sfc.lib.helper.FlashMessage.Type;
import com.astrider.sfc.lib.model.BaseModel;

public class RecipeModel extends BaseModel {

    public boolean getRecipeDetail(HttpServletRequest request) {
        // レシピID取得
        int recipeId = Integer.parseInt(request.getParameter("recipe_id"));
        if (recipeId == 0) {
            flashMessage.addMessage("該当するレシピは存在しませんでした");
            flashMessage.setMessageType(Type.WARNING);
            return false;
        }

        // レシピ取得
        RecipeDao recipeDao = new RecipeDao();
        RecipeVo recipe = recipeDao.selectByRecipeId(recipeId);
        if (recipe == null) {
            flashMessage.addMessage("該当するレシピは存在しませんでした");
            flashMessage.setMessageType(Type.WARNING);
            return false;
        }

        // 手順＆素材情報
        ArrayList<StepVo> steps = recipeDao.selectStepsByRecipeId(recipeId);
        ArrayList<MaterialQuantityVo> materialQuantities = recipeDao.selectMaterialQuantitiesByRecipeId(recipeId);
        recipeDao.close();

        // sessionに格納
        HttpSession session = request.getSession();
        session.setAttribute("recipe", recipe);
        session.setAttribute("steps", steps);
        session.setAttribute("materialQuantities", materialQuantities);

        return true;
    }

    public ArrayList<RecipeVo> getRecommendedRecipes() {
        return null;
    }
}
