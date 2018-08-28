package com.example.library.utils

import android.os.Build
import android.webkit.WebSettings
import java.util.*
import java.util.regex.Pattern

/**
 *
 *
 * HttpUtils 主要用于获取UserAgent
 */

object HttpUtils {
    /**
     * 获取UserAgent
     *
     * @return UserAgent
     */
    val userAgent: String
        get() {
            var userAgent: String
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    userAgent = WebSettings.getDefaultUserAgent(AppUtils.context)
                } catch (e: Exception) {
                    userAgent = System.getProperty("http.agent")
                }

            } else {
                userAgent = System.getProperty("http.agent")
            }
            val sb = StringBuffer()
            var i = 0
            val length = userAgent.length
            while (i < length) {
                val c = userAgent[i]
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", c.toInt()))
                } else {
                    sb.append(c)
                }
                i++
            }
            return sb.toString()
        }

    fun makeUA(): String {
        return Build.BRAND + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE
    }

    fun returnImageUrlsFromHtml(html: String): Array<String>? {
        val imageSrcList = ArrayList<String>()
        val p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\rf>]+(\\" +
                ".jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\" +
                ".jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic|\\b)\\b)[^>]*>", Pattern
                .CASE_INSENSITIVE)
        val m = p.matcher(html)
        var quote: String
        var src: String
        while (m.find()) {
            quote = m.group(1)
            src = if (quote == null || quote.trim { it <= ' ' }.isEmpty())
                m.group(2).split("//s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            else
                m
                        .group(2)
            imageSrcList.add(src)
        }
        return if (imageSrcList.size == 0) {
            null
        } else imageSrcList.toTypedArray()
    }
}
