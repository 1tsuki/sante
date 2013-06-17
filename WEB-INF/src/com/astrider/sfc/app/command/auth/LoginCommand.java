package com.astrider.sfc.app.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.model.AuthModel;
import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.FlashMessage.Type;

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
