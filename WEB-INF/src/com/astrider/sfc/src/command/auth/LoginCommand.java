package com.astrider.sfc.src.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.FlashMessage.Type;
import com.astrider.sfc.src.model.AuthModel;

public class LoginCommand extends Command {
	@Override
	public void doPost() throws ServletException, IOException {
		AuthModel model = new AuthModel();
		boolean succeed = model.authLogin(request);
		if (succeed) {
			redirect("/user/Index");
		} else {
			flashMessage.addMessage(model.getFlashMessage());
			flashMessage.setMessageType(Type.WARNING);
			redirect("/Index");
		}
	}
}
