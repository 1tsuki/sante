package com.astrider.sfc.src.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.astrider.sfc.src.model.dao.NutrientDao;
import com.astrider.sfc.src.model.dao.RecipeDao;
import com.astrider.sfc.src.model.dao.WeeklyLogDao;
import com.astrider.sfc.src.model.vo.db.MaterialQuantityVo;
import com.astrider.sfc.src.model.vo.db.MealLogVo;
import com.astrider.sfc.src.model.vo.db.NutrientVo;
import com.astrider.sfc.src.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.WeeklyLogVo;
import com.sun.xml.internal.ws.util.StringUtils;

public final class SanteUtils {
	private static final int NUT_COUNT = 11;
    public static class InsufficientNutrients {
        private int primaryKey;
        private int secondaryKey;
        private String primaryNutrientName;
        private String secondaryNutrientName;
        public int getPrimaryKey() {
            return primaryKey;
        }
        public void setPrimaryKey(int primaryKey) {
            this.primaryKey = primaryKey;
        }
        public int getSecondaryKey() {
            return secondaryKey;
        }
        public void setSecondKey(int secondKey) {
            this.secondaryKey = secondKey;
        }
		public String getPrimaryNutrientName() {
			return primaryNutrientName;
		}
		public void setPrimaryNutrientName(String priamryNutrientName) {
			this.primaryNutrientName = priamryNutrientName;
		}
		public String getSecondaryNutrientName() {
			return secondaryNutrientName;
		}
		public void setSecondaryNutrientName(String secondaryNutrientName) {
			this.secondaryNutrientName = secondaryNutrientName;
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
        double[] balances = getNutrientBalances(userId);
        // 値が空ならば肉と野菜を返す
        boolean allZero = true;
        for (double item : balances) {
        	allZero = Double.isNaN(item) && allZero;
        }
        if (allZero) {
        	int primaryKey = 3;
        	int secondaryKey = 5;
        	NutrientDao nutrientDao = new NutrientDao();
            String primaryNutrientName = nutrientDao.selectById(primaryKey).getNutrientName();
            String secondaryNutrientName = nutrientDao.selectById(secondaryKey).getNutrientName();
            nutrientDao.close();
            InsufficientNutrients nutrients = new InsufficientNutrients();
            nutrients.setPrimaryKey(primaryKey);
            nutrients.setSecondKey(secondaryKey);
            nutrients.setPrimaryNutrientName(convertPnameToLname(primaryNutrientName));
            nutrients.setSecondaryNutrientName(convertPnameToLname(secondaryNutrientName));
            
            return nutrients;
        }
        
        // 最小値を取得
        double primary = 1;
        int primaryIndex = 0;
        for (int i = 0; i < balances.length; i++) {
        	if (balances[i] < primary) {
        		primary = balances[i];
        		primaryIndex = i;
        	}
		}

        // 二番目の最小値を取得
        double secondary = 1;
        int secondaryIndex = 0;
        for (int j=0; j < balances.length; j++) {
        	if (primary < balances[j] && balances[j] < secondary) {
        		secondary = balances[j];
        		secondaryIndex = j;
        	}
        }
        
        // 値を取得
        int primaryKey = primaryIndex + 1;
        int secondaryKey = secondaryIndex + 1;
        NutrientDao nutrientDao = new NutrientDao();
        String primaryNutrientName = nutrientDao.selectById(primaryKey).getNutrientName();
        String secondaryNutrientName = nutrientDao.selectById(secondaryKey).getNutrientName();
        nutrientDao.close();
        
        // 値をセット
        InsufficientNutrients nutrients = new InsufficientNutrients();
        nutrients.setPrimaryKey(primaryKey);
        nutrients.setSecondKey(secondaryKey);
        nutrients.setPrimaryNutrientName(convertPnameToLname(primaryNutrientName));
        nutrients.setSecondaryNutrientName(convertPnameToLname(secondaryNutrientName));
        
        return nutrients;
    }

    public static String convertPnameToLname(String arg) {
    	if (arg.equals("milk")) {
    		return "乳製品";
    	}
    	if (arg.equals("egg")) {
    		return "卵";
    	}
    	if (arg.equals("meat")) {
    		return "肉・魚類";
    	}
    	if (arg.equals("bean")) {
    		return "豆類";
    	}
    	if (arg.equals("vegetable")) {
    		return "野菜類";
    	}
    	if (arg.equals("fruit")) {
    		return "果物類";
    	}
    	if (arg.equals("mineral")) {
    		return "きのこ・海藻類";
    	}
    	if (arg.equals("crop")) {
    		return "炭水化物";
    	}
    	if (arg.equals("potato")) {
    		return "イモ類";
    	}
    	if (arg.equals("fat")) {
    		return "油脂";
    	}
    	if (arg.equals("suguar")) {
    		return "糖分";
    	}
    	return "不明な食材";
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

    public static void addMealToCurrentWeekLog(int userId, MealLogVo meal) {
    	WeeklyLogVo week = getWeeklyLogOfThisWeek(userId);
        week.setMilk(week.getMilk() + meal.getMilk());
        week.setEgg(week.getEgg() + meal.getEgg());
        week.setMeat(week.getMeat() + meal.getMeat());
        week.setBean(week.getBean() + meal.getBean());
        week.setVegetable(week.getVegetable() + meal.getVegetable());
        week.setFruit(week.getFruit() + meal.getFruit());
        week.setMineral(week.getMineral() + meal.getMineral());
        week.setCrop(week.getCrop() + meal.getCrop());
        week.setPotato(week.getPotato() + meal.getPotato());
        week.setFat(week.getFat() + meal.getFat());
        week.setSuguar(week.getSuguar() + meal.getSuguar());

        WeeklyLogDao weeklyNutAmountsDao = new WeeklyLogDao();
        weeklyNutAmountsDao.update(week);
        weeklyNutAmountsDao.close();
    }

    public static void updateTotalBalanceOfCurrentWeekLog(int userId) {
    	double[] nutrientBalances = getNutrientBalances(userId);
    	double diff = 0;
    	for (int i = 0; i < nutrientBalances.length; i++) {
    		if (1 < nutrientBalances[i]) {
    			nutrientBalances[i] = 1;
    		}
    		diff += 1 - nutrientBalances[i];
    	}
    	int totalBalance = (int) (diff / nutrientBalances.length * 100);
    	System.out.println(totalBalance);

    	WeeklyLogVo weekLog = getWeeklyLogOfThisWeek(userId);
    	weekLog.setTotalBalance(totalBalance);
    	WeeklyLogDao dao = new WeeklyLogDao();
    	dao.update(weekLog);
    	dao.close();
    }

    public static double[] getNutrientBalances(int userId) {
    	// 現在の栄養摂取量と理想量を取得
    	int[] ingested = getIngestedNutrients(userId);
    	int[] desired = getDesiredNutrients();

    	// 理想割合から飛び抜けた値を丸める
        double ingestedAverage = getAverage(ingested);
        double desiredAverage = getAverage(desired);
        double coefficient = ingestedAverage / desiredAverage;
        for (int i = 0; i < NUT_COUNT; i++) {
        	// 丸め処理
        	int limit = (int) (desired[i] * coefficient);
        	if (limit < ingested[i]) {
        		ingested[i] = limit;
        	}
        }

        // 栄養バランスを再計算
        ingestedAverage = getAverage(ingested);
        desiredAverage = getAverage(desired);
        double[] items = new double[NUT_COUNT];
        for (int j = 0; j < NUT_COUNT; j++) {
        	double desiredDiff = (desired[j] - desiredAverage) / desiredAverage;
        	double ingestedDiff = (ingested[j] - ingestedAverage) / ingestedAverage;
        	double diff = (ingestedDiff - desiredDiff) / 2 + 0.5;
        	items[j] = diff;
        }
    	
        return items;
    }
    
    private static double getAverage(int[] items) {
    	int amount = 0;
    	for (int i=0; i < items.length; i++) {
    		amount += items[i];
    	}
    	return amount / items.length;
    }

    private static int[] getIngestedNutrients(int userId) {
    	WeeklyLogVo weekVo = SanteUtils.getWeeklyLogOfThisWeek(userId);
        int[] ingested = {
        		weekVo.getMilk(), weekVo.getEgg(), weekVo.getMeat(), weekVo.getBean(),
        		weekVo.getVegetable(), weekVo.getFruit(), weekVo.getMineral(), 
        		weekVo.getCrop(), weekVo.getPotato(), weekVo.getFat(), weekVo.getSuguar()
        };
        return ingested;
    }

    private static int[] getDesiredNutrients() {
    	int[] desired = new int[NUT_COUNT];
    	NutrientDao nutrientDao = new NutrientDao();
        for (int i = 1; i < desired.length; i++) {
        	desired[i] = nutrientDao.selectById(i+1).getDailyRequiredAmount();
        }
        nutrientDao.close();
        return desired;
    }

    public static RecipeNutAmountsVo generateRecipeNutrientAmounts(int recipeId) {
        RecipeDao recipeDao = new RecipeDao();
        ArrayList<MaterialQuantityVo> materials = recipeDao.selectMaterialQuantitiesByRecipeId(recipeId);
        recipeDao.close();
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
            	recipeNutrients.setFruit(recipeNutrients.getFruit() + gram);
                break;
            case 7:
                recipeNutrients.setMineral(recipeNutrients.getMineral() + gram);
                break;
            case 8:
                recipeNutrients.setCrop(recipeNutrients.getCrop() + gram);
                break;
            case 9:
                recipeNutrients.setPotato(recipeNutrients.getPotato() + gram);
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
                recipeDao.selectRecipeIdByNutrientId(nutrients.getPrimaryKey()),
                recipeDao.selectRecipeIdByNutrientId(nutrients.getSecondaryKey()));
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
