package com.astrider.sfc.src.command.user.recipe;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.FlashMessage.Type;
import com.astrider.sfc.src.model.RecipeModel;
import com.astrider.sfc.src.model.UserModel;

@Title("レシピ詳細")
public class DetailCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		RecipeModel recipeModel = new RecipeModel();
		boolean succeed = recipeModel.showDetail(request);
		if (!succeed) {
			flashMessage.addMessage(recipeModel.getFlashMessage());
			flashMessage.setMessageType(Type.WARNING);
			redirect("/user/recipe/Search");
			return;
		}

		UserModel userModel = new UserModel();
		userModel.getRecommendedRecipes(request);
		render();
	}
}
