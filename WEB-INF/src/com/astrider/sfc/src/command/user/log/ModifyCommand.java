package com.astrider.sfc.src.command.user.log;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.annotation.Title;

@Title("食事ログ編集")
public class ModifyCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
	}
}
