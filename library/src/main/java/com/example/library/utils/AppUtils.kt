package com.example.library.utils

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.library.global.GlobalApplication
import java.io.File

/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * App工具类
 */
object AppUtils {

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
  val context: Context
        get() = GlobalApplication.getContext()

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    private val handler: Handler
        get() = GlobalApplication.getHandler()

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    private val mainThreadId: Int
        get() = GlobalApplication.getMainThreadId()

    /**
     * 获取SD卡路径
     *
     * @return 如果sd卡不存在则返回null
     */
    private//判断sd卡是否存在
    val sdPath: File?
        get() {
            var sdDir: File? = null
            val sdCardExist = Environment.getExternalStorageState() == Environment
                    .MEDIA_MOUNTED
            if (sdCardExist) {
                sdDir = Environment.getExternalStorageDirectory()
            }
            return sdDir
        }

    /**
     * 判断是否运行在主线程
     *
     * @return true：当前线程运行在主线程
     * fasle：当前线程没有运行在主线程
     */
    private// 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
    val isRunOnUIThread: Boolean
        get() {
            val myTid = android.os.Process.myTid()
            return if (myTid == mainThreadId) {
                true
            } else false
        }

    /**
     * 获取版本名称
     */
    private fun getAppVersionName(context: Context): String? {
        var versionName: String? = null
        try {
            // ---get the package info---
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
            if (versionName == null || versionName.length <= 0) {
                return ""
            }
        } catch (e: Exception) {
            Log.e("VersionInfo", "Exception", e)
        }

        return versionName
    }

    /**
     * 获取版本号
     */
    private fun getAppVersionCode(context: Context): Int {
        var versioncode = -1
        try {
            // ---get the package info---
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versioncode = pi.versionCode
        } catch (e: Exception) {
            Log.e("VersionInfo", "Exception", e)
        }

        return versioncode
    }

    private fun getIMEI(context: Context): String? {
        val tm = context.getSystemService(Context
                .TELEPHONY_SERVICE) as TelephonyManager
        return if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            tm.deviceId
        } else null
    }

    /**
     * 显示软键盘
     */
    private fun openSoftInput(et: EditText) {
        val inputMethodManager = et.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftInput(et: EditText) {
        val inputMethodManager = et.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(et.windowToken, InputMethodManager
                .HIDE_NOT_ALWAYS)
    }

    /**
     * 安装文件
     *
     * @param data
     */
    private fun promptInstall(context: Context, data: Uri) {
        val promptInstall = Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive")
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(promptInstall)
    }

    fun copy2clipboard(context: Context, text: String) {
        val cm = context.getSystemService(Context
                .CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("clip", text)
        cm.primaryClip = clip
    }

    /**
     * 运行在主线程
     *
     * @param r 运行的Runnable对象
     */
    private fun runOnUIThread(r: Runnable) {
        if (isRunOnUIThread) {
            // 已经是主线程, 直接运行
            r.run()
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            handler.post(r)
        }
    }
}
