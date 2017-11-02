package com.dengzi.aspectjtest.singleclick;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Djk
 * @Title: 防止多次点击
 * @Time: 2017/11/2.
 * @Version:1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleClick {
}
