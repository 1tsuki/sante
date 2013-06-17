package com.astrider.sfc.app.command.user.recipe;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.model.RecipeModel;
import com.astrider.sfc.app.model.UserModel;
import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.annotation.Title;

@Title("レシピ")
public class DetailCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
        RecipeModel recipeModel = new RecipeModel();
        boolean succeed = recipeModel.getRecipeDetail(request);
        if (!succeed) {
            flashMessage.addMessage(recipeModel.getFlashMessage());
            redirect("/user/Index");
        }

        UserModel userModel = new UserModel();
        userModel.getRecommendedRecipes(request);
        render();
    }
}
