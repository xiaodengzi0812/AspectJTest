package com.dengzi.aspectjtest.checknet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Djk
 * @Title: 检查网络注解
 * @Time: 2017/11/2.
 * @Version:1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {

}
