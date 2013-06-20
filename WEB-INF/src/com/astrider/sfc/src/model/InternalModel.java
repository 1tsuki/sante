package com.astrider.sfc.src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.astrider.sfc.app.lib.BaseModel;
import com.astrider.sfc.app.lib.Mailer;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.helper.SanteUtils.InsufficientNutrients;
import com.astrider.sfc.src.model.dao.CookLogDao;
import com.astrider.sfc.src.model.dao.MaterialDao;
import com.astrider.sfc.src.model.dao.RecipeDao;
import com.astrider.sfc.src.model.dao.RecipeNutAmountsDao;
import com.astrider.sfc.src.model.dao.UserDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.vo.db.MaterialQuantityVo;
import com.astrider.sfc.src.model.vo.db.MaterialVo;
import com.astrider.sfc.src.model.vo.db.RecipeMaterialVo;
import com.astrider.sfc.src.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.UserStatsVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import static com.astrider.sfc.ApplicationContext.*;

/**
 * 内部ツール関連Model
 * 
 * @author astrider
 * 
 */
public class InternalModel extends BaseModel {
	/**
	 * 連続調理日数のリセット
	 */
	public void execDailyBatch() {
		UserDao userDao = new UserDao();
		ArrayList<UserVo> users = userDao.selectAll();
		userDao.close();

		CookLogDao cookLogDao = new CookLogDao();
		UserStatsDao userStatsDao = new UserStatsDao();
		for (UserVo user : users) {
			// 前日の調理履歴が0件ならば連続調理日数をリセット
			if (0 == cookLogDao.countCookedAtYesterday(user.getUserId())) {
				UserStatsVo userStatsVo = userStatsDao.selectByUserId(user.getUserId());
				userStatsVo.setConsecutivelyCooked(0);
				userStatsDao.update(userStatsVo, false);
			}
		}
		userStatsDao.commit();
		cookLogDao.close();
		userStatsDao.close();
	}

	/**
	 * 全レシピの栄養素統計情報を計算
	 */
	public void generateRecipeNutAmounts() {
		RecipeDao recipeDao = new RecipeDao();
		ArrayList<RecipeVo> recipes = recipeDao.selectAll();
		recipeDao.close();

		RecipeNutAmountsDao recipeNutDao = new RecipeNutAmountsDao();
		boolean succeed = true;
		for (RecipeVo recipe : recipes) {
			// RecipeNutAmountsが存在しなければ新規作成
			RecipeNutAmountsVo amounts = recipeNutDao.selectById(recipe.getRecipeId());
			if (amounts == null) {
				amounts = calculateRecipeNutAmounts(recipe.getRecipeId());
				succeed = recipeNutDao.insert(amounts, false) && succeed;
			}
		}

		if (succeed) {
			recipeNutDao.commit();
		} else {
			recipeNutDao.rollback();
		}
		recipeNutDao.close();
	}

	private RecipeNutAmountsVo calculateRecipeNutAmounts(int recipeId) {
		RecipeDao recipeDao = new RecipeDao();
		ArrayList<MaterialQuantityVo> materials = recipeDao.selectMaterialQuantitiesByRecipeId(recipeId);
		recipeDao.close();

		RecipeNutAmountsVo recipeNutrients = new RecipeNutAmountsVo();
		recipeNutrients.setRecipeId(recipeId);
		for (MaterialQuantityVo material : materials) {
			int nutrientId = material.getNutrientId();
			float amount = material.getGramPerQuantity() * material.getQuantity();
			addNutrientToAmountById(recipeNutrients, nutrientId, (int) amount);
		}
		return recipeNutrients;
	}

	private void addNutrientToAmountById(RecipeNutAmountsVo recipeNutrients, int nutrientId, int gram) {
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

	/**
	 * 素材情報挿入用ツール。
	 */
	public void insertMaterials() {
		MaterialDao materialDao = null;
		try {
			materialDao = new MaterialDao();
			File csv = new File(SANTE_MATERIALS_CSV);
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
				materialDao.insert(recipeMaterialVo, false);
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

	/**
	 * おすすめレシピ定期送信用バッチメソッド.
	 * 
	 * @param request
	 */
	public void sendRecommendMail(HttpServletRequest request) {
		UserDao userDao = new UserDao();
		ArrayList<UserVo> users = userDao.selectAll();

		// 全ての有効なユーザーにレシピを送信
		for (UserVo user : users) {
			if (user.isAvailable()) {
				ArrayList<RecipeVo> recipes = SanteUtils.getRecommendedRecipes(user.getUserId(), 4);
				InsufficientNutrients nutrients = SanteUtils.getInsufficientNutrients(user.getUserId());
				StringBuilder sb = new StringBuilder();
				
				sb.append(user.getUserName() + "様\n\n");
				sb.append("santeが本日のおすすめレシピをお届けします。\n");
				sb.append(user.getUserName() + "様に不足している栄養素は\n\n");
				sb.append(" ・" + nutrients.getPrimaryNutrientName() + "\n");
				sb.append(" ・" + nutrients.getSecondaryNutrientName() + "\n\n");
				sb.append("と判断されました。その条件に合致するレシピは下記の通りです。\n\n");
				
				// レシピリンク出力
				for (RecipeVo recipe : recipes) {
					sb.append(recipe.getRecipeName());
					sb.append("\n");
					sb.append("https://");
					sb.append(request.getServerName());
					sb.append(request.getContextPath());
					sb.append(PAGE_USER_RECIPE_DETAIL + "?recipe_id=");
					sb.append(recipe.getRecipeId());
					sb.append("\n");
				}
				sb.append("\nその他お勧めレシピはこちらからお探しいただけます。\n");
				sb.append("https://");
				sb.append(request.getServerName());
				sb.append(request.getContextPath());
				sb.append(PAGE_USER_RECIPE_SEARCH + "\n\n");
				sb.append("----------\n");
				sb.append("sante運営事務局");
				String title = "【sante】本日のおすすめレシピ";
				String body = sb.toString();
				Mailer mailer = new Mailer(user.getEmail(), title, body);
				mailer.send();
			}
		}
	}
}
