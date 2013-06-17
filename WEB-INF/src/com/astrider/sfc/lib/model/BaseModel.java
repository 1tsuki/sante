package com.astrider.sfc.lib.model;

import com.astrider.sfc.lib.helper.FlashMessage;

public class BaseModel {
    protected FlashMessage flashMessage = new FlashMessage();

    public FlashMessage getFlashMessage() {
        return flashMessage;
    }
}
