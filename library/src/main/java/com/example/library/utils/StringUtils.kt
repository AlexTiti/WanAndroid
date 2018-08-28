package com.example.library.utils

import android.text.TextUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Horrarndoo on 2017/4/5.
 * 字符串工具类
 */
object StringUtils {
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    fun isEmpty(value: String?): Boolean {
        return !(value != null && !"".equals(value.trim { it <= ' ' }, ignoreCase = true)
                && !"null".equals(value.trim { it <= ' ' }, ignoreCase = true))
    }

    /**
     * 判断字符串是否是邮箱
     *
     * @param email email
     * @return 字符串是否是邮箱
     */
    fun isEmail(email: String): Boolean {
        val str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" + "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str)
        val m = p.matcher(email)
        return m.matches()
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        var isValid = false
        val expression = "^1[3|4|5|7|8]\\d{9}$"
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(phoneNumber)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param areaCode    区号
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    fun isPhoneNumberValid(areaCode: String, phoneNumber: String): Boolean {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false
        }

        if (phoneNumber.length < 5) {
            return false
        }

        if (TextUtils.equals(areaCode, "+86") || TextUtils.equals(areaCode, "86")) {
            return isPhoneNumberValid(phoneNumber)
        }

        var isValid = false
        val expression = "^[0-9]*$"
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(phoneNumber)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    /**
     * 判断字符串是否是手机号格式
     *
     * @param areaCode    区号
     * @param phoneNumber 手机号字符串
     * @return 字符串是否是手机号格式
     */
    fun isPhoneFormat(areaCode: String, phoneNumber: String): Boolean {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false
        }

        if (phoneNumber.length < 7) {
            return false
        }

        var isValid = false
        val expression = "^[0-9]*$"
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(phoneNumber)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    /**
     * 判断字符串是否为纯数字
     *
     * @param str 字符串
     * @return 是否纯数字
     */
    fun isNumber(str: String): Boolean {
        for (i in 0 until str.length) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }
}
