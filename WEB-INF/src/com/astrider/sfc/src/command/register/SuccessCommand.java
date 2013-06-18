package com.astrider.sfc.src.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;

@Title("認証成功")
public class SuccessCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		render();
	}
}
