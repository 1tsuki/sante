package com.astrider.sfc.app.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * @author astrider<br>
 *         ORM用アノテーション。DB上のテーブル名を指定する
 * 
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
	String value();
}
