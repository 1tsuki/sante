package com.astrider.sfc.app.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.annotation.Title;

@Title("認証成功")
public class SuccessCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
        render();
    }
}
