package com.example.library.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

import com.example.library.R

/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * 对话框工具类, 提供常用对话框显示, 使用support.v7包内的AlertDialog样式
 */
object DialogUtils {

    fun showCommonDialog(context: Context, message: String, listener: DialogInterface.OnClickListener): Dialog {
        return showCommonDialog(context, message, context.getString(R.string.dialog_positive),
                context.getString(R.string.dialog_negative), listener)
    }

    fun showCommonDialog(context: Context, message: String, positiveText: String,
                         negativeText: String, listener: DialogInterface.OnClickListener): Dialog {
        return AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .setNegativeButton(negativeText, null)
                .show()
    }

    fun showConfirmDialog(context: Context, message: String,
                          listener: DialogInterface.OnClickListener): Dialog {
        return showConfirmDialog(context, message, context.getString(R.string.dialog_positive),
                listener)
    }

    fun showConfirmDialog(context: Context, message: String, positiveText: String,
                          listener: DialogInterface.OnClickListener): Dialog {
        return AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .show()
    }
}
