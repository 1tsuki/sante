package com.astrider.sfc.app.lib.model;

import com.astrider.sfc.app.lib.helper.FlashMessage;

/**
 * @author astrider<br>
 *         各種Modelの基底クラス。flashMessageを持つ
 */
public class BaseModel {
	protected FlashMessage flashMessage = new FlashMessage();

	public FlashMessage getFlashMessage() {
		return flashMessage;
	}
}
