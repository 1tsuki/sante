package com.astrider.sfc.test.app.command.test;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.annotation.*;

@Title("タイトル")
public class FooCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
//        render("/Unknown");
    }
}
