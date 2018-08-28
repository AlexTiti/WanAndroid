package com.example.library.utils

import java.text.SimpleDateFormat
import java.util.*

object TimestampUtils {
    /**
     * 获取当前的时间戳，时区为北京
     *
     * @return
     */
    //时间戳的格式必须为 yyyy-MM-dd HH:mm:ss
    val currentTimestamp: String?
        get() {
            var timestamp: String? = null
            val format = SimpleDateFormat.getDateTimeInstance()
            timestamp = format.format(Date())
            return timestamp
        }

    /**
     * 获取当前的时间戳，时区为北京
     *
     * @return
     */
    fun getCurrentTime(times: Long): String {
        //时间戳的格式必须为 yyyy-MM-dd HH:mm:ss
        val date = Date(java.lang.Long.valueOf(times))
        val format = SimpleDateFormat.getDateTimeInstance()
        val time = format.format(date)
        SimpleDateFormat.getDateTimeInstance()
                .format(Date())

        return time
    }

    //法国时间：东一区
    fun getDateTimeByGMT(timeZone: Int): String {
        val dff = SimpleDateFormat.getDateTimeInstance()
        when (timeZone) {
            1 -> dff.timeZone = TimeZone.getTimeZone("GMT+1")
            8 -> dff.timeZone = TimeZone.getTimeZone("GMT+8")
        }

        return dff.format(Date())
    }
}
