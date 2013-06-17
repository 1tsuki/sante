package com.astrider.sfc.app.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.annotation.Title;

@Title("認証失敗")
public class FailCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
        render();
    }
}
