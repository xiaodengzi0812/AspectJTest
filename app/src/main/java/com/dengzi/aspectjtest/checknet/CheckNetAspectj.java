package com.dengzi.aspectjtest.checknet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Djk
 * @Title: 检测网络处理切点
 * @Time: 2017/11/2.
 * @Version:1.0.0
 */
@Aspect
public class CheckNetAspectj {

    /**
     * 找到要处理的切点,（找到配置CheckNet注解的地方）
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.dengzi.aspectjtest.checknet.CheckNet * *(..))")
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
        // 获取一个Method签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 从Method签名中获取注解类
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            // 2.判断有没有网络  怎么样获取 context?
            Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
            Context context = getContext(object);
            if (context != null) {
                if (!isNetworkAvailable(context)) {
                    // 3.没有网络不要往下执行
                    Toast.makeText(context, "请检查您的网络", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     *
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        // 下面的方法是通过对象获取上下文，
        // 这样获取有一个问题就是在工具类中无法使用注解类去判断网络状态
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;

        // 如果是我们自己做开发，我们可以获取application的上下文，这样就能兼容所有工具类，
        // 如果在使用中获取不到application的上下文也可以把上面的方法放开来代替，只不过在工具类就无法使用了
//        return MyApp.getInstance().getApplicationContext();
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
