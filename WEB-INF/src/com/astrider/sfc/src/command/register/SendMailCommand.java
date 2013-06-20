package com.astrider.sfc.src.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.RegisterModel;
import static com.astrider.sfc.ApplicationContext.*;

public class SendMailCommand extends Command {

	@Override
	public void doPost() throws ServletException, IOException {
		RegisterModel model = new RegisterModel();
		model.registerUser(request);
		flashMessage.addMessage(model.getFlashMessage());

		redirect(PAGE_REGISTER_INPUT);
	}
}
