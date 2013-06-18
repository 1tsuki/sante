package com.astrider.sfc.app.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Columnアノテーション.
 * 
 * @author astrider
 *         <p>
 *         DBのカラム名、及びformのname要素を指定するアノテーション。 Validatorにて利用する。
 *         physicは物理名、logicはエラー出力等に利用する論理名を記述する。<br>
 *         </p>
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Column {
	boolean isPrimaryKey() default false;

	String physic();

	String logic();
}
