package com.astrider.sfc.app.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author astrider<br>
 *         タイトル自動設定用アノテーション<br>
 *         Commandクラスに設定することでページタイトルを自動挿入する
 * 
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Title {
	String value();
}
