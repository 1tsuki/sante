package com.astrider.sfc.app.test.lib.helper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.app.lib.helper.Mailer;

public class MailerTest {

	@Test
	public void 送信テスト() {
		Mailer mailer = new Mailer("sakitsu@r.recruit.co.jp", "テストメール", "本文");
		assertTrue(mailer.send());
	}
}
