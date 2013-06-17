package com.astrider.sfc.app.lib.helper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 概要<br>
 *  システム内のエラーやり取り用クラス<br>
 *<br>
 * 機能<br>
 *  主要機能<br>
 *      ・enum Type          メッセージ種別。WARNING, INFO等<br>
 *      ・addMessage()       メッセージ内容の登録<br>
 *      ・getMessages()      メッセージ一覧の取得<br>
 *      ・setMessageType（）       メッセージ種別を設定<br>
 *      ・getMessageType()   メッセージ種別を取得<br>
 *<br>
 *  副次機能<br>
 *      ・hasMessage()       メッセージ有無を取得<br>
 *      ・size()             メッセージ数を取得<br>
 * @author Itsuki Sakitsu
 *
 */
public class FlashMessage implements Serializable {
    private static final long serialVersionUID = -8229498358235532373L;

    /**
     * @author Itsuki Sakitsu
     *
     */
    public enum Type { INFO, WARNING; }
    private ArrayList<String> flashMessages = new ArrayList<String>();
    private Type messageType = Type.INFO;

    public FlashMessage() {
    }

    /**
     * @param flashMessages
     */
    public FlashMessage(ArrayList<String> flashMessages) {
        this.flashMessages = flashMessages;
    }

    /**
     * @param message
     */
    public void addMessage(String message) {
        flashMessages.add(message);
    }

    /**
     * @param messages
     */
    public void addMessage(ArrayList<String> messages) {
        for (String message: messages) {
            addMessage(message);
        }
    }

    /**
     * @param addingObj
     */
    public void addMessage(FlashMessage addingObj) {
        setMessageType(addingObj.getMessageType());
        ArrayList<String> messages = addingObj.getMessages();
        addMessage(messages);
    }

    /**
     * @return
     */
    public ArrayList<String> getMessages() {
        return flashMessages;
    }

    /**
     * @return
     */
    public boolean hasMessage() {
        return 0 < flashMessages.size();
    }

    /**
     * @return
     */
    public int size() {
        return flashMessages.size();
    }

    /**
     * @param t
     */
    public void setMessageType(Type t) {
        messageType = t;
    }

    /**
     * @return
     */
    public Type getMessageType() {
        return messageType;
    }
}
