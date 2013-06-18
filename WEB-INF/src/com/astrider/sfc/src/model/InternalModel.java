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
import com.astrider.sfc.src.model.dao.RecipeDao;
import com.astrider.sfc.src.model.dao.RecipeNutAmountsDao;
import com.astrider.sfc.src.model.dao.UserDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.vo.db.MaterialVo;
import com.astrider.sfc.src.model.vo.db.RecipeMaterialVo;
import com.astrider.sfc.src.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.UserStatsVo;
import com.astrider.sfc.src.model.vo.db.UserVo;

public class InternalModel extends BaseModel {
    public void execDailyBatch() {
        UserDao userDao = new UserDao();
        ArrayList<UserVo> users = userDao.selectAll();
        userDao.close();
        for (UserVo user : users) {
            checkDailyCookLogs(user);
        }
    }

    private void checkDailyCookLogs(UserVo user) {
        CookLogDao cookLogDao = new CookLogDao();
        if (0 < cookLogDao.countCookedAtYesterday(user.getUserId())) {
            // 連続調理日数の更新
        	UserStatsDao userStatsDao = new UserStatsDao();
        	UserStatsVo userStatsVo = userStatsDao.selectByUserId(user.getUserId());
            userStatsVo.setConsecutivelyCooked(0);
            userStatsDao.update(userStatsVo);
            userStatsDao.close();
        }
        cookLogDao.close();
    }

    public void generateRecipeNutAmounts() {
        RecipeDao recipeDao = new RecipeDao();
        ArrayList<RecipeVo> recipes = recipeDao.selectAll();
        recipeDao.close();

        RecipeNutAmountsDao recipeNutDao = new RecipeNutAmountsDao();
        boolean succeed = false;
        for (RecipeVo recipe : recipes) {
            RecipeNutAmountsVo amounts = recipeNutDao.selectById(recipe.getRecipeId());
            if (amounts == null) {
                amounts = SanteUtils.generateRecipeNutrientAmounts(recipe.getRecipeId());
                succeed = recipeNutDao.insertWithoutCommit(amounts);
                if (succeed) {
                	recipeNutDao.commit();
                } else {
                	recipeNutDao.rollback();
                }
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
            float quantity = -1;

            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(",");
                MaterialVo arg = new MaterialVo();
                arg.setMaterialName(splitted[1]);
                arg.setPrefix(splitted[2]);
                if (5 <= splitted.length) {
                	arg.setPostfix(splitted[4]);
                }

                recipeId = Integer.parseInt(splitted[0]);
                quantity = Float.parseFloat(splitted[3]);

                MaterialVo material = materialDao.selectByNameAndPrePostfix(arg);
                if (material == null) {
                	arg.setGramPerQuantity(1);
                    arg.setNutrientId(12);
                    materialDao.insert(arg);
                    material = materialDao.selectByNameAndPrePostfix(arg);
                }
                RecipeMaterialVo recipeMaterialVo = new RecipeMaterialVo();
                recipeMaterialVo.setRecipeId(recipeId);
                recipeMaterialVo.setQuantity(quantity);
                recipeMaterialVo.setMaterialName(material.getMaterialName());
                recipeMaterialVo.setPrefix(material.getPrefix());
                recipeMaterialVo.setPostfix(material.getPostfix());
                materialDao.insertWithoutCommit(recipeMaterialVo);
            }
            materialDao.commit();
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
