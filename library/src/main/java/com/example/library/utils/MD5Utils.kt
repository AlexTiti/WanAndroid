package com.example.library.utils

import java.security.MessageDigest

/**
 * Created by Horrarndoo on 2017/4/5.
 *
 *
 * MD5加密工具类
 */
object MD5Utils {
    /**
     * MD5加密，32位
     */
    fun getMD5(str: String): String {
        val md5: MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

        val charArray = str.toCharArray()
        val byteArray = ByteArray(charArray.size)
        for (i in charArray.indices) {
            byteArray[i] = charArray[i].toByte()
        }
        val md5Bytes = md5.digest(byteArray)
        val hexValue = StringBuffer()
        for (i in md5Bytes.indices) {
            val `val` = md5Bytes[i].toInt() and 0xff
            if (`val` < 16) {
                hexValue.append("0")
            }
            hexValue.append(Integer.toHexString(`val`))
        }
        return hexValue.toString()
    }
} 