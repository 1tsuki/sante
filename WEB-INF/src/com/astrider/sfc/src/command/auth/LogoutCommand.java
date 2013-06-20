package com.astrider.sfc.src.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.AuthModel;
import static com.astrider.sfc.ApplicationContext.*;

public class LogoutCommand extends Command {

	@Override
	public void doGet() throws ServletException, IOException {
		AuthModel apiModel = new AuthModel();
		apiModel.logout(request);
		flashMessage.addMessage(apiModel.getFlashMessage());
		redirect(PAGE_INDEX);
	}
}
