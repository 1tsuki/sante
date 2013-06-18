package com.astrider.sfc.app.lib.helper;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @author astrider<br>
 *         メール送信用ヘルパークラス<br>
 * <br>
 *         概要<br>
 *         javaMail利用用ラッパークラス<br>
 *         送信元情報は定数として設定済み。<br>
 *         FROM ... デフォルト送信元アドレス<br>
 *         MAIL_HOST ... 接続先SMTPサーバードメイン、Windows上ではlocalhost上のsmtp4dev<br>
 *         MAIL_PORT ... 接続先SMTPサーバーのポート、windows上ではport25のsmtp4dev<br>
 * <br>
 *         機能<br>
 *         主要機能<br>
 *         ・private class Mail メール内容格納用のVO的なモノ<br>
 *         ・send() 送信実行<br>
 */
public class Mailer {
	/**
	 * @author Itsuki Sakitsu メール内容格納用クラス
	 */
	@SuppressWarnings("unused")
	private class Mail {
		private String from;
		private String to;
		private String subject;
		private String body;

		public Mail(String from, String to, String subject, String body) {
			this.to = to;
			this.subject = subject;
			this.body = body;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}
	}

	private Mail mail;
	private static final String FROM = "noreply@sante.com"; // 標準送信元
	private static final String MAIL_HOST = "localhost"; // 標準SMTP host
	private static final String MAIL_PORT = "25"; // 標準SMTP port

	/**
	 * 送信元を指定しない場合のコンストラクタ
	 */
	public Mailer(String to, String subject, String body) {
		mail = new Mail(FROM, to, subject, body);
	}

	/**
	 * 送信元を指定する場合のコンストラクタ
	 */
	public Mailer(String from, String to, String subject, String body) {
		mail = new Mail(from, to, subject, body);
	}

	/**
	 * メール送信
	 * 
	 * @return メール送信成否
	 */
	public boolean send() {
		boolean succeed = true;
		try {
			Transport.send(createMessage());
		} catch (Exception e) {
			succeed = false;
		}
		return succeed;
	}

	/**
	 * メールオブジェクトを作成
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private MimeMessage createMessage() throws UnsupportedEncodingException,
			MessagingException {
		// 送信先情報
		Properties props = new Properties();
		props.put("mail.smtp.host", MAIL_HOST);
		props.put("mail.smtp.port", MAIL_PORT);
		Session session = Session.getDefaultInstance(props, null);

		// メール本文
		MimeMessage msg = new MimeMessage(session);

		InternetAddress[] toList = new InternetAddress[1];
		toList[0] = new InternetAddress(mail.getTo());

		msg.setRecipients(Message.RecipientType.TO, toList);
		msg.setFrom(new InternetAddress(FROM));
		msg.setSubject(encode(mail.getSubject()));

		MimeMultipart multipart = new MimeMultipart();
		MimeBodyPart bodypart = new MimeBodyPart();
		bodypart.setContent(mail.getBody(),
				"text/plain;charset=\"iso-2022-jp\"");
		multipart.addBodyPart(bodypart);
		msg.setContent(multipart);

		return msg;
	}

	private String encode(String arg) throws UnsupportedEncodingException {
		return MimeUtility.encodeText(arg, "iso-2022-jp", "B");
	}
}
