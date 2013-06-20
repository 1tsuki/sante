package com.astrider.sfc.app.lib.util;

import java.util.UUID;

import static com.astrider.sfc.ApplicationContext.*;

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
		return StringUtils.getHash(UUID.randomUUID().toString()).substring(0, SALT_LENGTH);
	}

	private static String doStretching(String password, String salt) {
		String stretched = password;
		for (int i = 0; i < STRETCH_COUNT; i++) {
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
		for (int i = 0; i < SALT_LENGTH; i++) {
			to = SALT_INSERT_POSITIONS[i];
			builder.append(hash.substring(from, to));
			builder.append(salt.charAt(i));
			from = to;
		}
		builder.append(hash.substring(from));
		return builder.toString();
	}

	private static String extractSalt(String hash) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < SALT_LENGTH; i++) {
			builder.append(hash.charAt(SALT_INSERT_POSITIONS[i] + i));
		}
		return builder.toString();
	}
}
