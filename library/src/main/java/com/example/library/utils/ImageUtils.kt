package com.example.library.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v7.app.AlertDialog

import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

/**
 * 图片工具类
 */
object ImageUtils {

    /**
     * 拍照
     */
    private const val REQUEST_CODE_FROM_CAMERA = 1 shl 10

    /**
     * 相册
     */
    private const val REQUEST_CODE_FROM_ALBUM = 1 shl 12

    /**
     * 裁剪
     */
    private const val REQUEST_CODE_CROP_IMAGE = 1 shl 14

    /**
     * 存放拍照图片的uri地址
     */
     var imageUriFromCamera: Uri? = null

    /**
     * 存放裁剪图片的uri地址
     */
    var cropImageUri: Uri? = null

    /**
     * 显示获取照片不同方式对话框
     */
    @JvmOverloads
    fun showImagePickDialog(activity: Activity, addRequest: Int = 0) {
        val title = "选择获取图片方式"
        val items = arrayOf("拍照", "相册")
        AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items) { dialog, which ->
                    dialog.dismiss()
                    when (which) {
                        0 -> pickImageFromCamera(activity, addRequest)
                        1 -> pickImageFromAlbum(activity, addRequest)
                        else -> {
                        }
                    }
                }
                .setNegativeButton("取消", null)
                .show()
    }

    /**
     * 打开相机拍照获取图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun pickImageFromCamera(activity: Activity, addRequest: Int) {
        // 先生成一个uri地址用于存放拍照获取的图片
        imageUriFromCamera = createImageUri(activity)

        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera)
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA + addRequest)
    }

    /**
     * 打开相机拍照获取图片
     */
    fun pickImageFromCamera(activity: Activity) {
        pickImageFromCamera(activity, 0)
    }

    /**
     * 打开本地相册选取图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun pickImageFromAlbum(activity: Activity, addRequest: Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM + addRequest)
    }

    /**
     * 打开本地相册选取图片
     */
    fun pickImageFromAlbum(activity: Activity) {
        pickImageFromAlbum(activity, 0)
    }

    /**
     * 图片裁剪
     */
    fun cropImage(activity: Activity, srcUri: Uri) {
        cropImageUri = createImageUri(activity)

        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(srcUri, "image/*")
        intent.putExtra("crop", "true")

        ////////////////////////////////////////////////////////////////
        // 1.宽高和比例都不设置时,裁剪框可以自行调整(比例和大小都可以随意调整)
        ////////////////////////////////////////////////////////////////
        // 2.只设置裁剪框宽高比(aspect)后,裁剪框比例固定不可调整,只能调整大小
        /////////////////////////////////
        // 3.裁剪后生成图片宽高(output)的设置和裁剪框无关,只决定最终生成图片大小
        ////////////////////////////////////////////////////////////////
        // 4.裁剪框宽高比例(aspect)可以和裁剪后生成图片比例(output)不同,此时,
        //	会以裁剪框的宽为准,按照裁剪宽高比例生成一个图片,该图和框选部分可能不同,
        //  不同的情况可能是截取框选的一部分,也可能超出框选部分,向下延伸补足
        ////////////////////////////////////////////////////////////////

        // aspectX aspectY 是裁剪框宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX outputY 是裁剪后生成图片的宽高
        //		intent.putExtra("outputX", 300);
        //		intent.putExtra("outputY", 100);

        // return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现OOM,推荐下面为false时的方式
        // return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri)

        activity.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE)
    }

    /**
     * 创建一条图片uri,用于保存拍照后的照片
     */
    private fun createImageUri(context: Context): Uri? {
        val name = "boreImg" + System.currentTimeMillis()
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, name)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "$name.jpeg")
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        return context.contentResolver.insert(MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI, values)
    }

    /**
     * 删除一条图片
     */
    fun deleteImageUri(context: Context, uri: Uri) {
        context.contentResolver.delete(uri, null, null)
    }

    /**
     * 用第三方应用app打开图片
     */
    fun openImageByOtherApp(context: Context, imageUri: Uri) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(imageUri, "image/*")
        context.startActivity(intent)
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     */
    fun getImageAbsolutePath19(context: Context?, imageUri: Uri?): String? {
        if (context == null || imageUri == null)
            return null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                val docId = DocumentsContract.getDocumentId(imageUri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(imageUri)) {
                val id = DocumentsContract.getDocumentId(imageUri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(imageUri)) {
                val docId = DocumentsContract.getDocumentId(imageUri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = MediaStore.Images.Media._ID + "=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        }

        // MediaStore (and general)
        if ("content".equals(imageUri.scheme, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(imageUri)) imageUri.lastPathSegment else getDataColumn(context, imageUri, null, null)
        } else if ("file".equals(imageUri.scheme, ignoreCase = true)) {
            return imageUri.path
        }// File
        return null
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = MediaStore.Images.Media.DATA
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection,
                    selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

}

