package com.astrider.sfc.src.command.user.log;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.annotation.Title;

@Title("月別食事ログ")
public class MonthlyCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
	}
}
