package com.astrider.sfc.src.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.AuthModel;

@Title("パスワード再発行")
public class ReissueCommand extends Command {

	@Override
	public void doGet() throws ServletException, IOException {
		render();
	}

	@Override
	public void doPost() throws ServletException, IOException {
		AuthModel model = new AuthModel();
		model.reissue(request);
		flashMessage.addMessage(model.getFlashMessage());

		render();
	}
}
