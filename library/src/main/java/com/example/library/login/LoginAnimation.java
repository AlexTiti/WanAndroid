package com.example.library.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/03
 * 在任何需要判断登陆状态方法上添加 @LoginAnimation 注解，如：
 * ================================
 *  @LoginAnimation
 * public void click(){
 * ...
 * }
 * ==================================
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginAnimation {
    int value() default 0;
}
