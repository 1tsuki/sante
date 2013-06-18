package com.astrider.sfc.src.command.knowledge;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.annotation.Title;

@Title("栄養知識")
public class IndexCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		render();
	}
}
