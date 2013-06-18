package com.astrider.sfc.src.command.api.user;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.src.model.ApiModel;

public class GetNutrientsCommand extends Command {
	@Override
	public void doGet() throws ServletException, IOException {
		ApiModel apiModel = new ApiModel();
		apiModel.getNutrients(request);
		render();
	}
}
