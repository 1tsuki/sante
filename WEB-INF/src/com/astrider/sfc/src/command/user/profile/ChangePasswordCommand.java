package com.astrider.sfc.src.command.user.profile;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.UserModel;

public class ChangePasswordCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		render();
	}
	
	@Override
	public void doPost() throws ServletException, IOException {
		UserModel userModel = new UserModel();
		userModel.changePassword(request);
		flashMessage.addMessage(userModel.getFlashMessage());
		render();
	}
}
