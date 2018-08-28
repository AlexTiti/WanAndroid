package com.example.library.utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import com.example.library.R
import com.example.library.helper.RxHelper
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.io.*
import kotlin.experimental.and

/**
 *
 *
 * 读取文件工具类
 */
object FileUtils {
    /**
     * Convert byte[] to hex string.将byte转换成int，
     * 然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    fun bytesToHexString(src: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (src == null || src.size <= 0) {
            return null
        }
        for (i in src.indices) {
            val v = src[i] and 0xFF.toByte()
            val hv = Integer.toHexString(v.toInt())
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }

    /**
     * 根据文件名称和路径，获取sd卡中的文件，以File形式返回byte
     */
    fun getFile(fileName: String, folder: String): File? {
        val state = Environment.getExternalStorageState()
        if (state == Environment.MEDIA_MOUNTED) {
            val pathFile = File(Environment.getExternalStorageDirectory().toString() + folder)
            // && !pathFile .isDirectory()
            if (!pathFile.exists()) {
                pathFile.mkdirs()
            }
            return File(pathFile, fileName)
        }
        return null
    }

    /**
     * 根据文件名称和路径，获取sd卡中的文件，判断文件是否存在，存在返回true
     */
    fun checkFile(fileName: String, folder: String): Boolean {

        val targetFile = getFile(fileName, folder)

        return targetFile!!.exists()
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    fun getRealFilePathFromUri(context: Context, uri: Uri?): String? {
        if (null == uri) {
            return null
        }
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE.equals(scheme, ignoreCase = true)) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme, ignoreCase = true)) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore
                    .Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    /**
     * 检查文件是否存在
     */
    fun checkDirPath(dirPath: String): String {
        if (TextUtils.isEmpty(dirPath)) {
            return ""
        }
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dirPath
    }

    fun copyFile(sourcefile: File, targetFile: File) {
        var input: FileInputStream? = null
        var inbuff: BufferedInputStream? = null
        var out: FileOutputStream? = null
        var outbuff: BufferedOutputStream? = null

        try {

            input = FileInputStream(sourcefile)
            inbuff = BufferedInputStream(input)

            out = FileOutputStream(targetFile)
            outbuff = BufferedOutputStream(out)

            val b = ByteArray(1024 * 5)
            var len: Int
            len = inbuff.read(b)
            while (len != -1) {
                outbuff.write(b, 0, len)
                len = inbuff.read(b)
            }
            outbuff.flush()
        } catch (ex: Exception) {
        } finally {
            try {
                inbuff?.close()
                outbuff?.close()
                out?.close()
                input?.close()
            } catch (ex: Exception) {

            }

        }
    }

    /**
     * 保存图片到本机
     *
     * @param context            context
     * @param fileName           文件名
     * @param file               file
     * @param saveResultCallback 保存结果callback
     */
    fun saveImage(context: Context, fileName: String, file: File,
                  saveResultCallback: SaveResultCallback) {
        val disposable = Observable.create(ObservableOnSubscribe<File> { e ->
            val appDir = File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name))
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            var saveFileName: String
            if (fileName.contains(".png") || fileName.contains(".gif")) {
                val fileFormat = fileName.substring(fileName.lastIndexOf("."))
                saveFileName = MD5Utils.getMD5(fileName) + fileFormat
            } else {
                saveFileName = MD5Utils.getMD5(fileName) + ".png"
            }
            saveFileName = saveFileName.substring(20)
            val saveFile = File(appDir, saveFileName)
            try {
                val `is` = FileInputStream(file)
                val fos = FileOutputStream(saveFile)
                val buffer = ByteArray(1024 * 1024)
                var count: Int
                count = `is`.read(buffer)
                while (count > 0) {
                    fos.write(buffer, 0, count)
                    count = `is`.read(buffer)
                }
                fos.close()
                `is`.close()
                e.onNext(saveFile)
            } catch (exception: FileNotFoundException) {
                e.onError(exception)

            } catch (exception: IOException) {
                e.onError(exception)
            }
        }).compose(RxHelper.rxSchedulerHelper())
                .subscribe({ saveFile ->
                    saveResultCallback.onSavedSuccess()
                    val uri = Uri.fromFile(saveFile)
                    context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                }, { throwable ->
                    if (throwable is FileNotFoundException) {
                        saveResultCallback.onSavedFailed()
                        throwable.printStackTrace()
                    } else if (throwable is IOException) {
                        saveResultCallback.onSavedFailed()
                        throwable.printStackTrace()
                    }
                })
    }

    /**
     * 保存Bitmap到本机
     *
     * @param context            context
     * @param fileName           bitmap文件名
     * @param bmp                bitmap
     * @param saveResultCallback 保存结果callback
     */
    fun saveBitmap(context: Context, fileName: String, bmp: Bitmap,
                   saveResultCallback: SaveResultCallback) {
        val disposable = Observable.create(ObservableOnSubscribe<File> { e ->
            val appDir = File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name))
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            // 设置以当前时间格式为图片名称
            var saveFileName = MD5Utils.getMD5(fileName) + ".png"
            saveFileName = saveFileName.substring(20)
            val file = File(appDir, saveFileName)
            try {
                val fos = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
                e.onNext(file)
            } catch (exception: FileNotFoundException) {
                e.onError(exception)
            } catch (exception: IOException) {
                e.onError(exception)
            }
        }).compose(RxHelper.rxSchedulerHelper())
                .subscribe({ saveFile ->
                    saveResultCallback.onSavedSuccess()
                    val uri = Uri.fromFile(saveFile)
                    context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                }, { throwable ->
                    if (throwable is FileNotFoundException) {
                        saveResultCallback.onSavedFailed()
                        throwable.printStackTrace()
                    } else if (throwable is IOException) {
                        saveResultCallback.onSavedFailed()
                        throwable.printStackTrace()
                    }
                })

    }

    interface SaveResultCallback {
        /**
         * 保存成功
         */
        fun onSavedSuccess()

        /**
         * 保存失败
         */
        fun onSavedFailed()
    }
}


