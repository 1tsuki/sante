package com.astrider.sfc.test.app.lib;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.app.lib.Mailer;

public class MailerTest {

	@Test
	public void 送信テスト() {
		Mailer mailer = new Mailer("sakitsu@r.recruit.co.jp", "テストメール", "本文");
		assertTrue(mailer.send());
	}
}
