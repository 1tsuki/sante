package com.astrider.sfc.src.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.AuthModel;

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
		redirect("/auth/Reissue");
	}
}
