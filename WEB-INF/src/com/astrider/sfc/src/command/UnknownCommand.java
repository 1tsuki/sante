package com.astrider.sfc.src.command;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.annotation.Title;

@Title("存在しないページ")
public class UnknownCommand extends Command {

    @Override
    public void doGet() throws ServletException, IOException {
        render("/Unknown.jsp");
    }

    @Override
    public void doPost() throws ServletException, IOException {
        render("/Unknown.jsp");
    }
}
