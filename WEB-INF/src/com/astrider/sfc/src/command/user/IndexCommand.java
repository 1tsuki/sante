package com.astrider.sfc.src.command.user;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.UserModel;

@Title("マイページ")
public class IndexCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		UserModel userModel = new UserModel();
		userModel.getRecommendedRecipes(request);
		render();
	}
}
