package com.astrider.sfc.app.lib;


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
