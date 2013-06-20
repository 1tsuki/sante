package com.astrider.sfc.src.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.RegisterModel;

import static com.astrider.sfc.ApplicationContext.*;

public class ConfirmEmailCommand extends Command {

	@Override
	public void doGet() throws ServletException, IOException {
		RegisterModel model = new RegisterModel();
		boolean succeed = model.confirmMail(request);
		flashMessage.addMessage(model.getFlashMessage());

		if (succeed) {
			redirect(PAGE_REGISTER_SUCCESS);
		} else {
			redirect(PAGE_REGISTER_FAIL);
		}
	}
}
