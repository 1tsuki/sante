package com.astrider.sfc.src.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.helper.Mapper;
import com.astrider.sfc.app.lib.helper.FlashMessage.Type;
import com.astrider.sfc.app.lib.helper.StringUtils;
import com.astrider.sfc.app.lib.model.BaseModel;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.model.dao.NutrientDao;
import com.astrider.sfc.src.model.dao.RecipeDao;
import com.astrider.sfc.src.model.vo.db.MaterialQuantityVo;
import com.astrider.sfc.src.model.vo.db.NutrientVo;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.StepVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.form.SearchQueryVo;

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

        // requestに格納
        request.setAttribute("recipe", recipe);
        request.setAttribute("steps", steps);
        request.setAttribute("materialQuantities", materialQuantities);

        return true;
    }

    public boolean searchRecipes(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	UserVo loginUser = (UserVo) session.getAttribute("loginUser");
    	if (loginUser == null) {
    		return false;
    	}

    	Mapper<SearchQueryVo> mapper = new Mapper<SearchQueryVo>();
    	SearchQueryVo query = mapper.fromHttpRequest(request);

    	ArrayList<RecipeVo> recipes = null;
    	if (0 < query.getNutrientId()) {
    		RecipeDao recipeDao = new RecipeDao();
    		recipes = recipeDao.selectByNutrientId(query.getNutrientId());
    		recipeDao.close();
    		request.setAttribute("materialId", query.getNutrientId());
    		NutrientDao nutrientDao = new NutrientDao();
    		NutrientVo nutrient = nutrientDao.selectById(query.getNutrientId());
    		nutrientDao.close();
    		request.setAttribute("query", SanteUtils.convertPnameToLname(nutrient.getNutrientName()) + "を使った");
    	} else if (StringUtils.isNotEmpty(query.getMaterialName())) {
    		RecipeDao dao = new RecipeDao();
    		recipes = dao.selectByMaterialName(query.getMaterialName());
    		dao.close();
    		request.setAttribute("materialName", query.getMaterialName());
    		request.setAttribute("query", query.getMaterialName() + "を使った");
    	} else {
    		recipes = SanteUtils.getRecommendedRecipes(loginUser.getUserId(), 10);
    		request.setAttribute("query", "おすすめ");
    	}
    	request.setAttribute("recipes", recipes);
        return true;
    }
}
