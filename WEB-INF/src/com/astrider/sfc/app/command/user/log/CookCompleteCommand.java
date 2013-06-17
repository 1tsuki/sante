package com.astrider.sfc.app.command.user.log;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.model.LogModel;
import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.FlashMessage.Type;

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
            redirect("/user/recipe/Detail?recipe_id=" + (String) request.getAttribute("recipe_id"));
        }
    }
}
