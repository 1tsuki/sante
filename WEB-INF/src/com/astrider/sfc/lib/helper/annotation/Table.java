package com.astrider.sfc.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * ORM用アノテーション。DB上のテーブル名を指定する
 * @author 01002552
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
    String value();
}
