package com.astrider.sfc.src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.astrider.sfc.app.lib.model.BaseModel;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.model.dao.CookLogDao;
import com.astrider.sfc.src.model.dao.MaterialDao;
import com.astrider.sfc.src.model.dao.MealLogDao;
import com.astrider.sfc.src.model.dao.RecipeDao;
import com.astrider.sfc.src.model.dao.RecipeNutAmountsDao;
import com.astrider.sfc.src.model.dao.UserDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.dao.WeeklyLogDao;
import com.astrider.sfc.src.model.vo.db.CookLogVo;
import com.astrider.sfc.src.model.vo.db.MaterialVo;
import com.astrider.sfc.src.model.vo.db.MealLogVo;
import com.astrider.sfc.src.model.vo.db.RecipeMaterialVo;
import com.astrider.sfc.src.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.UserStatsVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.db.WeeklyLogVo;

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

    public void insertMaterials() {
    	MaterialDao materialDao = null;
    	try {
            materialDao = new MaterialDao();
            File csv = new File("/Users/astrider/Documents/workspace/recruit/sante/WEB-INF/test-data/materials.csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            int recipeId = -1;
            String materialName = "";
            String prefix = "";
            float quantity = -1;
            String postfix = "";

            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(",");
                recipeId = Integer.parseInt(splitted[0]);
                materialName = splitted[1];
                prefix = splitted[2];
                quantity = Float.parseFloat(splitted[3]);
                postfix = splitted[4];

                MaterialVo material = materialDao.selectByNameAndPrePostfix(materialName, prefix, postfix);
                if (material == null) {
                    System.out.println("inserting item " + materialName + " " + "prefix" + " " + postfix);
                    material = new MaterialVo();
                    material.setGramPerQuantity(1);
                    material.setPrefix(prefix);
                    material.setMaterialName(materialName);
                    material.setPostfix(postfix);
                    material.setNutrientId(12);
                    materialDao.insert(material);
                    material = materialDao.selectByNameAndPrePostfix(materialName, prefix, postfix);
                }
                RecipeMaterialVo recipeMaterialVo = new RecipeMaterialVo();
                recipeMaterialVo.setRecipeId(recipeId);
                recipeMaterialVo.setQuantity(quantity);
                recipeMaterialVo.setMaterialId(material.getMaterialId());
                materialDao.insert(recipeMaterialVo);
            }
            materialDao.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (materialDao != null) {
        		materialDao.close();
        	}
        }
    }
}
