package com.example.administrator.wanandroid

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment


/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/10/09
 */

@SuppressLint("ValidFragment")
class UserDialogFragment(
        internal var message: String,
        internal var positiveButtonString: Int,
        internal var negativeButtonString: Int,
        private var dialogButtonClick: DialogButtonClickListener?
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity)

        return alertDialogBuilder.setMessage(message)
                .setTitle(R.string.notice)
                .setPositiveButton(positiveButtonString) { dialog, _ -> dialogButtonClick?.positiveButtonClick(dialog) }
                .setNegativeButton(negativeButtonString) { dialog, _ -> dialogButtonClick?.negativeButtonClick(dialog) }
                .create()
    }

    internal class Builder {

        private lateinit var message: String

        private var positiveButtonString: Int = R.string.confirm
        private var negativeButtonString: Int = R.string.cancel
        private lateinit var buttonClickListener: DialogButtonClickListener


        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setPositiveButtonString(positiveButtonString: Int): Builder {
            this.positiveButtonString = positiveButtonString
            return this
        }

        fun setNegativeButtonString(negativeButtonString: Int): Builder {
            this.negativeButtonString = negativeButtonString
            return this
        }

        fun setButtonListener(dialogButtonClick: DialogButtonClickListener): Builder {
            this.buttonClickListener = dialogButtonClick
            return this
        }

        fun build(): UserDialogFragment {
            return UserDialogFragment(message, positiveButtonString, negativeButtonString, buttonClickListener)
        }

    }


    /**
     * 对话框点击事件的接口
     */
    interface DialogButtonClickListener {

        /**
         * 确定点击
         */
        fun positiveButtonClick(dialog: DialogInterface?)

        /**
         * 取消点击
         */
        fun negativeButtonClick(dialog: DialogInterface?)

    }
}


