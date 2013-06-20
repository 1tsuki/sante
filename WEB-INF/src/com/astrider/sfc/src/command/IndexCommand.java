package com.astrider.sfc.src.command;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.helper.SanteUtils;
import static com.astrider.sfc.ApplicationContext.*;

@Title("トップページ")
public class IndexCommand extends Command {

	@Override
	public void doGet() throws ServletException, IOException {
		if (SanteUtils.getLoginUser(request) != null) {
			redirect(PAGE_USER_INDEX);
		} else {
			render();
		}
	}
}
