package com.example.library.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View

/**
 * Created by Horrarndoo on 2017/9/1.
 *
 *
 * 资源工具类-加载资源文件
 */

object ResourcesUtils {
    /**
     * 获取strings.xml资源文件字符串
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串
     */
    fun getString(id: Int): String {
        return AppUtils.context.resources.getString(id)
    }

    /**
     * 获取strings.xml资源文件字符串数组
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串数组
     */
    fun getStringArray(id: Int): Array<String> {
        return AppUtils.context.resources.getStringArray(id)
    }

    /**
     * 获取drawable资源文件图片
     *
     * @param id 资源文件id
     * @return 资源文件对应图片
     */
    fun getDrawable(id: Int): Drawable {
        return AppUtils.context.resources.getDrawable(id)
    }

    /**
     * 获取colors.xml资源文件颜色
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色值
     */
    fun getColor(id: Int): Int {
        return AppUtils.context.resources.getColor(id)
    }

    /**
     * 获取颜色的状态选择器
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色状态
     */
    fun getColorStateList(id: Int): ColorStateList? {
        return AppUtils.context.resources.getColorStateList(id)
    }

    /**
     * 获取dimens资源文件中具体像素值
     *
     * @param id 资源文件id
     * @return 资源文件对应像素值
     */
    fun getDimen(id: Int): Int {
        return AppUtils.context.resources.getDimensionPixelSize(id)
    }

    /**
     * 加载布局文件
     *
     * @param id 布局文件id
     * @return 布局view
     */
    fun inflate(id: Int): View {
        return View.inflate(AppUtils.context, id, null)
    }
}
