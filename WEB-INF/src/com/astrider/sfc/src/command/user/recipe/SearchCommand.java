package com.astrider.sfc.src.command.user.recipe;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.RecipeModel;

public class SearchCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
    	RecipeModel model = new RecipeModel();
    	model.searchRecipes(request);
    	render();
    }

    @Override
    public void doPost() throws ServletException, IOException {
    	RecipeModel model = new RecipeModel();
    	model.searchRecipes(request);
    	render();
    }
}
