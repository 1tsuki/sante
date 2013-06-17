package com.astrider.sfc.app.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.model.vo.db.UserVo;
import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.annotation.Title;

@Title("トップページ")
public class IndexCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserVo user = (UserVo) session.getAttribute("loginUser");
        if (user != null) {
            redirect("/user/Index");
        } else {
            render();
        }
    }
}
