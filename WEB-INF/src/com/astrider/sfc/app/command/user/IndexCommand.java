package com.astrider.sfc.app.command.user;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.annotation.Title;
import com.astrider.sfc.app.model.UserModel;

@Title("マイページ")
public class IndexCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
        UserModel userModel = new UserModel();
        userModel.getRecommendedRecipes(request);
        render();
    }
}
