package com.astrider.sfc.app.model;

import com.astrider.sfc.app.lib.FlashMessage;

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
