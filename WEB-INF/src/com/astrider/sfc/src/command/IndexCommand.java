package com.astrider.sfc.src.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.annotation.Title;
import com.astrider.sfc.src.model.vo.db.UserVo;

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
