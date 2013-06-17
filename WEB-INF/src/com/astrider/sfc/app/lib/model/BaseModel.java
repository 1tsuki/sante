package com.astrider.sfc.app.lib.model;

import com.astrider.sfc.app.lib.helper.FlashMessage;

public class BaseModel {
    protected FlashMessage flashMessage = new FlashMessage();

    public FlashMessage getFlashMessage() {
        return flashMessage;
    }
}
