package com.dengzi.aspectjtest.singleclick;

import android.util.Log;
import android.view.View;

import com.dengzi.aspectjtest.R;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Djk
 * @Title: 防止多次点击
 * @Time: 2017/11/2.
 * @Version:1.0.0
 */
@Aspect
public class SingleClickAspectj {
    static int TIME_TAG = R.id.single_click_check;
    public static final int MIN_CLICK_DELAY_TIME = 600;//间隔时间600ms

    /**
     * 找到要处理的切点,（找到配置SingleClick注解的地方）
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.dengzi.aspectjtest.singleclick.SingleClick * *(..))")
    public void methodAnnotated() {

    }

    /**
     * 这个方法就是处理切点，处理的范围就是上面找到的切点位置
     * 这个方法就是这样写的，可以记住它或copy
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof View) {
                view = (View) arg;
            }
        }
        if (view != null) {
            Object tagTimeObj = view.getTag(TIME_TAG);
            long tagTime = tagTimeObj == null ? 0 : (long) tagTimeObj;
            long currentTime = System.currentTimeMillis();
            if ((currentTime - tagTime) > MIN_CLICK_DELAY_TIME) {
                view.setTag(TIME_TAG, currentTime);
                return joinPoint.proceed();
            } else {
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
