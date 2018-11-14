package com.example.administrator.wanandroid.adapter

import android.support.v7.util.DiffUtil
import com.example.administrator.wanandroid.bean.ArticleBean

/**
 * @author  : Alex
 * @date    : 2018/10/18
 * @version : V 2.0.0
 */
class ArticleDiffUtil : DiffUtil.ItemCallback<ArticleBean>() {

    override fun areItemsTheSame(p0: ArticleBean, p1: ArticleBean): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: ArticleBean, p1: ArticleBean): Boolean {
        return p0 == p1
    }
}