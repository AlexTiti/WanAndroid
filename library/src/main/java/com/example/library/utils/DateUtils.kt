package com.example.library.utils

import android.text.TextUtils
import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * 日期时间工具类
 */
object DateUtils {

    private const val ONE_SECOND_MILLIONS: Long = 1000
    private const val ONE_MINUTE_MILLIONS = 60 * ONE_SECOND_MILLIONS
    private const val ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS
    private const val ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS
    private const val DAY_OF_YEAR = 365

    /**
     * 日期格式为 2016-02-03 17:04:58
     */
    private const val PATTERN_DATE = "yyyy年MM月dd日"
    private const val PATTERN_TIME = "HH:mm:ss"
    private const val PATTERN_SPLIT = " "
    private const val PATTERN = PATTERN_DATE + PATTERN_SPLIT + PATTERN_TIME

    fun getShortTime(dateStr: String): String {
        val str: String

        val date = str2date(dateStr)
        val curDate = Date()

        val durTime = curDate.time - date!!.time
        val dayDiff = calculateDayDiff(date, curDate)

        return if (durTime <= 10 * ONE_MINUTE_MILLIONS) {
            "刚刚"
        } else if (durTime < ONE_HOUR_MILLIONS) {
            (durTime / ONE_MINUTE_MILLIONS).toString() + "分钟前"
        } else if (dayDiff == 0) {
            (durTime / ONE_HOUR_MILLIONS).toString() + "小时前"
        } else if (dayDiff == -1) {
            "昨天" + DateFormat.format("HH:mm", date)
        } else if (isSameYear(date, curDate) && dayDiff < -1) {
            DateFormat.format("MM-dd", date).toString()
        } else {
            DateFormat.format("yyyy-MM", date).toString()
        }

    }


    /**
     * 获取日期 PATTERN_DATE 部分
     */
    fun getDate(date: String): String {
        return if (TextUtils.isEmpty(date) || !date.contains(PATTERN_SPLIT)) {
            ""
        } else date.split(PATTERN_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }

    /**
     * 原有日期上累加月
     *
     * @return 累加后的日期 PATTERN_DATE 部分
     */
    fun addMonth(date: String, moonCount: Int): String {
        var date = date
        if (TextUtils.isEmpty(date)) {

//            val df = SimpleDateFormat(PATTERN_DATE + PATTERN_SPLIT + PATTERN_TIME)
            val df = SimpleDateFormat.getDateTimeInstance()
            date = df.format(Date())
        }
        val calendar = str2calendar(date)
        calendar!!.add(Calendar.MONTH, moonCount)
        return getDate(calendar2str(calendar))
    }

    /**
     * 计算天数差
     */
    fun calculateDayDiff(targetTime: Date, compareTime: Date): Int {
        val sameYear = isSameYear(targetTime, compareTime)
        if (sameYear) {
            return calculateDayDiffOfSameYear(targetTime, compareTime)
        } else {
            var dayDiff = 0

            // 累计年数差的整年天数
            val yearDiff = calculateYearDiff(targetTime, compareTime)
            dayDiff += yearDiff * DAY_OF_YEAR

            // 累计同一年内的天数
            dayDiff += calculateDayDiffOfSameYear(targetTime, compareTime)

            return dayDiff
        }
    }

    /**
     * 计算同一年内的天数差
     */
    fun calculateDayDiffOfSameYear(targetTime: Date?, compareTime: Date?): Int {
        if (targetTime == null || compareTime == null) {
            return 0
        }

        val tarCalendar = Calendar.getInstance()
        tarCalendar.time = targetTime
        val tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR)

        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = compareTime
        val comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR)

        return tarDayOfYear - comDayOfYear
    }

    /**
     * 计算年数差
     */
    private fun calculateYearDiff(targetTime: Date?, compareTime: Date?): Int {
        if (targetTime == null || compareTime == null) {
            return 0
        }

        val tarCalendar = Calendar.getInstance()
        tarCalendar.time = targetTime
        val tarYear = tarCalendar.get(Calendar.YEAR)

        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = compareTime
        val comYear = compareCalendar.get(Calendar.YEAR)

        return tarYear - comYear
    }

    /**
     * 计算月数差
     *
     * @param targetTime
     * @param compareTime
     * @return
     */
    fun calculateMonthDiff(targetTime: String, compareTime: String): Int {
        return calculateMonthDiff(str2date(targetTime, PATTERN_DATE),
                str2date(compareTime, PATTERN_DATE))
    }

    /**
     * 计算月数差
     *
     * @param targetTime
     * @param compareTime
     * @return
     */
    fun calculateMonthDiff(targetTime: Date?, compareTime: Date?): Int {
        val tarCalendar = Calendar.getInstance()
        tarCalendar.time = targetTime
        val tarYear = tarCalendar.get(Calendar.YEAR)
        val tarMonth = tarCalendar.get(Calendar.MONTH)

        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = compareTime
        val comYear = compareCalendar.get(Calendar.YEAR)
        val comMonth = compareCalendar.get(Calendar.MONTH)
        return (tarYear - comYear) * 12 + tarMonth - comMonth

    }

    /**
     * 是否为同一年
     */
    private fun isSameYear(targetTime: Date?, compareTime: Date?): Boolean {
        if (targetTime == null || compareTime == null) {
            return false
        }

        val tarCalendar = Calendar.getInstance()
        tarCalendar.time = targetTime
        val tarYear = tarCalendar.get(Calendar.YEAR)

        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = compareTime
        val comYear = compareCalendar.get(Calendar.YEAR)

        return tarYear == comYear
    }

    @JvmOverloads
    private fun str2date(str: String?, format: String = PATTERN): Date? {
        var date: Date? = null
        try {
            if (str != null) {
                val sdf = SimpleDateFormat(format)
                date = sdf.parse(str)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    @JvmOverloads
    private fun date2str(date: Date, format: String = PATTERN): String {
        val sdf = SimpleDateFormat(format, Locale.CHINA)
        return sdf.format(date)
    }

    private fun str2calendar(str: String): Calendar? {
        var calendar: Calendar? = null
        val date = str2date(str)
        if (date != null) {
            calendar = Calendar.getInstance()
            calendar!!.time = date
        }
        return calendar
    }


    fun str2calendar(str: String, format: String): Calendar? {
        var calendar: Calendar? = null
        val date = str2date(str, format)
        if (date != null) {
            calendar = Calendar.getInstance()
            calendar!!.time = date
        }
        return calendar
    }

    private fun calendar2str(calendar: Calendar): String {
        return date2str(calendar.time)
    }

    fun calendar2str(calendar: Calendar, format: String): String {
        return date2str(calendar.time, format)
    }
}
