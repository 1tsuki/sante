package com.astrider.sfc.app.lib.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 文字列Util.
 * 
 * @author astrider
 */
public final class StringUtils {
	/**
	 * isNotNull and isNotEmpty.
	 * 
	 * @param 文字列
	 * @return boolean
	 */
	public static boolean isNotEmpty(String arg) {
		return arg != null && !arg.isEmpty();
	}

	/**
	 * isNotNull but isEmpty.
	 * 
	 * @param 文字列
	 * @return boolean
	 */
	public static boolean isEmpty(String arg) {
		return arg != null && arg.isEmpty();
	}

	/**
	 * 先頭一文字を大文字化.
	 * 
	 * @param 文字列
	 * @return 変換された文字列
	 */
	public static String toCamelCase(String arg) {
		return arg.substring(0, 1).toUpperCase() + arg.substring(1);
	}

	/**
	 * UUID文字列を取得.
	 * 
	 * @param 文字列
	 * @return
	 */
	public static String getUniqueString() {
		return UUID.randomUUID().toString();
	}

	/**
	 * メールアドレス認証用トークンを生成.
	 * 
	 * @param email
	 * @return メールアドレス認証用トークン(16文字)
	 */
	public static String getEmailToken(String email) {
		String token = getHash(email);
		return token.substring(0, 16);
	}

	/**
	 * SHA-512を用いたハッシュ化.
	 * 
	 * @param 文字列
	 * @return ハッシュ化済み文字列
	 */
	public static String getHash(String arg) {
		StringBuilder builder = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.reset();
			md.update(arg.getBytes());
			byte[] digest = md.digest();

			for (int i = 0; i < digest.length; i++) {
				int d = digest[i] & 0xff;
				String hex = Integer.toHexString(d);
				if (hex.length() == 1) {
					builder.append("0");
				}
				builder.append(hex);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}
}
