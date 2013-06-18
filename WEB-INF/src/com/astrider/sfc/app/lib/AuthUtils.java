package com.astrider.sfc.app.lib;

import java.util.UUID;

/**
 * 認証Util.
 * 
 * @author astrider
 *         <p>
 *         ユーザー認証用ヘルパークラス。 salt+SHA-512を用いたパスワードの暗号化とストレッチングを行う。
 *         saltはパスワード文字列内の指定した位置にまぶされる。
 *         </p>
 */
public final class AuthUtils {
	private static int saltLength = 10;
	private static int stretchCount = 100;
	private static int[] insertPositions = { 2, 14, 52, 63, 78, 81, 93, 101, 103, 120 };

	/**
	 * 暗号化関数.
	 * 
	 * @param 生パスワード
	 * @return 暗号化されたパスワード
	 *         <p>
	 *         SHA-512を用いたハッシュ化、salt付与及びストレッチングを行う。
	 *         </p>
	 */
	public static String encrypt(String password) {
		return doStretching(password, generateSalt());
	}

	/**
	 * 認証関数.
	 * 
	 * @param 生パスワード
	 * @param 暗号化された文字列
	 * @return パスワード一致
	 *         <p>
	 *         入力されたパスワードと暗号化された文字列が一致するか検証する。
	 *         </p>
	 */
	public static boolean verify(String password, String authToken) {
		String salt = extractSalt(authToken);
		return doStretching(password, salt).equals(authToken);
	}

	private static String generateSalt() {
		return StringUtils.getHash(UUID.randomUUID().toString()).substring(0, saltLength);
	}

	private static String doStretching(String password, String salt) {
		String stretched = password;
		for (int i = 0; i < stretchCount; i++) {
			stretched = doEncrypt(stretched, salt);
		}
		return embedSalt(stretched, salt);
	}

	private static String doEncrypt(String password, String salt) {
		return StringUtils.getHash(salt + password);
	}

	private static String embedSalt(String hash, String salt) {
		StringBuilder builder = new StringBuilder();
		int from = 0;
		int to = 0;
		for (int i = 0; i < saltLength; i++) {
			to = insertPositions[i];
			builder.append(hash.substring(from, to));
			builder.append(salt.charAt(i));
			from = to;
		}
		builder.append(hash.substring(from));
		return builder.toString();
	}

	private static String extractSalt(String hash) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < saltLength; i++) {
			builder.append(hash.charAt(insertPositions[i] + i));
		}
		return builder.toString();
	}
}
