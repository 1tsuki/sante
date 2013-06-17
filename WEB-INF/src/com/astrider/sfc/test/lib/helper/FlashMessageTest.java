package com.astrider.sfc.test.lib.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.astrider.sfc.lib.helper.FlashMessage;
import com.astrider.sfc.lib.helper.FlashMessage.Type;

public class FlashMessageTest {
    @Test
    public void 通常コンストラクタ() {
        FlashMessage flashMessage = new FlashMessage();
        assertFalse(flashMessage.hasMessage());
    }

    @Test
    public void ArrayListを用いたコンストラクタ() {
        String message1 = "ほげもげ";
        String message2 = "あばば";
        ArrayList<String> messages = new ArrayList<String>();
        messages.add(message1);
        messages.add(message2);
        FlashMessage flashMessage = new FlashMessage(messages);
        assertTrue(flashMessage.hasMessage());
        assertTrue(flashMessage.size() == 2);
        assertTrue(flashMessage.getMessages().get(0).equals(message1));
        assertTrue(flashMessage.getMessages().get(1).equals(message2));
    }
    @Test
    public void a文字列メッセージの追加と取得() {
        String message = "ほげもげ";
        FlashMessage flashMessage = new FlashMessage();
        flashMessage.addMessage(message);
        assertTrue(flashMessage.hasMessage());
        assertTrue(flashMessage.size() == 1);
        assertTrue(flashMessage.getMessages().get(0).equals(message));
    }

    @Test
    public void ArrayListメッセージの追加と取得() {
        String message1 = "ほげもげ";
        String message2 = "あばば";
        ArrayList<String> messages = new ArrayList<String>();
        messages.add(message1);
        messages.add(message2);
        FlashMessage flashMessage = new FlashMessage();
        flashMessage.addMessage(messages);
        assertTrue(flashMessage.hasMessage());
        assertTrue(flashMessage.size() == 2);
        assertTrue(flashMessage.getMessages().get(0).equals(message1));
        assertTrue(flashMessage.getMessages().get(1).equals(message2));
    }

    @Test
    public void FlashMessageを渡した時にType情報が引き継がれる() {
        FlashMessage parentMessage = new FlashMessage();
        FlashMessage childMessage = new FlashMessage();

        parentMessage.setMessageType(Type.WARNING);
        childMessage.addMessage(parentMessage);
        assertTrue(childMessage.getMessageType() == Type.WARNING);
    }
}
