package com.astrider.sfc.app.command.api.user;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.model.ApiModel;
import com.astrider.sfc.lib.Command;

public class GetStatsCommand extends Command {
    @Override
    public void doGet() throws ServletException, IOException {
        ApiModel apiModel = new ApiModel();
        apiModel.getStats(request);
        render();
    }
}
