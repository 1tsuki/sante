package com.astrider.sfc.src.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;

@Title("新規登録")
public class InputCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		render();
	}
}
