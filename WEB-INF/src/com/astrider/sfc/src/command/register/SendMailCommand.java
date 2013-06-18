package com.astrider.sfc.src.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.FlashMessage.Type;
import com.astrider.sfc.src.model.RegisterModel;

public class SendMailCommand extends Command {
	@Override
	public void doPost() throws ServletException, IOException {
		RegisterModel model = new RegisterModel();
		boolean succeed = model.registerUser(request);
		if (succeed) {
			flashMessage
					.addMessage("お客様のメールアドレスに仮登録完了メールが送信されました。メール記載のリンクから本登録手続きを完了してください。");
			flashMessage.setMessageType(Type.INFO);
		} else {
			flashMessage.addMessage(model.getFlashMessage());
			flashMessage.setMessageType(Type.WARNING);
		}
		redirect("/register/Input");
	}
}
