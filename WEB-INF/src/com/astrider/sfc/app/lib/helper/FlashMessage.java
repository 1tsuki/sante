package com.astrider.sfc.app.lib.helper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * フラッシュメッセージ.
 * 
 * @author astrider<br>
 *         <p>
 *         FW内で共通利用する、ユーザー通知メッセージ用クラス。
 *         </p>
 */
public class FlashMessage implements Serializable {
	private static final long serialVersionUID = -8229498358235532373L;

	public enum Type {
		INFO, WARNING;
	}

	private ArrayList<String> flashMessages = new ArrayList<String>();
	private Type messageType = Type.INFO;

	public FlashMessage() {
	}

	public FlashMessage(ArrayList<String> flashMessages) {
		this.flashMessages = flashMessages;
	}

	public void addMessage(String message) {
		flashMessages.add(message);
	}

	public void addMessage(ArrayList<String> messages) {
		for (String message : messages) {
			addMessage(message);
		}
	}

	public void addMessage(FlashMessage addingObj) {
		setMessageType(addingObj.getMessageType());
		ArrayList<String> messages = addingObj.getMessages();
		addMessage(messages);
	}

	public ArrayList<String> getMessages() {
		return flashMessages;
	}

	public boolean hasMessage() {
		return 0 < flashMessages.size();
	}

	public int size() {
		return flashMessages.size();
	}

	public void setMessageType(Type t) {
		messageType = t;
	}

	public Type getMessageType() {
		return messageType;
	}
}
