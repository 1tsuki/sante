package com.astrider.sfc.src.command.user.recipe;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.RecipeModel;
import com.astrider.sfc.src.model.UserModel;
import static com.astrider.sfc.ApplicationContext.*;

@Title("レシピ詳細")
public class DetailCommand extends Command {

	@Override
	public void doGet() throws ServletException, IOException {
		RecipeModel recipeModel = new RecipeModel();
		boolean recipeSucceed = recipeModel.showDetail(request);
		flashMessage.addMessage(recipeModel.getFlashMessage());
		
		UserModel userModel = new UserModel();
		boolean userSucceed = userModel.getRecommendedRecipes(request);
		flashMessage.addMessage(recipeModel.getFlashMessage());

		if (recipeSucceed && userSucceed) {
			render();
		} else {
			redirect(PAGE_USER_RECIPE_SEARCH);
		}	
	}
}
