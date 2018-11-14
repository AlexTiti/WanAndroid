package com.example.library.login;

import com.example.administrator.login.aop.login.ILoginView;
import com.example.administrator.login.aop.login.LoginAssistant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/03
 */
@Aspect
public class LoginAspect {

    /**
     * 添加切入点：只要被LoginAnimation标注的方法都是拦截的切入点
     */
    @Pointcut("execution(@com.example.administrator.login.aop.login.LoginAnimation * *(..))")
    public void loginAnimation() {
    }

    /**
     * 添加Around Advice
     */
    @Around("loginAnimation()")
    public void aroundLoginPoint(ProceedingJoinPoint jointPoint) {
        // 初始化ILoginView
        ILoginView loginView = LoginAssistant.Companion.getInstance().getView();
        if (loginView == null) {
            try {
                throw new Exception("必须初始化 ILoginView");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MethodSignature signature = (MethodSignature) jointPoint.getSignature();
        // 获取方法的注解是否为LoginAnimation
        LoginAnimation animation = signature.getMethod().getAnnotation(LoginAnimation.class);
        if (animation == null) {
            return;
        }

        if ("exitLogin".equals(signature.getMethod().getName())) {
            loginView.exitLogin();
            try {
                jointPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return;
        }

        if (loginView.isLogin()) {
            try {
                jointPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            loginView.login(animation.value());
        }
    }
}
