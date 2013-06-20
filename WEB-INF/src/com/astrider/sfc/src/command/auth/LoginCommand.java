package com.astrider.sfc.src.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.AuthModel;

import static com.astrider.sfc.ApplicationContext.*;

public class LoginCommand extends Command {

	@Override
	public void doPost() throws ServletException, IOException {
		AuthModel authModel = new AuthModel();
		boolean succeed = authModel.authLogin(request);
		flashMessage.addMessage(authModel.getFlashMessage());

		if (succeed) {
			redirect(PAGE_USER_INDEX);
		} else {
			redirect(PAGE_INDEX);
		}
	}
}
