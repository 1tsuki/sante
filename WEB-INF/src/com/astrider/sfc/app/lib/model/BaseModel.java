package com.astrider.sfc.app.lib.model;

import com.astrider.sfc.app.lib.helper.FlashMessage;

/**
 * Model基底クラス
 * 
 * @author astrider
 *         <p>
 *         FlashMessageを持つ
 *         </p>
 */
public class BaseModel {
	protected FlashMessage flashMessage = new FlashMessage();

	public FlashMessage getFlashMessage() {
		return flashMessage;
	}
}
