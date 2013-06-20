package com.astrider.sfc.src.command.internal;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.util.AuthUtils;
import com.astrider.sfc.src.model.InternalModel;

import static com.astrider.sfc.ApplicationContext.*;

public class DailyBatchCommand extends Command {

	@Override
	public void doGet() throws ServletException, IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");

		if (id.equals(ADMIN_ID) && AuthUtils.verify(password, ADMIN_PASS)) {
			InternalModel model = new InternalModel();
			model.execDailyBatch();
		}
	}
}
