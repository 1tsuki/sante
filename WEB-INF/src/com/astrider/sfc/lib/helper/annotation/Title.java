package com.astrider.sfc.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * タイトル自動設定用アノテーション
 * Commandクラスに設定することでページタイトルを自動挿入する
 * @author 01002552
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Title {
    String value();
}
