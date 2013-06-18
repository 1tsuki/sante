package com.astrider.sfc.src.command.knowledge;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;

public class IndexCommand extends Command {
	@Override
    public void doGet() throws ServletException, IOException {
		render();
	}
}
