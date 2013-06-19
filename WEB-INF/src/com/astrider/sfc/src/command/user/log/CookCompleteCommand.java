package com.astrider.sfc.src.command.user.log;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.FlashMessage.Type;
import com.astrider.sfc.src.model.LogModel;

public class CookCompleteCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		LogModel model = new LogModel();
		boolean succeed = model.cookComplete(request);
		if (succeed) {
			flashMessage.addMessage("調理完了おめでとうございます！");
			flashMessage.setMessageType(Type.INFO);
			redirect("/user/Index");
		} else {
			flashMessage.addMessage(model.getFlashMessage());
			flashMessage.setMessageType(Type.WARNING);
			redirect("/user/Index");
		}
	}
}
