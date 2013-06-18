package com.astrider.sfc.src.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.FlashMessage.Type;
import com.astrider.sfc.src.model.AuthModel;

public class LogoutCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		AuthModel model = new AuthModel();
		boolean succeed = model.logout(request);
		if (succeed) {
			flashMessage.addMessage("ログアウトを実行しました");
			flashMessage.setMessageType(Type.INFO);
		} else {
			flashMessage.addMessage("不明なエラーが発生しました");
			flashMessage.setMessageType(Type.WARNING);
		}
		redirect("/Index");
	}
}
