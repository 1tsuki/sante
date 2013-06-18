package com.astrider.sfc.app.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Titleアノテーション.
 * 
 * @author astrider<br>
 *         <p>
 *         HTMLにおけるTitle要素を設定するアノテーション。Commandクラスに設定することで、値がページタイトルとして自動挿入される。
 *         </p>
 * 
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Title {
	String value();
}
