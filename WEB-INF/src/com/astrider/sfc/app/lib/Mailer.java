package com.astrider.sfc.app.lib;

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
 * メール送信用ヘルパークラス.
 * 
 * @author astrider<br>
 *         <p>
 *         javaMail利用用ラッパークラス。 送信元情報は定数として設定済み。
 *         </p>
 */
public class Mailer {
	/**
	 * メールBean.
	 * 
	 * @author astrider
	 *         <p>
	 *         メール内容を格納するBean
	 *         </p>
	 */
	@SuppressWarnings("unused")
	private class Mail {
		private String from;
		private String to;
		private String subject;
		private String body;

		/**
		 * コンストラクタ.
		 * 
		 * @param 送信元アドレス
		 * @param 送信先アドレス
		 * @param タイトル
		 * @param 本文
		 */
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
	private static final String FROM = "noreply@sante.com";
	private static final String MAIL_HOST = "localhost";
	private static final String MAIL_PORT = "25";

	/**
	 * 標準送信元アドレスを利用するコンストラクタ.
	 * 
	 * @param 送信先アドレス
	 * @param タイトル
	 * @param 本文
	 */
	public Mailer(String to, String subject, String body) {
		mail = new Mail(FROM, to, subject, body);
	}

	/**
	 * 送信元アドレスを指定するコンストラクタ.
	 * 
	 * @param 送信元アドレス
	 * @param 送信先アドレス
	 * @param タイトル
	 * @param 本文
	 */
	public Mailer(String from, String to, String subject, String body) {
		mail = new Mail(from, to, subject, body);
	}

	/**
	 * メール送信.
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
	 * メールオブジェクトを作成.
	 * 
	 * @return MimeMessage
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private MimeMessage createMessage() throws UnsupportedEncodingException, MessagingException {
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
		bodypart.setContent(mail.getBody(), "text/plain;charset=\"iso-2022-jp\"");
		multipart.addBodyPart(bodypart);
		msg.setContent(multipart);

		return msg;
	}

	private String encode(String arg) throws UnsupportedEncodingException {
		return MimeUtility.encodeText(arg, "iso-2022-jp", "B");
	}
}
