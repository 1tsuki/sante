package com.astrider.sfc.src.command.user.log;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.LogModel;
import static com.astrider.sfc.ApplicationContext.*;

public class CookCompleteCommand extends Command {

	@Override
	public void doGet() throws ServletException, IOException {
		LogModel model = new LogModel();
		boolean succeed = model.cookComplete(request);
		flashMessage.addMessage(model.getFlashMessage());
		
		if (succeed) {
			redirect(PAGE_USER_INDEX);
		} else {
			redirect(PAGE_USER_INDEX);
		}
	}
}
