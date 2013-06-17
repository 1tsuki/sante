package com.astrider.sfc.app.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.astrider.sfc.app.model.dao.NutrientDao;
import com.astrider.sfc.app.model.dao.RecipeDao;
import com.astrider.sfc.app.model.dao.WeeklyLogDao;
import com.astrider.sfc.app.model.vo.db.MaterialQuantityVo;
import com.astrider.sfc.app.model.vo.db.MealLogVo;
import com.astrider.sfc.app.model.vo.db.NutrientVo;
import com.astrider.sfc.app.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.app.model.vo.db.RecipeVo;
import com.astrider.sfc.app.model.vo.db.WeeklyLogVo;
import com.sun.xml.internal.ws.util.StringUtils;

public final class SanteUtils {
    public static class InsufficientNutrients {
        private int firstKey;
        private int secondKey;
        public int getFirstKey() {
            return firstKey;
        }
        public void setFirstKey(int firstKey) {
            this.firstKey = firstKey;
        }
        public int getSecondKey() {
            return secondKey;
        }
        public void setSecondKey(int secondKey) {
            this.secondKey = secondKey;
        }
    }

    private SanteUtils() {
    }

    public static int calcTotalBalance(RecipeNutAmountsVo recipeNut) {
        NutrientDao nutrientDao = new NutrientDao();
        ArrayList<NutrientVo> nutrients = nutrientDao.selectAll();

        float sum = 0;
        int counter = 0;
        for (NutrientVo nutrient : nutrients) {
            try {
                String name = nutrient.getNutrientName();
                if (name.equals("mineral")) {
                    continue;
                }

                Method m = recipeNut.getClass().getMethod("get" + StringUtils.capitalize(name));
                int actual = (Integer) m.invoke(recipeNut);
                int required = nutrient.getDailyRequiredAmount();
                sum += Math.abs((actual / required) - 1);
                counter++;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return (int) (sum / counter);
    }

    public static InsufficientNutrients getInsufficientNutrients(int userId) {
        // TODO implement better method
        InsufficientNutrients nutrients = new InsufficientNutrients();
        nutrients.setFirstKey(5);
        nutrients.setSecondKey(8);
        return nutrients;
    }

    public static ArrayList<RecipeVo> getRecommendedRecipes(int userId, int limit) {
        // TODO nasty method
        InsufficientNutrients nutrients = getInsufficientNutrients(userId);
        ArrayList<RecipeVo> recipes = getRecipesContainBothNutrients(nutrients, limit);
        return recipes;
    }

    public static ArrayList<RecipeVo> getRecommendedRecipes(int userId) {
        return getRecommendedRecipes(userId, 4);
    }

    public static WeeklyLogVo getWeeklyLogOfThisWeek(int userId) {
        WeeklyLogDao dao = new WeeklyLogDao();
        WeeklyLogVo vo = dao.selectItemOfThisWeek(userId);
        if (vo == null) {
            vo = new WeeklyLogVo();
            vo.setUserId(userId);
            dao.insert(vo);
        }
        dao.close();
        return vo;
    }

    public static void joinWeeklyAndMealLog(WeeklyLogVo week, MealLogVo meal) {
        week.setMilk(week.getMilk() + meal.getMilk());
        week.setEgg(week.getEgg() + meal.getEgg());
        week.setMeat(week.getMeat() + meal.getMeat());
        week.setBean(week.getBean() + meal.getBean());
        week.setVegetable(week.getVegetable() + meal.getVegetable());
        week.setPotato(week.getPotato() + meal.getPotato());
        week.setFruit(week.getFruit() + meal.getFruit());
        week.setMineral(week.getMineral() + meal.getMineral());
        week.setCrop(week.getCrop() + meal.getCrop());
        week.setFat(week.getFat() + meal.getFat());
        week.setSuguar(week.getSuguar() + meal.getSuguar());
    }

    public static RecipeNutAmountsVo generateRecipeNutrientAmounts(int recipeId) {
        RecipeDao recipeDao = new RecipeDao();
        ArrayList<MaterialQuantityVo> materials = recipeDao.selectMaterialQuantitiesByRecipeId(recipeId);
        RecipeNutAmountsVo recipeNutrients = new RecipeNutAmountsVo();
        recipeNutrients.setRecipeId(recipeId);
        for (MaterialQuantityVo material : materials) {
            int nutrientId = material.getNutrientId();
            float amount = material.getGramPerQuantity() * material.getQuantity();
            addNutrientToAmountById(nutrientId, (int) amount, recipeNutrients);
        }
        return recipeNutrients;
    }

    private static void addNutrientToAmountById(int nutrientId, int gram, RecipeNutAmountsVo recipeNutrients) {
        switch (nutrientId) {
            case 1:
                recipeNutrients.setMilk(recipeNutrients.getMilk() + gram);
                break;
            case 2:
                recipeNutrients.setEgg(recipeNutrients.getEgg() + gram);
                break;
            case 3:
                recipeNutrients.setMeat(recipeNutrients.getMeat() + gram);
                break;
            case 4:
                recipeNutrients.setBean(recipeNutrients.getBean() + gram);
                break;
            case 5:
                recipeNutrients.setVegetable(recipeNutrients.getVegetable() + gram);
                break;
            case 6:
                recipeNutrients.setPotato(recipeNutrients.getPotato() + gram);
                break;
            case 7:
                recipeNutrients.setFruit(recipeNutrients.getFruit() + gram);
                break;
            case 8:
                recipeNutrients.setMineral(recipeNutrients.getMineral() + gram);
                break;
            case 9:
                recipeNutrients.setCrop(recipeNutrients.getCrop() + gram);
                break;
            case 10:
                recipeNutrients.setFat(recipeNutrients.getFat() + gram);
                break;
            case 11:
                recipeNutrients.setSuguar(recipeNutrients.getSuguar() + gram);
                break;
            default:
                break;
        }
    }

    private static ArrayList<RecipeVo> getRecipesContainBothNutrients(InsufficientNutrients nutrients, int limit) {
        ArrayList<RecipeVo> recipes = new ArrayList<RecipeVo>();
        RecipeDao recipeDao = new RecipeDao();
        ArrayList<Integer> recipesContainsBoth = extractDuplicateKeys(
                recipeDao.selectRecipeIdByNutrientId(nutrients.getFirstKey()),
                recipeDao.selectRecipeIdByNutrientId(nutrients.getSecondKey()));
        if (recipesContainsBoth.size() < limit) {
            limit = recipesContainsBoth.size();
        }
        for (int i = 0; i < limit; i++) {
            int recipeId = recipesContainsBoth.get(i);
            RecipeVo recipe = recipeDao.selectByRecipeId(recipeId);
            recipes.add(recipe);
        }
        recipeDao.close();

        return recipes;
    }

    private static ArrayList<Integer> extractDuplicateKeys(
            ArrayList<Integer> firstKeys, ArrayList<Integer> secondKeys) {
        ArrayList<Integer> duplicate = new ArrayList<Integer>();
        for (int first : firstKeys) {
            for (int second : secondKeys) {
                if (first == second) {
                    duplicate.add(first);
                }
            }
        }
        return duplicate;
    }

}
