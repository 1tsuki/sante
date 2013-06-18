package com.astrider.sfc.app.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Tableアノテーション.
 * 
 * @author astrider
 *         <p>
 *         ORM用アノテーション。DB上のテーブル名を指定する。
 *         </p>
 * 
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
	String value();
}
